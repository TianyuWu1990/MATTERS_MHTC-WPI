/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dao.admin;

import java.text.ParseException;
import java.util.List;

import edu.wpi.mhtc.model.admin.Schedule;

/**
 * Interface for Data Access Object for Schedules
 * @author Alex Fortier
 *
 */
public interface ScheduleDAO {

	/**
	 * Save a schedule to the database
	 * @param schedule
	 */
	public void save(Schedule schedule);
	
	/**
	 * Delete a schedule from the database
	 * @param job_name
	 */
	public void delete(String job_name);
	
	/**
	 * Update schedule in the database
	 * @param schedule
	 */
	public void update(Schedule schedule);
	
	/**
	 * Get schedule from the database
	 * @param sched_name
	 * @return
	 */
	public Schedule get(String sched_name);
	
	/**
	 * Get current status of pipeline (Running..., Completed, etc.)
	 * @param sched_date
	 * @return
	 * @throws ParseException
	 */
	public String getSchedStatus(String sched_date) throws ParseException;
	
	/**
	 * Get all schedules in the database
	 * @return
	 * @throws ParseException
	 */
	public List<Schedule> getAll() throws ParseException;
	
	/**
	 * Get corresponding job with schedule
	 * @param sched_job
	 * @return
	 */
	public String getTalendJob(String sched_job);
	
}
