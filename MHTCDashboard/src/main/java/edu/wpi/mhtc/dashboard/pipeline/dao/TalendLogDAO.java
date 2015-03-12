/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

/**
 * Data Access Object specifically for Talend Logs
 * @author Alex Fortier
 *
 */
public interface TalendLogDAO {

	/**
	 * Save a log generate from Talend pipeline
	 * @param log
	 */
	public void save(TalendLog log);
	
	/**
	 * Retrieve a log for a given job name
	 * @param job
	 * @return
	 */
	public List<TalendLog> get(String job);
	
	/**
	 * Get summary of all logs from the db
	 * @return
	 */
	public List<TalendLog> getSummary();
	
}
