/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.service;

import java.sql.SQLException;
import java.util.List;

import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.admin.AdminMetric;
import edu.wpi.mhtc.model.admin.TreeViewNode;

public interface MetricService{

	public List<AdminMetric> getMetricsForCategory(int categoryID);
	public TreeViewNode getCategoriesAsTree();
	public void storeCategory(String name, int parentId, String source);
	public void storeMetric(int categoryId, String name, boolean isCalculated, String type);
	public void updateCategory(int categoryId, String name, boolean visible, String source);
	public void updateMetric(int metricId, String name, boolean visible, boolean isCalculated, String type);
	
	public List<Metric> getMetricsInCategory(int categoryId, int binId);
	
	public List<Metric> getAllMetrics();
	public List<Metric> getMetricsFromParents(Integer... parentIds) throws SQLException;
}
