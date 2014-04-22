package edu.wpi.mhtc.service;

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
			// TODO get proper values for the datasources
			DataSource source = new DataSource(metric.getName(), "", "NA", "");
			List<DataPoint> points = getAllYearsForStateAndMetric(state, metric);

			for (DataPoint datapoint : points)
			{
				source.addData(new Data(datapoint.getYear(), (int) datapoint.getValue()));
			}
			sources.add(source);
		}

		return sources;
	}

	@Override
	public State getDataForState(String state, String metrics)
	{

		DBState dbState = stateMapper.getStateFromString(state);
		List<DBMetric> dbMetrics = getListOfMetricsFromCommaSeparatedString(metrics);

		List<DataSource> sources = getDataForState(dbState, dbMetrics);

		return new State(dbState.getName(), dbState.getInitial(), sources.toArray(new DataSource[1]));

	}

	@Override
	public State getStateBinData(String state, int binId)
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

}
