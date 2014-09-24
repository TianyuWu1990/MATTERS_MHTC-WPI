package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.mhtc.dashboard.pipeline.data.DBData;

public class DBConnector {

	private static DBConnector dbConnector;
	private String url = "jdbc:postgresql://127.0.0.1:5432/mhtc_local";
	private String user = "postgres";
	private String password = "12345";
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
