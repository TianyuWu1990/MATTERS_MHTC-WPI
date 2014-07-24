package edu.wpi.mhtc.service;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.Data.MetricRowMapper;
import edu.wpi.mhtc.persistence.JdbcProcedure;

// TODO cached service
@Service
public class MetricServiceJdbc implements MetricService
{

	private JdbcProcedure<Metric> getMetrics;
	private JdbcProcedure<Metric> getMetricsByCategory;
	private JdbcProcedure<Metric> getMetricById;

	public static final String MHTC_SCHEMA = "mhtc_sch";

	@Autowired
	public MetricServiceJdbc(JdbcTemplate template)
	{
		getMetrics = new JdbcProcedure<Metric>(template).withSchemaName(MHTC_SCHEMA).withProcedureName("getMetrics")
				.addDeclaredParameter(new SqlParameter("showOnlyVisible", Types.BOOLEAN))
				.addDeclaredRowMapper(new MetricRowMapper());

		getMetricsByCategory = new JdbcProcedure<Metric>(template).withSchemaName(MHTC_SCHEMA)
				.withProcedureName("getMetricsByCategory")
				.addDeclaredParameter(new SqlParameter("showOnlyVisible", Types.BOOLEAN))
				.addDeclaredParameter(new SqlParameter("categoryId", Types.INTEGER))
				.addDeclaredRowMapper(new MetricRowMapper());
		
		getMetricById = new JdbcProcedure<Metric>(template).withSchemaName(MHTC_SCHEMA)
				.withProcedureName("getMetricById")
				.addDeclaredParameter(new SqlParameter("metricId", Types.INTEGER))
				.addDeclaredRowMapper(new MetricRowMapper());
	}

	@Override
	public List<Metric> getAllMetrics()
	{
		return getMetrics.createCall().withParam("showOnlyVisible", false).call();
	}

	@Override
	public List<Metric> getAllVisibleMetrics()
	{
		return getMetrics.createCall().withParam("showOnlyVisible", true).call();
	}

	@Override
	public List<Metric> getAllMetricsByCategory(int categoryId)
	{
		return getMetricsByCategory.createCall().withParam("showOnlyVisible", false).withParam("categoryId", categoryId).call();
	}

	@Override
	public List<Metric> getAllVisibleMetricsByCategory(int categoryId)
	{
		return getMetricsByCategory.createCall().withParam("showOnlyVisible", true).withParam("categoryId", categoryId).call();
	}

	@Override
	public Metric getMetricById(int id)
	{
		return getMetricById.createCall().withParam("metricId", id).call().get(0);
	}

	@Override
	public Metric getMetricByName(String name)
	{
		throw new NotImplementedException();
	}

	@Override
	public Metric getMetricByDisplayName(String name)
	{
		throw new NotImplementedException();
	}

	@Override
	public void saveMetric(Metric metric)
	{
		throw new NotImplementedException();
	}

}
