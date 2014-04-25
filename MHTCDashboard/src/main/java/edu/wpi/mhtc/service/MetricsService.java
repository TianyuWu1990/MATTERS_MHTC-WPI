package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.Data.Metrics;
import edu.wpi.mhtc.persistence.DBMetric;

public interface MetricsService extends Service<DBMetric, Metrics>{

	@Override
	public Metrics getAvailible(Object... params);
	
	public List<DBMetric> getMetricsInCategory(int categoryId);
}
