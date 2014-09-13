package edu.wpi.mhtc.persistence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.service.MetricService;

@Component
public class MetricMapper
{
	private List<Metric> metrics;

	@Autowired
	public MetricMapper(MetricService service)
	{
		metrics = service.getAllMetrics();
	}

	/**
	 * Returns the first metric that matches the given string by id
	 * 
	 * @return The matching metric or null if nothing found
	 */
	public Metric getMetricByID(int id)
	{

		for (Metric metric : metrics)
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
	public Metric getMetricByName(String name)
	{

		for (Metric metric : metrics)
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
	public Metric getMetricFromString(String metric)
	{
	    Metric dbMetric = this.getMetricByName(metric);
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
	public List<Metric> getAll()
	{
		return Collections.unmodifiableList(metrics);
	}

	/**
	 * Returns a list of all metrics in a specific bin
	 * @param id The id of the bin to search for
	 */
	public List<Metric> getAllInBin(int id)
	{
		List<Metric> binMetrics = new LinkedList<Metric>();
		for (Metric metric : metrics)
		{
			if (metric.getBinId() == id)
				binMetrics.add(metric);
		}
		return binMetrics;
	}
}

