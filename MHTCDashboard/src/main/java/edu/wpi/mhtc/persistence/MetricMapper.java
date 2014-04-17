package edu.wpi.mhtc.persistence;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.wpi.mhtc.dashboard.util.DashboardUtils;
import edu.wpi.mhtc.dashboard.util.GenericPredicate;
import edu.wpi.mhtc.service.MetricsService;

/**
 * A class that caches metric metadata in the server application and allows for
 * easy access. Since the metric metadata doesn't change frequently and isn't
 * particularly large, this can be used to reduce the number of trips to the
 * database. <br>
 * If, in the future the metadata does change frequently or the number of rows
 * becomes large, caching this information will no longer be a preferable
 * approach, and we should query the database for each metric metadata
 * retrieval.
 * 
 * @author Jeffrey Stokes
 * 
 */
@Component
public class MetricMapper {

    private List<DBMetric> metrics;
    private MetricsService service;

    /**
     * Creates a new MetricMapper using the given MetricsService as a JDBC
     * wrapper. Except for testing purposes, this should only be called by the
     * spring container.
     */
    @Autowired
    public MetricMapper(MetricsService service) {
        this.service = service;
        refresh();
    }

    /**
     * Will re-query the database for all metrics, discarding the current cache
     * and loading a fresh copy. Should be called whenever there are changes to
     * the metric metadata.
     */
    public void refresh() {
        metrics = service.getAvailibleStatistics();
    }

    /**
     * Returns a cached metric with the given id.
     * 
     * @return The metric with the given id, or null if there is none.
     */
    public DBMetric getMetricByID(final int id) {
        return DashboardUtils.find(metrics, new GenericPredicate<DBMetric>() {
            @Override
            public boolean evaluate(DBMetric object) {
                return object.getId() == id;
            }
        });
    }

    /**
     * Returns a cached metric with the given name.
     * 
     * @return The metric with the given name, or null if there is none.
     */
    public DBMetric getMetricByName(final String name) {
        return DashboardUtils.find(metrics, new GenericPredicate<DBMetric>() {
            @Override
            public boolean evaluate(DBMetric object) {
                return object.getName().equals(name);
            }
        });
    }

    /**
     * Attempts to find a metric that somehow matches the given string.
     * 
     * @param metric
     *            A metric name or metric id.
     * @return A matching metric or false otherwise.
     */
    public DBMetric getMetricFromString(final String metric) {
        return DashboardUtils.find(metrics, new GenericPredicate<DBMetric>() {
            @Override
            public boolean evaluate(DBMetric object) {
                // This relies on short circuit evaluation to not throw an
                // exception
                return object.equals(metric) || DashboardUtils.integerTryParse(metric)
                        && Integer.parseInt(metric) == object.getId();
            }
        });
    }

    /**
     * Gets all the metrics in the cache.
     * 
     * @return An unmodifable list, changes to this list will not affect the
     *         cache. The cache can only be changed by refreshing from the
     *         database.
     */
    public List<DBMetric> getAll() {
        return Collections.unmodifiableList(metrics);
    }
}
