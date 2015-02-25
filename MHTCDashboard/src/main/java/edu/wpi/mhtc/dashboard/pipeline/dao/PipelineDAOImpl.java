package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

}
