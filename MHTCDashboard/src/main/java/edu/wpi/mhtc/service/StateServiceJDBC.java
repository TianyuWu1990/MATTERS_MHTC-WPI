package edu.wpi.mhtc.service;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.model.state.StateRowMapper;
import edu.wpi.mhtc.persistence.JdbcProcedure;

// TODO cached service
@Service
public class StateServiceJDBC implements StateService
{

	private JdbcProcedure<State> getStates;
	private JdbcProcedure<State> getTopTenStates;
	private JdbcProcedure<State> getBottomTenStates;
	private JdbcProcedure<State> getStateById;

	@Autowired
	public StateServiceJDBC(JdbcTemplate template)
	{
		getStates = new JdbcProcedure<State>(template).withSchemaName(MetricServiceJdbc.MHTC_SCHEMA)
				.withProcedureName("getStates").addDeclaredParameter(new SqlParameter("onlyPeers", Types.BOOLEAN))
				.addDeclaredRowMapper(new StateRowMapper());

		getTopTenStates = new JdbcProcedure<State>(template).withSchemaName(MetricServiceJdbc.MHTC_SCHEMA)
				.withProcedureName("getTopTenStates").addDeclaredParameter(new SqlParameter("metricId", Types.INTEGER))
				.addDeclaredRowMapper(new StateRowMapper());

		getBottomTenStates = new JdbcProcedure<State>(template).withSchemaName(MetricServiceJdbc.MHTC_SCHEMA)
				.withProcedureName("getBottomTenStates")
				.addDeclaredParameter(new SqlParameter("metricId", Types.INTEGER))
				.addDeclaredRowMapper(new StateRowMapper());

		getStateById = new JdbcProcedure<State>(template).withSchemaName(MetricServiceJdbc.MHTC_SCHEMA)
				.withProcedureName("getStateById").addDeclaredParameter(new SqlParameter("stateId", Types.INTEGER))
				.addDeclaredRowMapper(new StateRowMapper());
	}

	@Override
	public List<State> getAllPeers()
	{
		return getStates.createCall().withParam("onlyPeers", true).call();
	}

	@Override
	public List<State> getAllStates()
	{
		return getStates.createCall().withParam("onlyPeers", false).call();
	}

	@Override
	public List<State> getTopTenStatesForMetric(Metric metric)
	{
		return getTopTenStates.createCall().withParam("metricId", metric.getId()).call();
	}

	@Override
	public List<State> getBottomTenStatesForMetric(Metric metric)
	{
		return getBottomTenStates.createCall().withParam("metricId", metric.getId()).call();
	}

	@Override
	public State getStateById(int id)
	{
		return getStateById.createCall().withParam("stateId", id).call().get(0);
	}

}
