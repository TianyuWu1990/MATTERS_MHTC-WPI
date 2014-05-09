package edu.wpi.mhtc.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.model.Data.Data;
import edu.wpi.mhtc.model.Data.DataSource;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBMetric;
import edu.wpi.mhtc.persistence.DBState;
import edu.wpi.mhtc.persistence.MetricMapper;
import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;
import edu.wpi.mhtc.persistence.StateMapper;

@Service
public class StatsServiceJDBC extends StatsService
{

	private JdbcTemplate template;
	private StateMapper stateMapper;
	private MetricMapper metricMapper;
	private MetricsService metricsService;
	
	private int[] tabids = { 91, 92, 93, 94, 95, 32, 29, 30, 25, 34, 28, 33, 27, 31, 61, 65, 66, 67, 70, 71, 73 };
	private int[] swaptrendids = { 96, 97 };
	
	
	@Autowired
	public StatsServiceJDBC(JdbcTemplate template, StateMapper stateMapper, MetricMapper metricMapper, MetricsService metricsService)
	{
		this.template = template;
		this.stateMapper = stateMapper;
		this.metricMapper = metricMapper;
		this.metricsService = metricsService;
	}

	@Override
	protected List<DataPoint> getAllYearsForStateAndMetric(final DBState state, final DBMetric metric)
	{

		PSqlStringMappedJdbcCall<DataPoint> call = new PSqlStringMappedJdbcCall<DataPoint>(template).withSchemaName(
				"mhtc_sch").withProcedureName("getdatabymetricandstate");

		call.addDeclaredRowMapper(new PSqlRowMapper<DataPoint>()
			{
				@Override
				public DataPoint mapRow(SqlRowSet rs, int rowNum) throws SQLException
				{
					return new DataPoint(state, metric, rs.getInt("Year"), rs.getDouble("Value"));
				}
			});

		call.addDeclaredParameter(new SqlParameter("stateid", Types.INTEGER));
		call.addDeclaredParameter(new SqlParameter("metricid", Types.INTEGER));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stateid", state.getId());
		params.put("metricid", metric.getId());

		return call.execute(params);

	}

	@Override
	protected State getDataForState(DBState state, List<DBMetric> metrics)
	{
		LinkedList<DataSource> sources = new LinkedList<DataSource>();

		for (DBMetric metric : metrics)
		{
			List<DataPoint> points = getAllYearsForStateAndMetric(state, metric);

			String trend = "none";
			if (points.size() > 0) {
				DataPoint recent = points.get(0);
				DataPoint old = points.get(0);
				for (DataPoint datapoint : points) {
					if (datapoint.getYear() > recent.getYear()) {
						recent = datapoint;
					}
				}
				for (DataPoint datapoint : points) {
				
					if (datapoint.getYear() < old.getYear() && recent.getYear() - datapoint.getYear() <= 5) {
						old = datapoint;
					}
				}
				// TODO calculate this depending on the datatype and do a real calculation
				double diff = recent.getValue() - old.getValue();
				
				boolean swap = false;
	            for (int i = 0; i < swaptrendids.length; i++)
	                if (swaptrendids[i] == metric.getId())
	                    swap = true;
				
	            if (swap)
	                   //diff = diff - 1;
	            
				if (diff > 0) {
					trend = "up";
				} else if (diff < 0) {
					trend = "down";
				} else {
				    trend = "no_change";
				}
				
			}

			// TODO delete this crap once we no longer care about this tab idiocy
			boolean tabbed = false;
			for (int i = 0; i < tabids.length; i++)
			    if (tabids[i] == metric.getId())
			        tabbed = true;
			
			DataSource source = new DataSource(metric.getId(), metric.getName(), metric.getURL(), trend, metric.getSource(), metric.getBinName(), tabbed);
			
			for (DataPoint datapoint : points)
			{
				try
				{
					double value = datapoint.getValue();
					if (metric.getDataType().equals("percentage")) {
						value *= 100;
						BigDecimal bd = new BigDecimal(value);
					    bd = bd.setScale(2, RoundingMode.HALF_UP);
					    value = bd.doubleValue();
					} else {
						
					}
					
					source.addData(new Data(datapoint.getYear(), value));
				}
				catch (Exception e)
				{
					//TODO log it!
				}
			}
			sources.add(source.sort());
		}

		return new State(state, sources);
	}

	@Override
	public State getDataForStateByName(String state, String metrics)
	{

		DBState dbState = stateMapper.getStateFromString(state);
		List<DBMetric> dbMetrics = getListOfMetricsFromCommaSeparatedString(metrics);

		return getDataForState(dbState, dbMetrics);
	}
	
	
	

	@Override
	public State getStateBinData(String state, Integer binId)
	{

		DBState dbState = stateMapper.getStateFromString(state);
		List<DBMetric> dbMetrics = metricsService.getMetricsInCategory(binId);

		return getDataForState(dbState, dbMetrics);
	}

	@Override
	public List<DBMetric> getListOfMetricsFromCommaSeparatedString(String metric)
	{

		if (metric.equals("all"))
			return metricMapper.getAll();

		String[] splits = metric.split(",");

		List<DBMetric> metrics = new LinkedList<DBMetric>();

		for (String split : splits)
		{
			metrics.add(metricMapper.getMetricFromString(split));
		}

		return metrics;
	}

	

	
	@Override
	public State invokeThis(Method m, Object[] params)
	{
		try
		{
			return (State) m.invoke(this, params);
		}
		catch (Exception e)
		{
			return null;
		}
	}

}
