package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.scheduler.Schedule;

/**
 * Interface for Data Access Object for Schedules
 * @author Alex Fortier
 *
 */
public interface ScheduleDAO {

	public void save(Schedule schedule);
	
	public void delete(String job_name);
	
	public void update(Schedule schedule);
	
	public Schedule get(String sched_name);
	
	public List<Schedule> getAll();
	
}
