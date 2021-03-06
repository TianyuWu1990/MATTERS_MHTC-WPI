/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Connect to db.
 *
 */
public class DBConnector {

	private static DBConnector dbConnector;
	
	/* Local DB Configuration */
//	private String url = "jdbc:postgresql://localhost:5432/mhtc_local";
//	private String user = "postgres";
//	private String password = "12345";
	
	/*Production Configuration*/
	private String url = "jdbc:postgresql://localhost:5432/mhtc-dev";
	private String user ="server";
	private String password ="mhtcboxofducks";
	
	private Connection conn = null;

	private DBConnector() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DBConnector getInstance() {
		if (dbConnector == null) {
			dbConnector = new DBConnector();
		}
		return dbConnector;
	}

	/*
	 * getters and setters
	 */
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {  
		this.conn = conn;
	}
	
}
