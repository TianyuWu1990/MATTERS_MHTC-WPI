/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.scheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.sql.SQLException;

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
	
	public String getSched_datePassed() throws ParseException {
		Date today = new Date();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.ENGLISH);
		Date runDate = format.parse(sched_date);
		
		if (today.after(runDate)) {
			return "Pipeline executed."; 
		} else {
			return "Will run at " + sched_date;
		}		
	}

	public String getJob_name() {
		return job_name;
	}
	
	public boolean isCronJob() {
		return sched_cron;
	}
}
