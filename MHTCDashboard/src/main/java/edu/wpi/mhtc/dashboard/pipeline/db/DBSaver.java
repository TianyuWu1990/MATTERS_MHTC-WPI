/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import edu.wpi.mhtc.dashboard.pipeline.data.Line;

public class DBSaver {

	/**
	 * Save line data into DB.
	 */

	public static void saveLine(Connection conn, Line line) throws SQLException {
		String sql = "insert into mhtc_sch.statistics values(?, ?, ?, ?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setInt(1, line.getStateID()); // set parameter 1 (FIRST_NAME)
		pstatement.setInt(2, line.getMetricID()); // set parameter 2 (ID)
		pstatement.setInt(3, line.getYear());
		pstatement.setFloat(4, line.getMetricValue());
		pstatement.execute();
		pstatement.close();
	}

	public static boolean insertNewCategory(String name, String parentID, String source) throws SQLException {

		Connection conn = DBConnector.getInstance().getConn();

		String sql = "select * from mhtc_sch.insertcategory(?,?,?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, name); 

		if (parentID.isEmpty()) {
			pstatement.setNull(2, Types.INTEGER);
		}
		else {
			pstatement.setInt(2, Integer.parseInt(parentID));
		}

		pstatement.setString(3, source); 
		ResultSet rs = pstatement.executeQuery();

		rs.next();

		if (Integer.parseInt(rs.getString("insertcategory")) == 1)
			return true;
		else 
			return false;
	}

	//	Category must exist in db already !!!
	public static boolean insertNewMetric(String metricName, String metricDesc, boolean b, int categoryID, String dataType) throws SQLException {

		Connection conn = DBConnector.getInstance().getConn();

		// Stored procedure doesn't allow us to enter a description (column DisplayName)
		String sql = "select * from mhtc_sch.insertmetric(?,?,?,?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, metricName); 
		pstatement.setBoolean(2, b);
		pstatement.setInt(3,categoryID);
		pstatement.setString(4, dataType);
		
		pstatement.execute();
		
		sql = "UPDATE mhtc_sch.metrics SET \"DisplayName\" = ? WHERE \"Name\" = ?";
		pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, metricDesc);
		pstatement.setString(2, metricName);
		
		return pstatement.execute();
	}

	// Insert new schedule
	public static boolean insertNewSchedule(String job_name, String sched_name, String sched_job, String sched_description, String sched_date, boolean sched_cron) throws SQLException {
		String sql = "insert into mhtc_sch.schedules(\"job_name\", \"sched_name\", \"sched_job\", \"sched_date\", \"sched_description\", \"sched_cron\") values(?, ?, ?, ?, ?, ?) ";
		Connection conn = DBConnector.getInstance().getConn();
		PreparedStatement pstatement = conn.prepareStatement(sql);

		pstatement.setString(1, job_name);
		pstatement.setString(2, sched_name);
		pstatement.setString(3, sched_job);
		pstatement.setString(4, sched_date);
		pstatement.setString(5, sched_description);
		pstatement.setBoolean(6, sched_cron);
		
		return pstatement.execute();
	}
	
	/**
	 * Inserts a new pipeline for tracking into the db
	 * @param pipelineName
	 * @param pipelineDesc
	 * @param path
	 * @param filename
	 * @return
	 * @throws SQLException
	 */
	public static boolean insertPipeline(String pipelineName, String pipelineDesc, String path, String filename) throws SQLException {
		String sql = "INSERT INTO mhtc_sch.pipelines(\"pipelinename\", \"pipelinedesc\", \"path\", \"filename\") VALUES (?, ?, ?, ?)";
		Connection conn = DBConnector.getInstance().getConn();
		PreparedStatement pstatement = conn.prepareStatement(sql);
		
		pstatement.setString(1, pipelineName);
		pstatement.setString(2, pipelineDesc);
		pstatement.setString(3, path);
		pstatement.setString(4, filename);
		
		return pstatement.execute();
	}

}
