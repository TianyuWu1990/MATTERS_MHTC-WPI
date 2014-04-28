package edu.wpi.mhtc.persistence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.wpi.mhtc.model.Data.Metrics;
import edu.wpi.mhtc.service.MetricsService;

@Component
public class MetricMapper
{

	private List<DBMetric> metrics;

	@Autowired
	public MetricMapper(MetricsService service)
	{
		Metrics metrics = service.getAvailible();
		if (metrics != null)
		{
			this.metrics = metrics.getMetrics();
		}
	}

	/**
	 * Returns the first metric that matches the given string by id
	 * 
	 * @return The matching metric or null if nothing found
	 */
	public DBMetric getMetricByID(int id)
	{

		for (DBMetric metric : metrics)
		{
			if (metric.getId() == id)
			{
				return metric;
			}
		}

		return null;
	}

	/**
	 * Returns the first metric that matches the given string by name
	 * 
	 * @return The matching metric or null if nothing found
	 */
	public DBMetric getMetricByName(String name)
	{

		for (DBMetric metric : metrics)
		{
			if (metric.getName().equals(name))
			{
				return metric;
			}
		}

		return null;
	}

	/**
	 * Returns the first metric that matches the given string by name or id
	 * 
	 * @return The matching metric or null if nothing found
	 */
	public DBMetric getMetricFromString(String metric)
	{
		DBMetric dbMetric = this.getMetricByName(metric);
		if (dbMetric == null)
			try {
				dbMetric = this.getMetricByID(Integer.parseInt(metric));
			}
			catch (NumberFormatException e) {}
		
		return dbMetric;
	}

	/**
	 * Returns an unmodifiable list of all metrics in the mapper
	 */
	public List<DBMetric> getAll()
	{
		return Collections.unmodifiableList(metrics);
	}

	/**
	 * Returns a list of all metrics in a specific bin
	 * @param id The id of the bin to search for
	 */
	public List<DBMetric> getAllInBin(int id)
	{
		List<DBMetric> binMetrics = new LinkedList<DBMetric>();
		for (DBMetric metric : metrics)
		{
			if (metric.getBinId() == id)
				binMetrics.add(metric);
		}
		return binMetrics;
	}
}
