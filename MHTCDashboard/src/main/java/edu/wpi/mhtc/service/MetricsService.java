package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.admin.AdminMetric;
import edu.wpi.mhtc.model.admin.TreeViewNode;
import edu.wpi.mhtc.model.Data.Metrics;
import edu.wpi.mhtc.persistence.DBMetric;

public interface MetricsService extends Service<DBMetric, Metrics>{

	List<AdminMetric> getMetricsForCategory(int categoryID);
	TreeViewNode getCategoriesAsTree();
	void storeCategory(String name, int parentId, String source);
    void storeMetric(int categoryId, String name, boolean isCalculated, String type);
    void updateCategory(int categoryId, String name, boolean visible, String source);
    void updateMetric(int metricId, String name, boolean visible, boolean isCalculated, String type);

	@Override
	public Metrics getAvailible(Object... params);
	
	public List<DBMetric> getMetricsInCategory(int categoryId);
}
