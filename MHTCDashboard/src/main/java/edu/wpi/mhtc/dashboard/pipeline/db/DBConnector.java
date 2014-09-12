package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

	private static DBConnector dbConnector;
	private String url = "jdbc:postgresql://mhtc.cs.wpi.edu:5432/mhtc_local";
	private String user = "long";
	private String password = "123";
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
