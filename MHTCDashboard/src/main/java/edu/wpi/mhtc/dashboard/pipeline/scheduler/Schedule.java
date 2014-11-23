package edu.wpi.mhtc.dashboard.pipeline.scheduler;

import java.sql.SQLException;

import edu.wpi.mhtc.dashboard.pipeline.db.DBSaver;
import edu.wpi.mhtc.dashboard.pipeline.db.MD5;

public class Schedule {
	private String sched_name;
	private String sched_job;
	private String sched_description;
	private String sched_date;
	private String job_name;
	
	public Schedule(String sched_name, String sched_job, String sched_description, String sched_date) {
		this.sched_name = sched_name;
		this.sched_job = sched_job;
		this.sched_description = sched_description;
		this.sched_date = sched_date;
		this.job_name = MD5.getMD5(sched_description + sched_date + sched_job);
	}
	
	public Schedule(String job_name, String sched_name, String sched_job, String sched_description, String sched_date) {
		this.job_name = job_name;
		this.sched_name = sched_name;
		this.sched_job = sched_job;
		this.sched_description = sched_description;
		this.sched_date = sched_date;	
	}
	
	public boolean insertToDB() throws SQLException {
		return DBSaver.insertNewSchedule(sched_name, sched_job, sched_description, sched_date);
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

	public String getJob_name() {
		return job_name;
	}
}
