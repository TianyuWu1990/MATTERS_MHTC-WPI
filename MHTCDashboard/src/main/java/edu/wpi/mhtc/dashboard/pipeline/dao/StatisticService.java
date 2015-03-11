package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Service to handle getting Stats from the database, as well
 * as storing Stats from Manual Upload
 * @author afortier
 *
 */
@Service
public class StatisticService {
	
	@Autowired private StatisticDAO dao;
	@Autowired private PlatformTransactionManager transManager;
	
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
	public void save(List<Statistic> stats, boolean overwrite) {
		
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transManager.getTransaction(def);
		
		
		for (Statistic s : stats) {
			try {
				if (overwrite) {
					dao.merge(s);
				} else {
					dao.save(s);
				}
			} catch (Exception e) {
				transManager.rollback(status);
				throw e;
			}
		}
		
		transManager.commit(status);
	}
		
}
