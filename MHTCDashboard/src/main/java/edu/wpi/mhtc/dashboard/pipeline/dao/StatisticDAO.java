package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

public interface StatisticDAO {

	public List<Statistic> getStatsByMetric(int metricID);
	
	public List<Statistic> getStatsByState(int stateID);
	
	public List<Statistic> getStatsByMetricAndState(int metricID, int stateID);
	
}
