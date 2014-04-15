package edu.wpi.mhtc.persistence;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.wpi.mhtc.service.MetricsService;

@Component
public class MetricMapper {
	
	private List<DBMetric> metrics;

	@Autowired
	public MetricMapper(MetricsService service) {

		metrics = service.getAvailibleStatistics();
	}

	public DBMetric getMetricByID(int id) {

		for (DBMetric metric : metrics) {
			if (metric.getId() == id) {
				return metric;
			}
		}

		return null;
	}

	public DBMetric getMetricByName(String name) {

		for (DBMetric metric : metrics) {
			if (metric.getName().equals(name)) {
				return metric;
			}
		}

		return null;
	}
	
	public DBMetric getMetricFromString(String metric) {
		DBMetric dbMetric = this.getMetricByName(metric);
		if (dbMetric == null)
			dbMetric = this.getMetricByID(Integer.parseInt(metric));	
		
		return dbMetric;
	}
	
	public List<DBMetric> getAll() {
		return Collections.unmodifiableList(metrics);
	}
}
