package edu.wpi.mhtc.model.Data;

import java.util.List;

import edu.wpi.mhtc.model.Model;
import edu.wpi.mhtc.persistence.DBMetric;

/**
 * 
 * a model for DBMetrics
 * 
 * @author ted
 *
 */
public class Metrics implements Model<DBMetric>
{

	private final List<DBMetric> metrics;
	
	/**
	 * 
	 * @param metrics default metrics
	 */
	public Metrics(List<DBMetric> metrics)
	{
		this.metrics = metrics;
	}

	/**
	 * 
	 * @return the metrics
	 */
	public List<DBMetric> getMetrics()
	{
		return metrics;
	}

}
