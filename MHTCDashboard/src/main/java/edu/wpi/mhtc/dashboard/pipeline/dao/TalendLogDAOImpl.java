package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TalendLogDAOImpl implements TalendLogDAO {

    @Autowired private JdbcTemplate jdbcTemplate;
	
    public TalendLogDAOImpl() {}

	@Override
	public void save(TalendLog log) {
		String sql = "INSERT INTO mhtc_sch.logs(job, code, message, moment, priority, origin) VALUES (?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, log.getJob(), log.getCode(), log.getMessage(), log.getMoment(), log.getPriority(), log.getOrigin());
	}

	@Override
	public TalendLog get(String job) {
		String sql = "SELECT * FROM mhtc_sch.logs WHERE job = ?";
		
		Object[] args = {job};
		
		return jdbcTemplate.query(sql, args, new ResultSetExtractor<TalendLog>() {

			@Override
			public TalendLog extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return new TalendLog(rs.getInt("id"), rs.getString("moment"), rs.getString("job"), 
							rs.getString("message"), rs.getInt("code"), rs.getInt("priority"), rs.getString("origin"));
				}
				
				return null;
			}
			
		});
	}

	@Override
	public List<TalendLog> getSummary() {
		String sql = "SELECT message, job, recent_tbl.log_count FROM mhtc_sch.logs "
				+ "RIGHT JOIN (SELECT COUNT(*) as log_count, MAX(id) as recent_id FROM mhtc_sch.logs GROUP BY job) as recent_tbl ON recent_id = id;"; 

		List<TalendLog> logs = jdbcTemplate.query(sql, new RowMapper<TalendLog>() {

			@Override
			public TalendLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				TalendLog log = new TalendLog();
				
				log.setMessage(rs.getString("message"));
				log.setJob(rs.getString("job"));
				log.setLogCount(rs.getInt("log_count"));
				
				return log;
			}
			
		});
		
		return logs;
	}

}
