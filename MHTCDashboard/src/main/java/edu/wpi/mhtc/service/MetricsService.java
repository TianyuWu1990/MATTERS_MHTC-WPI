package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.admin.AdminMetric;
import edu.wpi.mhtc.model.admin.TreeViewNode;
import edu.wpi.mhtc.persistence.DBMetric;

public interface MetricsService {

	List<DBMetric> getAvailibleStatistics();
	List<AdminMetric> getMetricsForCategory(int categoryID);
	TreeViewNode getCategoriesAsTree();
	void storeCategory(String name, int parentId, String source);
}
