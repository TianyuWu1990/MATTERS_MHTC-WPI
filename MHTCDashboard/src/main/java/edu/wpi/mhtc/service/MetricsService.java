package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.persistence.DBMetric;

public interface MetricsService {

	public List<DBMetric> getAvailibleStatistics();
	public List<DBMetric> getMetricsInCategory(int categoryId);
}
