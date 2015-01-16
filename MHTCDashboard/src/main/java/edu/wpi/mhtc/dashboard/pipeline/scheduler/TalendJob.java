/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;

@PersistJobDataAfterExecution 
public class TalendJob implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException{
		// Process job parameters
		JobDataMap parameters = context.getJobDetail().getJobDataMap();
		String jobPipelineName = parameters.get("jobPipelineName").toString();
		
		// Retrieve the job detail from the database
		try {
			Connection conn = DBConnector.getInstance().getConn();
			String sql = "SELECT * FROM mhtc_sch.pipelines WHERE pipelinename = ?";
			PreparedStatement pstatement;
			pstatement = conn.prepareStatement(sql);
			pstatement.setString(1, jobPipelineName);
			ResultSet rs = pstatement.executeQuery();
			
			while (rs.next()) {
				// Primary key selection, therefore this only loops once.
				String pipelineDesc = rs.getString("pipelinedesc");	
				String path = rs.getString("path");
				String filename = rs.getString("filename");
				
				// Run the job
				TalendJob.runPipeline(jobPipelineName, pipelineDesc, path, filename);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void runPipeline(String pipelineName, String pipelineDesc, String path, String filename) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(path);
		Process p = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		System.out.println("==== Talend job" + pipelineName + " shell script log ====");
		while ((line = reader.readLine()) != null)
		{
			System.out.println(line);
		}	   
	}
}