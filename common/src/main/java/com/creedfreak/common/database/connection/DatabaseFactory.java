package com.creedfreak.common.database.connection;

import com.creedfreak.common.IConfiguration;
import com.creedfreak.common.ICraftyProfessions;
import com.creedfreak.common.utility.Logger;

public class DatabaseFactory {

	public static Database buildDatabase (ICraftyProfessions plugin, IConfiguration config) {
		String dbType = config.getString ("DatabaseType");
		Database db = null;

		if (dbType.equalsIgnoreCase ("sqlite")) {
			Logger.Instance ().Info (Database.DATABASE_PREFIX, "Constructing SQLite Connection.");
			db = new SQLite_Conn (plugin.getResourceFile ());
		} else if (dbType.equalsIgnoreCase ("mysql")) {
			String host = config.getString ("MySQL.host");
			int port = config.getInt ("MySQL.port");
			String database = config.getString ("MySQL.db_name");
			String user = config.getString ("MySQL.user");
			String identifier = config.getString ("MySQL.password");

			Logger.Instance ().Info (Database.DATABASE_PREFIX, "Constructing MariaDB Connection.");
			db = new MySQL_Conn (host, port, database, user, identifier);
		}

		return db;
	}
}
