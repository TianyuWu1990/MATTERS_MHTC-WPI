package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricService {
	
	@Autowired private MetricDAO dao;
	
	/**
	 * Insert a metric into the database
	 * @param metric
	 */
	public void save(String metricName, String metricDesc, boolean isCalc, int categoryID, String datatype) {
		Metric m = new Metric();
		
		m.setName(metricName);
		m.setDisplayName(metricDesc);
		m.setCalculated(isCalc);
		m.setCategoryId(categoryID);
		m.setDataType(datatype);
		
		dao.save(m);
	}
	
	/**
	 * Update a metric in the database
	 * @param metric
	 */
	public void update(Metric metric) {
		
	}
	
	/**
	 * Delete a metric from the database, based on ID
	 * @param ID
	 */
	public void delete(int ID) {
		
	}
	
	/**
	 * Get all metrics in the database
	 */
	public List<Metric> getAll() {
		return dao.getAll();
	}
	
	/**
	 * Get all metrics for a single category
	 * @param categoryID
	 * @return
	 */
	public List<Metric> getMetricsForCategory(int categoryID) {
		return dao.getMetricsForCategory(categoryID);
	}
	
	/**
	 * Get all metrics for all categories, and subcategories
	 * @param categoryIDs
	 * @return
	 */
	public List<Metric> getMetricsForCategories(Integer[] categoryIDs) {
		return dao.getMetricsForCategories(categoryIDs);
	}
	
	/**
	 * Get all metric DataTypes
	 * @return
	 */
	public Set<String> getMetricDataTypes() {
		return dao.getMetricDataTypes();
	}

}
