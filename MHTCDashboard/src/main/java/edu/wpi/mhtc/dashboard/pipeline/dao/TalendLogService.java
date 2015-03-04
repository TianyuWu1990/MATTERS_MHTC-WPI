package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TalendLogService {

	@Autowired private TalendLogDAO dao;
	
	/**
	 * Insert a log into the database
	 * @param job
	 * @param code
	 * @param message
	 * @param origin
	 * @param moment
	 * @param priority
	 */
	public void save(String job, int code, String message, String origin, String moment, int priority) {
		TalendLog log = new TalendLog(moment, job, message, code, priority, origin);
		
		dao.save(log);
	}
	
	/**
	 * Get TalendLog by jobname
	 * @param job
	 * @return
	 */
	public List<TalendLog> get(String job) {
		return dao.get(job);
	}
	
	/**
	 * Return summary of logs for reports page
	 * @return
	 */
	public List<TalendLog> getSummary() {
		return dao.getSummary();
	}
	
}
