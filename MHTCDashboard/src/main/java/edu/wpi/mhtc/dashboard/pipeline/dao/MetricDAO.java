package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;
import java.util.Set;

/**
 * Interface for Data Access Object
 * Used for Metrics
 * @author Alex Fortier
 *
 */
public interface MetricDAO {

	/**
	 * Insert a metric into the database
	 * @param metric
	 */
	public void save(Metric metric);
	
	/**
	 * Update a metric in the database
	 * @param metric
	 */
	public void update(Metric metric);
	
	/**
	 * Delete a metric from the database, based on ID
	 * @param ID
	 */
	public void delete(int ID);
	
	/**
	 * Get all metrics in the database
	 */
	public List<Metric> getAll();
	
	/**
	 * Get all metrics for a single category
	 * @param categoryID
	 * @return
	 */
	public List<Metric> getMetricsForCategory(int categoryID);
	
	/**
	 * Get all metrics for all categories, and subcategories
	 * @param categoryIDs
	 * @return
	 */
	public List<Metric> getMetricsForCategories(Integer[] categoryIDs);

	/**
	 * Returns the various datatypes for a metric
	 * @return
	 */
	public Set<String> getMetricDataTypes();
}
