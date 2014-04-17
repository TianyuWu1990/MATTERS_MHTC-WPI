package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.admin.AdminMetric;
import edu.wpi.mhtc.model.admin.TreeViewNode;
import edu.wpi.mhtc.persistence.DBMetric;

public interface MetricsService {

	public List<DBMetric> getAvailibleStatistics();
	public List<AdminMetric> getMetricsForCategory(int categoryID);
	public TreeViewNode getCategoriesAsTree();
}
