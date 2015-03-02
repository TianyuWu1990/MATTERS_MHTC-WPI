package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
	
	@Autowired private StatisticDAO dao;
	
	/**
	 * Get all statistics by metric
	 * @param metricID
	 * @return
	 */
	public List<Statistic> getStatsByMetric(int metricID) {
		return dao.getStatsByMetric(metricID);
	}
	
	/**
	 * Get all statistics for all metrics in given category
	 * @param categoryID
	 * @return
	 */
	public List<Statistic> getStatsByCategory(int categoryID) {
		return dao.getStatsByCategory(categoryID);
	}
		
}
