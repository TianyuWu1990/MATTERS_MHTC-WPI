/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

/**
 * Implementation of DAO for Statistics/Data
 * @author Alex Fortier
 *
 */
@Repository
public class StatisticDAOImpl implements StatisticDAO {
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	public StatisticDAOImpl() {}

	@Override
	public List<Statistic> getStatsByMetric(int metricID) {
		PSqlStringMappedJdbcCall<Statistic> call =
				new PSqlStringMappedJdbcCall<Statistic>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getdatabymetric");
		
		call.addDeclaredRowMapper(new PSqlRowMapper<Statistic>() {

			@Override
			public Statistic mapRow(SqlRowSet rs, int rowNum)
					throws SQLException {
				Statistic stat = new Statistic();
				
				stat.setStateName(rs.getString("StateName"));
				stat.setStateID(rs.getInt("StateId"));
				stat.setValue(rs.getDouble("Value"));
				stat.setYear(rs.getInt("Year"));
				
				return stat;
			}
			
		});
		
		call.addDeclaredParameter(new SqlParameter("metricid", Types.INTEGER));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metricid", metricID);
		
		List<Statistic> stats = call.execute(params);
		
		// Have to include the metricID into each Statistic object
		// Hard to do from the RowMapper
		for (Statistic s : stats) {
			s.setMetricID(metricID);
		}
		
		return stats;
	}

	@Override
	public List<Statistic> getStatsByState(int stateID) {
		PSqlStringMappedJdbcCall<Statistic> call =
				new PSqlStringMappedJdbcCall<Statistic>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getdatabystate");
		
		call.addDeclaredRowMapper(new PSqlRowMapper<Statistic>() {

			@Override
			public Statistic mapRow(SqlRowSet rs, int rowNum)
					throws SQLException {
				Statistic stat = new Statistic();
				
				stat.setMetricID(rs.getInt("Id"));
				stat.setValue(rs.getDouble("Value"));
				stat.setYear(rs.getInt("Year"));
				
				return stat;
			}
			
		});
		
		call.addDeclaredParameter(new SqlParameter("stateid", Types.INTEGER));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stateid", stateID);
		
		List<Statistic> stats = call.execute(params);
		
		// Have to include the stateID into each Statistic object
		// Hard to do from the RowMapper
		for (Statistic s : stats) {
			s.setStateID(stateID);
		}
		
		return stats;
	}

	@Override
	public List<Statistic> getStatsByMetricAndState(int metricID, int stateID) {
		PSqlStringMappedJdbcCall<Statistic> call =
				new PSqlStringMappedJdbcCall<Statistic>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getdatabymetricandstate");
		
		call.addDeclaredRowMapper(new PSqlRowMapper<Statistic>() {

			@Override
			public Statistic mapRow(SqlRowSet rs, int rowNum)
					throws SQLException {
				Statistic stat = new Statistic();
				
				stat.setValue(rs.getDouble("Value"));
				stat.setYear(rs.getInt("Year"));
				
				return stat;
			}
			
		});
		
		call.addDeclaredParameter(new SqlParameter("metricid", Types.INTEGER));
		call.addDeclaredParameter(new SqlParameter("stateid", Types.INTEGER));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metricid", metricID);
		params.put("stateid", stateID);
		
		List<Statistic> stats = call.execute(params);
		
		// Have to include the stateID and metricID into each Statistic object
		// Hard to do from the RowMapper
		for (Statistic s : stats) {
			s.setMetricID(metricID);
			s.setStateID(stateID);
		}
		
		return stats;
	}

	/**
	 * Return a list of statistics given a cateogry (does not look for subcategories!)
	 */
	@Override
	public List<Statistic> getStatsByCategory(int categoryID) {
		PSqlStringMappedJdbcCall<Statistic> call =
				new PSqlStringMappedJdbcCall<Statistic>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getdatabycategory");
		
		call.addDeclaredRowMapper(new PSqlRowMapper<Statistic>() {

			@Override
			public Statistic mapRow(SqlRowSet rs, int rowNum)
					throws SQLException {
				Statistic stat = new Statistic();
				
				stat.setMetricName(rs.getString("MetricName"));
				stat.setStateName(rs.getString("StateName"));
				stat.setValue(rs.getDouble("Value"));
				stat.setYear(rs.getInt("Year"));
				
				return stat;
			}
			
		});
		
		call.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryid", categoryID);

		return call.execute(params);
	}

	@Override
	public void merge(final Statistic stat) {
		String sql = "SELECT mhtc_sch.merge_statistics(?, ?, ?, ?)";
				
		jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				
				ps.setInt(1, stat.getStateID());
				ps.setInt(2, stat.getMetricID());
				ps.setInt(3, stat.getYear());
				ps.setDouble(4, stat.getValue());
				
				return ps.execute();
			}
			
		});
	}

	@Override
	public void save(Statistic stat) {
		String sql = "INSERT INTO mhtc_sch.statistics VALUES (?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, stat.getStateID(), stat.getMetricID(), stat.getYear(), stat.getValue());
	}

}
