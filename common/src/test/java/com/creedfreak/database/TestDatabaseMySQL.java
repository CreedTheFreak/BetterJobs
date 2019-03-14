package com.creedfreak.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MariaDBContainer;

@TestInstance (TestInstance.Lifecycle.PER_CLASS)
@DisplayName ("Test MySQL Database")
public class TestDatabaseMySQL
{
	private MariaDBContainer mDatabase;
	
	private String mJdbcUrl;
	private String mDatabaseName;
	private String mDatabasePassword;
	
	@BeforeAll
	public void Initialize ()
	{
		// Initialize data here.
		mDatabase = new MariaDBContainer ();
		
		mJdbcUrl = mDatabase.getJdbcUrl ();
		mDatabaseName = mDatabase.getDatabaseName ();
		mDatabasePassword = mDatabase.getPassword ();
	}
	
	
	@Test
	public void TestDatabaseConnectionSQLite ()
	{
	
	}
	
	@Test
	public void TestDatabaseConnectionMySQL ()
	{
	
	}
}
