package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

public class StatisticDAOImpl implements StatisticDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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

	@Override
	public List<Statistic> getStatsByCategory(int categoryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
