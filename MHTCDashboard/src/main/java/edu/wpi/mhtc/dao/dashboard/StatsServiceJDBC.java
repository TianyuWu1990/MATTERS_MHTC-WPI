/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dao.dashboard;

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

import edu.wpi.mhtc.model.dashboard.Data;
import edu.wpi.mhtc.model.dashboard.DataSeries;
import edu.wpi.mhtc.model.dashboard.Metric;
import edu.wpi.mhtc.model.dashboard.State;
import edu.wpi.mhtc.service.dashboard.MetricService;
import edu.wpi.mhtc.service.dashboard.StatsService;
import edu.wpi.mhtc.util.persistence.MetricMapper;
import edu.wpi.mhtc.util.persistence.PSqlRowMapper;
import edu.wpi.mhtc.util.persistence.PSqlStringMappedJdbcCall;
import edu.wpi.mhtc.util.persistence.StateMapper;

@Service
public class StatsServiceJDBC implements StatsService
{

	private JdbcTemplate template;
	private StateMapper stateMapper;
	private MetricMapper metricMapper;
	private MetricService metricsService;
	
	@Autowired
	public StatsServiceJDBC(JdbcTemplate template, StateMapper stateMapper, MetricMapper metricMapper, MetricService metricsService)
	{
		this.template = template;
		this.stateMapper = stateMapper;
		this.metricMapper = metricMapper;
		this.metricsService = metricsService;
	}

	private List<Data> getAllYearsForStateAndMetric(final State state, final Metric metric)
	{

		PSqlStringMappedJdbcCall<Data> call = new PSqlStringMappedJdbcCall<Data>(template).withSchemaName(
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
	public List<DataSeries> getDataForStateByName(String state, String metrics)
	{

		State dbState = stateMapper.getStateFromString(state);
		List<Metric> dbMetrics = getListOfMetricsFromCommaSeparatedString(metrics);

		return getDataForState(dbState, dbMetrics);
	}
	
	@Override
	/**
	 * Retrieve all metrics for this state and bin, includes all child categories of the bin. 
	 */
	public List<DataSeries> getStateBinData(String state, Integer binId) {

		State dbState = stateMapper.getStateFromString(state);
		List<Metric> metrics = metricsService.getMetricsFromParents(binId);
		
		return getDataForState(dbState, metrics);
	}

	private List<Metric> getListOfMetricsFromCommaSeparatedString(String metric)
	{

		if (metric.equals("all"))
			return metricMapper.getAll();

		String[] splits = metric.split(",");

		List<Metric> metrics = new LinkedList<Metric>();

		for (String split : splits)
		{
			Metric m = metricMapper.getMetricFromString(split);
			if(m!=null){
				metrics.add(m);
			}
		}

		return metrics;
	}
}
