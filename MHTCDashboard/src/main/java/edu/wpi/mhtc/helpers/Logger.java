/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.helpers;

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
	
	public static void jsonTalendLog(String job, int code, String message, String origin, String moment, int priority) throws SQLException {
		Logger.postLog(job, code, message, origin, moment, priority);
	}

	public static void postLog(String job, int code, String message, String origin, String moment, int priority) throws SQLException {
		String sql = "insert into mhtc_sch.logs(job, code, message, moment, priority, origin) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, job); // set parameter 1 (FIRST_NAME)
		pstatement.setInt(2, code); // set parameter 1 (FIRST_NAME)
		pstatement.setString(3, message); // set parameter 2 (ID)
		pstatement.setString(4, moment);
		pstatement.setInt(5,priority);
		pstatement.setString(6, origin);
		
		pstatement.execute();
		pstatement.close();	
		System.out.println(job + " - " + message + " (" + moment + ")");
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
			String job = rs.getString("job");	
			String message = rs.getString("message");
			String code = rs.getString("code");
			String priority = rs.getString("priority");
			String moment = rs.getString("moment");
			
			row.put("id",id);
			row.put("job", job);
			row.put("message", message);
			row.put("moment", moment);
			row.put("priority", priority);
			row.put("code", code);
			
			data.add(row);
		}
		
		return data;	
	}
}
