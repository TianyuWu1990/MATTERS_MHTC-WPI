package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.Data.Metric;

public interface MetricService{

	public List<Metric> getAllMetrics();
	public List<Metric> getAllMetricsByCategory(int categoryId);
	public List<Metric> getAllVisibleMetrics();
	public List<Metric> getAllVisibleMetricsByCategory(int categoryId);
	public Metric getMetricById(int id);
	public Metric getMetricByName(String name);
	public Metric getMetricByDisplayName(String name);
	
	public void saveMetric(Metric metric);
}
