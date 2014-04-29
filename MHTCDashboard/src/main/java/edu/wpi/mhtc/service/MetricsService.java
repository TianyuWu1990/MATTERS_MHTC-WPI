package edu.wpi.mhtc.service;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.model.admin.AdminMetric;
import edu.wpi.mhtc.model.admin.TreeViewNode;
import edu.wpi.mhtc.model.Data.Metrics;
import edu.wpi.mhtc.persistence.DBMetric;

public abstract class MetricsService extends Service<DBMetric, Metrics>{

	public abstract List<AdminMetric> getMetricsForCategory(int categoryID);
	public abstract TreeViewNode getCategoriesAsTree();
	public abstract void storeCategory(String name, int parentId, String source);
	public abstract void storeMetric(int categoryId, String name, boolean isCalculated, String type);
	public abstract void updateCategory(int categoryId, String name, boolean visible, String source);
	public abstract void updateMetric(int metricId, String name, boolean visible, boolean isCalculated, String type);

	@Override
	public List<Method> getMethods()
	{
		Method[] methods = this.getClass().getDeclaredMethods();
		List<Method> result = new LinkedList<Method>();
		for(Method m : methods)
		{
			if (m.getReturnType().equals(Metrics.class))
			{
				result.add(m);
			}
		}
		return result;
	}
	
	
	
	
	public abstract List<DBMetric> getMetricsInCategory(int categoryId);
}
