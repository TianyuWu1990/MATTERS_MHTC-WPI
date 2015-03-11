package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to handle getting Stats from the database, as well
 * as storing Stats from Manual Upload
 * @author afortier
 *
 */
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
	
	/**
	 * Determines if overwrite is needed, and then enters data
	 * into the db
	 * @param stats
	 * @param overwrite
	 */
	@Transactional
	public void save(List<Statistic> stats, boolean overwrite) {
		
		for (Statistic s : stats) {
			if (overwrite) {
				dao.merge(s);
			} else {
				dao.save(s);
			}
		}
	}
		
}
