package edu.wpi.mhtc.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class StatsServiceJDBC implements StatsService
{

	private JdbcTemplate template;
	private StateMapper stateMapper;
	private MetricMapper metricMapper;
	private MetricsService metricsService;

	@Autowired
	public StatsServiceJDBC(JdbcTemplate template, StateMapper stateMapper, MetricMapper metricMapper, MetricsService metricsService)
	{
		this.template = template;
		this.stateMapper = stateMapper;
		this.metricMapper = metricMapper;
		this.metricsService = metricsService;
	}

	private List<DataPoint> getAllYearsForStateAndMetric(final DBState state, final DBMetric metric)
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

	private List<DataSource> getDataForState(DBState state, List<DBMetric> metrics)
	{
		List<DataSource> sources = new LinkedList<DataSource>();

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
				if (diff > 0) {
					trend = "up";
				} else if (diff < 0) {
					trend = "down";
				}
				
			}

			DataSource source = new DataSource(metric.getName(), metric.getURL(), trend, metric.getSource(), metric.getBinName());
			
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
			sources.add(source);
		}

		return sources;
	}

	
	public State getDataForState(String state, String metrics)
	{

		DBState dbState = stateMapper.getStateFromString(state);
		List<DBMetric> dbMetrics = getListOfMetricsFromCommaSeparatedString(metrics);

		List<DataSource> sources = getDataForState(dbState, dbMetrics);

		return new State(dbState.getName(), dbState.getInitial(), sources.toArray(new DataSource[1]));

	}
	
	
	

	@Override
	public State getStateBinData(String state, Integer binId)
	{

		DBState dbState = stateMapper.getStateFromString(state);
		List<DBMetric> dbMetrics = metricsService.getMetricsInCategory(binId);
		
		List<DataSource> sources = getDataForState(dbState, dbMetrics);

		return new State(dbState.getName(), dbState.getInitial(), sources.toArray(new DataSource[1]));		
	}

	private List<DBMetric> getListOfMetricsFromCommaSeparatedString(String metric)
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
	public State getAvailible(Object... params)
	{
		if (params.length == 0)
		{
			return null;
		}
		else
		{
			Method[] methods = this.getClass().getMethods();
			for(Method m : methods)
			{
				if (m.getName().equals(params[0]) && m.getReturnType().equals(State.class))
				{
					Object[] newParams = new Object[params.length - 1];
					for(int i=0;i<newParams.length; i++)
					{
						newParams[i] = params[i+1];
					}
					try
					{
						return (State) m.invoke(this, newParams);
					}
					catch (Exception e)
					{
						return null;
					}
				}
			}
			return null;
		}
	}

}
