package edu.wpi.mhtc.dashboard.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;
import org.json.*;

public class Logger {
	 // Database connection
	static Connection conn = DBConnector.getInstance().getConn();
	
	public static void jsonTalendLog(String jsonStr) throws SQLException {
		JSONObject json = new JSONObject(jsonStr);
		JSONObject data = json.getJSONArray("data").getJSONObject(0);
		
		// Parse JSON properties
		String message = data.getString("message");
		int priority = data.getInt("priority");
		String moment = data.getString("moment");
		String origin = data.getString("origin");
		String job = data.getString("job");
		String status;
		
		// Decide which status
		switch (priority) {
			case 6: status = "fatal"; break;
			case 5: status = "error"; break;
			case 4: status = "warning"; break;
			default: status = "info"; break;
		}
		
		// Post the log
		Logger.postLogWithDate(job, "[" + origin + "] " + message, status, moment);
	}
	
	public static void log(String componentName, String message) throws SQLException {
		Logger.postLog(componentName, message, "");
	}
	
	public static void error(String componentName, String message) throws SQLException {
		Logger.postLog(componentName, message, "error");
	}
	
	public static void warning(String componentName, String message) throws SQLException {
		Logger.postLog(componentName, message, "warning");
	}
	
	public static void success(String componentName, String message) throws SQLException {
		Logger.postLog(componentName, message, "success");
	}
	
	private static void postLog(String componentName, String message, String status) throws SQLException {
		String sql = "insert into mhtc_sch.logs(component_name, message, status) values(?, ?, ?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, componentName); // set parameter 1 (FIRST_NAME)
		pstatement.setString(2, message); // set parameter 2 (ID)
		pstatement.setString(3, status);
		pstatement.execute();
		pstatement.close();	
		System.out.println("[" + status + "]" + componentName + " - " + message);
	}

	public static void postLogWithDate(String componentName, String message, String status, String log_datetime) throws SQLException {
		String sql = "insert into mhtc_sch.logs(component_name, message, status, log_datetime) values(?, ?, ?, ?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, componentName); // set parameter 1 (FIRST_NAME)
		pstatement.setString(2, message); // set parameter 2 (ID)
		pstatement.setString(3, status);
		pstatement.setString(4, log_datetime);
		pstatement.execute();
		pstatement.close();	
		System.out.println("[" + status + "]" + componentName + " - " + message + " (" + log_datetime + ")");
	}
	
	public static ArrayList<HashMap<String,String>> retriveLog() throws SQLException, ParseException {
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		Connection conn = DBConnector.getInstance().getConn();
		
		String sql = "SELECT * FROM mhtc_sch.logs";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		ResultSet rs = pstatement.executeQuery();
		
		while (rs.next()) {
			HashMap<String,String> row = new HashMap<String,String>();
			String id = rs.getString("id");
			String componentName = rs.getString("component_name");	
			String message = rs.getString("message");
			String status = rs.getString("status");
			String logDateTime = rs.getString("log_datetime");
			
			row.put("id",id);
			row.put("logDateTime", logDateTime);
			row.put("componentName", componentName);
			row.put("message", message);
			row.put("status", status);
			
			data.add(row);
		}
		
		return data;	
	}
}
