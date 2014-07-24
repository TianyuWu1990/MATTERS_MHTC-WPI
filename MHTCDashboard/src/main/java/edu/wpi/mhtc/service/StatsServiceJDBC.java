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
import edu.wpi.mhtc.model.Data.DataSeries;
import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.JdbcProcedure;

@Service
public class StatsServiceJDBC implements StatsService
{

	private JdbcTemplate template;
	private StateService stateService;
	private MetricService metricService;
	
	@Autowired
	public StatsServiceJDBC(JdbcTemplate template, StateService stateService, MetricService metricService)
	{
		this.template = template;
		this.stateService = stateService;
		this.metricService = metricService;
	}

	private List<Data> getAllYearsForStateAndMetric(final State state, final Metric metric)
	{

		JdbcProcedure<Data> call = new JdbcProcedure<Data>(template).withSchemaName(
				"mhtc_sch").withProcedureName("getdatabymetricandstate");

		call.addDeclaredRowMapper(new PSqlRowMapper<Data>()
			{
				@Override
				public Data mapRow(SqlRowSet rs, int rowNum) throws SQLException
				{
					return new Data(rs.getInt("Year"), rs.getDouble("Value"));
				}
			});

		call.addDeclaredParameter(new SqlParameter("stateid", Types.INTEGER));
		call.addDeclaredParameter(new SqlParameter("metricid", Types.INTEGER));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stateid", state.getId());
		params.put("metricid", metric.getId());

		return call.execute(params);

	}

	private List<DataSeries> getDataForState(State state, List<Metric> metrics)
	{
		List<DataSeries> series = new LinkedList<DataSeries>();

		for (Metric metric : metrics)
		{
			List<Data> points = getAllYearsForStateAndMetric(state, metric);
			DataSeries single = new DataSeries(metric, state);
			single.addData(points);

			series.add(single);
		}

		return series;
	}

	@Override
	public List<DataSeries> getDataForStateByName(String stateId, String metricIds)
	{

		State state = stateService.getStateById(Integer.parseInt(stateId));
		List<Metric> metrics = getListOfMetricsFromCommaSeparatedString(metricIds);

		return getDataForState(state, metrics);
	}
	
	@Override
	public List<DataSeries> getStateBinData(String stateId, Integer binId)
	{

		State state = stateService.getStateById(Integer.parseInt(stateId));
		List<Metric> metrics = metricService.getAllVisibleMetricsByCategory(binId);

		return getDataForState(state, metrics);
	}

	private List<Metric> getListOfMetricsFromCommaSeparatedString(String metric)
	{

		if (metric.equals("all"))
			return metricService.getAllVisibleMetrics();

		String[] splits = metric.split(",");

		List<Metric> metrics = new LinkedList<Metric>();

		for (String split : splits)
		{
			metrics.add(metricService.getMetricById(Integer.parseInt(split)));
		}

		return metrics;
	}
}
