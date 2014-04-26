package edu.wpi.mhtc.service;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.model.Data.Metrics;
import edu.wpi.mhtc.persistence.DBMetric;

public abstract class MetricsService extends Service<DBMetric, Metrics>{

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
