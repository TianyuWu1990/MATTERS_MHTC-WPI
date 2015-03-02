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
import java.io.File;
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
	private String status;
	private String filename;
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
	
	public String getTalendJob() throws SQLException {
		return filename;
	}
	
	public String getStatus() {
		return status;
	}

	public String getJob_name() {
		return job_name;
	}
	
	/**
	 * @return the sched_cron
	 */
	public boolean isSched_cron() {
		return sched_cron;
	}

	/**
	 * @param sched_cron the sched_cron to set
	 */
	public void setSched_cron(boolean sched_cron) {
		this.sched_cron = sched_cron;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	
	
}
