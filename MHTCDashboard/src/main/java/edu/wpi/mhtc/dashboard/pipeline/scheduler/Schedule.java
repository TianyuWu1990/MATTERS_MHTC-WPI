/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.scheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;
import edu.wpi.mhtc.dashboard.pipeline.db.DBSaver;
import edu.wpi.mhtc.helpers.MD5;


// TODO: Abstract this class into multiple type of Schedule: TalendSchedule, NormalSchedule
public class Schedule {
	private String sched_name;
	private String sched_job;
	private String sched_description;
	private String sched_date;
	private String job_name;
	private boolean sched_cron;
	static Connection conn = DBConnector.getInstance().getConn();
	
	public Schedule(String sched_name, String sched_job, String sched_description, String sched_date, boolean sched_cron) {
		this.sched_name = sched_name;
		this.sched_job = sched_job;
		this.sched_description = sched_description;
		this.sched_cron = sched_cron;
		this.sched_date = sched_date;
		this.job_name =  sched_name.replaceAll("\\s+","") + MD5.getMD5(sched_description + sched_date + sched_job);
	}
	
	public Schedule(String job_name, String sched_name, String sched_job, String sched_description, String sched_date, boolean sched_cron) {
		this.job_name = job_name;
		this.sched_name = sched_name;
		this.sched_job = sched_job;
		this.sched_description = sched_description;
		this.sched_cron = sched_cron;
		this.sched_date = sched_date;	
	}
	
	public boolean insertToDB() {
		try {
			return DBSaver.insertNewSchedule(job_name, sched_name, sched_job, sched_description, sched_date, sched_cron);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Getters
	public String getSched_name() {
		return sched_name;
	}

	public String getSched_job() {
		return sched_job;
	}

	public String getSched_description() {
		return sched_description;
	}

	public String getSched_date() {
		return sched_date;
	}
	
	public String getSched_datePassed() throws ParseException, SQLException {
		Date today = new Date();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.ENGLISH);
		Date runDate = format.parse(sched_date);
		
		if (today.after(runDate)) {
			// Now, check the Logger to see the current status
			String sql = "SELECT * FROM mhtc_sch.logs WHERE message = 'Pipeline has finished' AND job = ?";
			
			PreparedStatement pstatement = conn.prepareStatement(sql);
			pstatement.setString(1, this.sched_job);
			ResultSet rs = pstatement.executeQuery();
			
			// Loop through the record
			while (rs.next()) {
				String moment = rs.getString("moment");
				DateFormat finishDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				Date finishDate = finishDateFormat.parse(moment);
				long timediff = finishDate.getTime() - runDate.getTime();
				
				if (timediff <= 604800) { // Check if the difference between finish date and execution date is less than 1 week 
					return "Completed"; 
				}
			}
			
			return "Running..."; 
		} else {
			return "Scheduled to run at " + sched_date;
		}		
	}

	public String getJob_name() {
		return job_name;
	}
	
	public boolean isCronJob() {
		return sched_cron;
	}
}
