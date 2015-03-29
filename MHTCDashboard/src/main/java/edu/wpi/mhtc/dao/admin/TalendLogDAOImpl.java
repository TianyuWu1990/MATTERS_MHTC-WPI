/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dao.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.wpi.mhtc.model.admin.TalendLog;

/**
 * Implementation of DAO layer for Talend Logs
 * @author Alex Fortier
 *
 */
@Repository
public class TalendLogDAOImpl implements TalendLogDAO {

    @Autowired private JdbcTemplate jdbcTemplate;

	@Override
	public void save(TalendLog log) {
		String sql = "INSERT INTO mhtc_sch.logs(job, code, message, moment, priority, origin) VALUES (?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, log.getJob(), log.getCode(), log.getMessage(), log.getMoment(), log.getPriority(), log.getOrigin());
	}

	@Override
	public List<TalendLog> get(String job) {
		String sql = "SELECT * FROM mhtc_sch.logs WHERE job = ?";
		
		Object[] args = {job};
		
		return jdbcTemplate.query(sql, args, new RowMapper<TalendLog>() {

			@Override
			public TalendLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new TalendLog(rs.getInt("id"), rs.getString("moment"), rs.getString("job"), 
						rs.getString("message"), rs.getInt("code"), rs.getInt("priority"), rs.getString("origin"));
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
