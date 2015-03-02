package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.dashboard.pipeline.scheduler.Schedule;

@Service
public class ScheduleService {

	@Autowired private ScheduleDAO dao;
	
	/**
	 * Save a schedule to the database
	 * @param job_name
	 * @param sched_name
	 * @param sched_job
	 * @param sched_description
	 * @param sched_date
	 * @param sched_cron
	 */
	public void save(String sched_name, String sched_job, String sched_description, 
			String sched_date, boolean sched_cron) {
		dao.save(new Schedule(sched_name, sched_job, sched_description, sched_date, sched_cron));
	}
	
	/**
	 * Get all schedules in the database
	 * @return
	 * @throws ParseException 
	 */
	public List<Schedule> getAll() throws ParseException {
		return dao.getAll();
	}
	
	public void delete(String job_name) {
		dao.delete(job_name);
	}
}
