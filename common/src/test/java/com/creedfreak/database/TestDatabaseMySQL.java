package com.creedfreak.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.awaitility.Awaitility.*;

import com.creedfreak.artificial.MockPlayerFactory;
import com.creedfreak.common.container.PlayerManager;
import com.creedfreak.common.database.connection.Database;
import com.creedfreak.common.database.connection.MySQL_Conn;
import com.creedfreak.common.utility.UuidUtil;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

@Testcontainers
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
@DisplayName ("Test MySQL Database")
public class TestDatabaseMySQL
{
	@Container
	private static MariaDBContainer mDBContainer = new MariaDBContainer ();

	private static Database mDatabase;
	private PlayerManager mPManagerRef = PlayerManager.Instance ();
	private	Logger mLogger = LoggerFactory.getLogger (TestDatabaseMySQL.class);

	@Test
	@BeforeAll
	public void TestContainerRunning () {
		assertTrue (mDBContainer.isRunning ());
	}

	@BeforeAll
	public void Initialize () {
		mDatabase = new MySQL_Conn (mDBContainer.getJdbcUrl (), mDBContainer.getUsername (), mDBContainer.getPassword ());
		mDatabase.initializeDatabase ();

		// Prepare the player manager running with a 4 thread total.
		mPManagerRef.preparePlayerManager (mDatabase, new MockPlayerFactory (), 4);
	}

	@AfterAll
	public void Destroy () {
		mDatabase.shutdown ();
		mDBContainer.close ();
	}

	@Test
	public void TestDatabaseConstruction () {
		try (Connection conn = mDatabase.dbConnect ();
		PreparedStatement prepStmt = conn.prepareStatement ("SELECT * FROM Settings");
		ResultSet resultSet = prepStmt.executeQuery ()) {

			assertTrue (resultSet.first ());

		}
		catch (SQLException except) {
			mLogger.error ("SQLException caught causing test to fail!");
			mLogger.error ("Exception: ", except);
			fail ();
		}
	}

	/**
	 * Test that the same connection is not returned from the MariaDB connection
	 * method.
	 */
	@Test
	public void TestMariaDBMultipleConnections () {
		Connection conn1, conn2, conn3;

		conn1 = mDatabase.dbConnect ();
		conn2 = mDatabase.dbConnect ();
		conn3 = mDatabase.dbConnect ();

		assertNotEquals (conn1, conn2);
		assertNotEquals (conn1, conn3);
		assertNotEquals (conn2, conn3);

		Database.CloseConnection (conn1);
		Database.CloseConnection (conn2);
		Database.CloseConnection (conn3);

	}

	@Test
	public void TestInsertUsers ()
	{
		HashMap<String, UUID> playerMap = new HashMap<> ();
		Connection conn = mDatabase.dbConnect ();
		PreparedStatement prepStmt = null;
		ResultSet resultSet = null;
		int resSize = 0;

		playerMap.put ("Logan", UUID.randomUUID ());
		playerMap.put ("Jimmy", UUID.randomUUID ());
		playerMap.put ("Javes", UUID.randomUUID ());
		playerMap.put ("CreedTheFreak", UUID.randomUUID ());

		for (Map.Entry<String, UUID> entry : playerMap.entrySet ()) {
			mPManagerRef.savePlayer (entry.getValue (), entry.getKey ());
		}

		try {
			Thread.sleep (100);

			prepStmt = conn.prepareStatement ("SELECT * FROM Users");
			resultSet = prepStmt.executeQuery ();

			while (resultSet.next ()) {
				String currentUsername;
				UUID currentUUID;

				currentUsername = resultSet.getString ("Username");
				currentUUID = UuidUtil.fromBytes (resultSet.getBytes ("UUID"));

				assertTrue (playerMap.containsKey (currentUsername));
				assertTrue (playerMap.containsValue (currentUUID));
				++resSize;
			}

			assertEquals (resSize, playerMap.size ());
		}
		catch (InterruptedException | SQLException except) {
			// Force the test to fail.
			mLogger.error ("Exception: ", except);
			fail ();
		}
		finally {
			Database.CloseResources (resultSet, prepStmt);
			Database.CloseConnection (conn);
		}
	}

	@Test
	public void TestUserExists () {
		UUID savedUUID = UUID.randomUUID ();
		String mockUsername = "CreedTheFreak";

		mPManagerRef.savePlayer (savedUUID, mockUsername);

		try {
			mLogger.info ("Waiting on database task condition...");
			await ().until (userIsAdded (mockUsername));
		}
		catch (ConditionTimeoutException except) {
			mLogger.error ("Added user condition not met by timeout, failing test.");
			fail ();

		}

	}


	/**
	 * Private Callables tailored for Awaitility
	 */
	private Callable<Boolean> userIsAdded (String username) {
		return new Callable<Boolean> () {
			public Boolean call () {
				boolean retVal;
				Connection conn = mDatabase.dbConnect ();
				PreparedStatement prepStmt = null;
				ResultSet resultSet = null;

				try {
					prepStmt = conn.prepareStatement ("SELECT Username FROM Users WHERE Username = ?");
					prepStmt.setString (1, username);
					resultSet = prepStmt.executeQuery ();

					retVal = resultSet.first ();
				}
				catch (SQLException except) {
					retVal = false;
				}
				finally {
					Database.CloseResources (resultSet, prepStmt);
					Database.CloseConnection (conn);
				}
				return retVal;
			}
		};
	}
}
