/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
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

/**
 * Implementation of Pipeline DAO layer
 * @author Alex Fortier
 *
 */
@Repository
public class PipelineDAOImpl implements PipelineDAO {
	
	@Autowired private JdbcTemplate jdbcTemplate;

	@Override
	public void save(Pipeline pipeline) {
		
		String sql = "INSERT INTO mhtc_sch.pipelines(\"pipelinename\", \"pipelinedesc\", \"path\", \"filename\", \"uploadedby\") "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, pipeline.getPipelineName(), pipeline.getPipelineDesc(), pipeline.getPath().toString(), 
				pipeline.getFilename(), pipeline.getUploadedBy());

	}

	@Override
	public void delete(String pipelineName) {
		
		String sql = "DELETE FROM mhtc_sch.pipelines WHERE pipelinename = ?";
		
		jdbcTemplate.update(sql, pipelineName);
		
	}

	@Override
	public List<Pipeline> getAll() {
		String sql = "SELECT * FROM mhtc_sch.pipelines";
		
		List<Pipeline> pipelines = jdbcTemplate.query(sql, new RowMapper<Pipeline>() {

			@Override
			public Pipeline mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Pipeline(rs.getString("pipelineName"), rs.getString("pipelineDesc"), rs.getString("path"), 
						rs.getString("filename"), rs.getString("dateAdded"), rs.getString("uploadedby"));
			}
			
		});
		
		return pipelines;
	}

	@Override
	public Pipeline get(String pipelineName) {
		String sql = "SELECT * FROM mhtc_sch.pipelines WHERE pipelinename = ?";
		
		Object[] args = {pipelineName};
		
		return jdbcTemplate.query(sql, args, new ResultSetExtractor<Pipeline>() {

			@Override
			public Pipeline extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return new Pipeline(rs.getString("pipelinename"), rs.getString("pipelinedesc"), 
							rs.getString("path"), rs.getString("filename"), rs.getString("dateadded"), rs.getString("uploadedby"));
					
				}
				
				return null;
			}
			
		});
	}

}
