/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dao.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import edu.wpi.mhtc.model.admin.Metric;
import edu.wpi.mhtc.util.persistence.PSqlRowMapper;
import edu.wpi.mhtc.util.persistence.PSqlStringMappedJdbcCall;

/**
 * Implementation of DAO layer for Metric
 * @author Alex Fortier
 *
 */
@Repository
public class MetricDAOImpl implements MetricDAO {

    @Autowired private JdbcTemplate jdbcTemplate;
	
    public MetricDAOImpl() {}
	
	@Override
	public void save(Metric object) {
        PSqlStringMappedJdbcCall<Integer> call = 
        		new PSqlStringMappedJdbcCall<Integer>(jdbcTemplate)
        		.withSchemaName("mhtc_sch")
        		.withProcedureName("insertmetric");
       
        call.addDeclaredParameter(new SqlParameter("metricname", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("iscalculated", Types.BOOLEAN));
        call.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
        call.addDeclaredParameter(new SqlParameter("datatype", Types.VARCHAR));
        
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put("metricname", object.getName());
        params.put("iscalculated", object.isCalculated());
        params.put("categoryid", object.getCategoryId());
        params.put("datatype", object.getDataType());

        call.execute(params);		
	}

	@Override
	public void update(Metric object) {
        PSqlStringMappedJdbcCall<Integer> call = 
        		new PSqlStringMappedJdbcCall<Integer>(jdbcTemplate)
        		.withSchemaName("mhtc_sch")
                .withProcedureName("updatemetric");

        call.addDeclaredParameter(new SqlParameter("metricid", Types.INTEGER));
        call.addDeclaredParameter(new SqlParameter("mname", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("visible", Types.BOOLEAN));
        call.addDeclaredParameter(new SqlParameter("iscalculated", Types.BOOLEAN));
        call.addDeclaredParameter(new SqlParameter("datatype", Types.VARCHAR));

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("metricid", object.getId());
        params.put("mname", object.getName());
        params.put("visible", object.isVisible());
        params.put("iscalculated", object.isCalculated());
        params.put("datatype", object.getDataType());

        call.execute(params);
	}

	@Override
	public void delete(int ID) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Get all metrics given the category
	 * TODO only returns VISIBLE metrics
	 * @param categoryID
	 * @return
	 */
	public List<Metric> getMetricsForCategory(int categoryID) {
		PSqlStringMappedJdbcCall<Metric> call = 
				new PSqlStringMappedJdbcCall<Metric>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getmetrics");
		
		call.addDeclaredRowMapper(new MetricMapper());
		
		call.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
		call.addDeclaredParameter(new SqlParameter("showall", Types.BOOLEAN));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryid", categoryID);
		params.put("showall", false);
		
		return call.execute(params);

	}
	
	/**
	 * Retrieve all metrics given a list of categories
	 * @param categoryIDs
	 * @return
	 */
	public List<Metric> getMetricsForCategories(Integer[] categoryIDs) {
        PSqlStringMappedJdbcCall<Metric> call = 
        		new PSqlStringMappedJdbcCall<Metric>(jdbcTemplate)
                .withSchemaName("mhtc_sch")
                .withProcedureName("getmetricsbyparent");

        call.addDeclaredRowMapper(new MetricMapper());

        call.addDeclaredParameter(new SqlParameter("parentids", Types.ARRAY));
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("parentids", categoryIDs);

        return call.execute(params);
	}
	
	/**
	 * Retrieve all metrics in the database
	 */
	@Override
	public List<Metric> getAll() {		
		String sql = "SELECT m.\"Name\" AS \"metricName\", m.\"DisplayName\", m.\"Source\", m.\"URL\", m.\"Visible\", "
				+ "m.\"IsCalculated\", m.\"DataType\", cxm.\"metricId\", cxm.\"categoryId\", c.\"Name\" AS \"categoryName\" "
				+ "FROM mhtc_sch.metrics m "
    			+ "INNER JOIN mhtc_sch.categoriesxmetrics cxm ON m.\"Id\" = cxm.\"metricId\" "
    			+ "INNER JOIN mhtc_sch.categories c ON cxm.\"categoryId\" = c.\"Id\"";
		
		List<Metric> metrics = jdbcTemplate.query(sql, new RowMapper<Metric>() {

			@Override
			public Metric mapRow(ResultSet rs, int rowNum) throws SQLException {
				Metric metric = new Metric();
				
				metric.setId(rs.getInt("metricId"));
				metric.setName(rs.getString("metricName"));
				metric.setVisible(rs.getBoolean("Visible"));
				metric.setCalculated(rs.getBoolean("IsCalculated"));
				metric.setDataType(rs.getString("DataType"));
				metric.setDisplayName(rs.getString("DisplayName"));
				metric.setURL(rs.getString("URL"));
				metric.setSource(rs.getString("Source"));
				metric.setCategoryId(rs.getInt("categoryId"));
				metric.setCategoryName(rs.getString("categoryName"));
				
				return metric;
			}
			
		});
		
		return metrics;
	}
	
	/**
	 * Gets metric DataType column information
	 */
	@Override
	public Set<String> getMetricDataTypes() {
		String sql = "SELECT \"DataType\" FROM mhtc_sch.metrics";

		List<String> dataTypes = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("DataType");
			}
			
		});
		
		return new HashSet<>(dataTypes);
	}

	
	/**
	 * Predefined RowMapper for Metric
	 * @author Alex Fortier
	 *
	 */
	private static class MetricMapper implements PSqlRowMapper<Metric> {
		
		@Override
		public Metric mapRow(SqlRowSet rs, int rowNum) throws SQLException {
			Metric m = new Metric();
			
			m.setId(rs.getInt("Id"));
			m.setName(rs.getString("Name"));
			m.setVisible(rs.getBoolean("Visible"));
			m.setCalculated(rs.getBoolean("IsCalculated"));
			m.setDataType(rs.getString("DataType"));
			m.setDisplayName(rs.getString("DisplayName"));
			m.setURL(rs.getString("URL"));
			m.setSource(rs.getString("Source"));
			
			return m;
		}

	}
	
}
