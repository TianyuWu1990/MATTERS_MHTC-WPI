/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dao.admin;

import java.util.List;

import edu.wpi.mhtc.model.admin.Statistic;

/**
 * Interface for Statistic DAO layer
 * @author Alex Fortier
 *
 */
public interface StatisticDAO {

	/**
	 * Get all stats in database by category
	 * @param categoryID
	 * @return
	 */
	public List<Statistic> getStatsByCategory(int categoryID);
	
	/**
	 * Get all stats in database by metric
	 * @param metricID
	 * @return
	 */
	public List<Statistic> getStatsByMetric(int metricID);
	
	/**
	 * Get all stats in database by state
	 * @param stateID
	 * @return
	 */
	public List<Statistic> getStatsByState(int stateID);
	
	/**
	 * Get stats in database by metric and state
	 * @param metricID
	 * @param stateID
	 * @return
	 */
	public List<Statistic> getStatsByMetricAndState(int metricID, int stateID);
	
	/**
	 * Save statistic to database
	 * @param stat
	 */
	public void save(Statistic stat);

	/**
	 * Overwrite statistic in database
	 * Will also save statistics in database (UPSERT)
	 * @param stat
	 */
	public void merge(Statistic stat);
}
