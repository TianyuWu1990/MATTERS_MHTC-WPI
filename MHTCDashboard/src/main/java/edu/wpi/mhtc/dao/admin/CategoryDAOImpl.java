/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dao.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import edu.wpi.mhtc.model.admin.Category;
import edu.wpi.mhtc.util.persistence.PSqlRowMapper;
import edu.wpi.mhtc.util.persistence.PSqlStringMappedJdbcCall;

/**
 * Category Data Access Object class for interface with DB
 * TODO Maybe combine save() and update() into one function?
 * @author Alex Fortier
 *
 */
@Repository
public class CategoryDAOImpl implements CategoryDAO {
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	public CategoryDAOImpl() {}

	@Override
	public void save(Category object) {
        PSqlStringMappedJdbcCall<Integer> call = 
        		new PSqlStringMappedJdbcCall<Integer>(jdbcTemplate)
        		.withSchemaName("mhtc_sch")
        		.withProcedureName("insertcategory");
       
        call.addDeclaredParameter(new SqlParameter("categname", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("parentid", Types.INTEGER));
        call.addDeclaredParameter(new SqlParameter("source", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("url", Types.VARCHAR));
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categname", object.getName());

        int parentId = object.getParentId();
        if (parentId == -1) {
            params.put("parentid", null);
        } else {
            params.put("parentid", parentId);
        }
        
        params.put("source", object.getSource());
        params.put("url", object.getURL());

        call.execute(params);
	}

	@Override
	public void update(Category object) {
        PSqlStringMappedJdbcCall<Integer> call = 
        		new PSqlStringMappedJdbcCall<Integer>(jdbcTemplate)
        		.withSchemaName("mhtc_sch")
                .withProcedureName("updatecategory");

        call.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
        call.addDeclaredParameter(new SqlParameter("cname", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("visible", Types.BOOLEAN));
        call.addDeclaredParameter(new SqlParameter("source", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("url", Types.VARCHAR));

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("categoryid", object.getId());
        params.put("cname", object.getName());
        params.put("visible", object.getVisible());
        params.put("source", object.getSource());
        params.put("url", object.getURL());

        call.execute(params);
	}

	@Override
	public void delete(int ID) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Category get(int ID) {
		String sql = "SELECT * FROM mhtc_sch.categories WHERE \"Id\" = ?";
		
		Object[] args = {ID};
		
		return jdbcTemplate.query(sql, args, new ResultSetExtractor<Category>() {

			@Override
			public Category extractData(ResultSet rs) throws SQLException, DataAccessException {

				if (rs.next()) {
					Category cat = new Category();
					
					cat.setId(rs.getInt("Id"));
					cat.setName(rs.getString("Name"));
					cat.setParentId(rs.getInt("ParentId"));
					cat.setSource(rs.getString("Source"));
					cat.setURL(rs.getString("URL"));
					cat.setVisible(rs.getBoolean("Visible"));
					
					return cat;
				}
				
				return null;
			}
			
		});
		
	}

	@Override
	public List<Category> getAll() {
		PSqlStringMappedJdbcCall<Category> categoryCall = 
				new PSqlStringMappedJdbcCall<Category>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getCategories");
		
		categoryCall.addDeclaredRowMapper(new CategoryMapper());
		
		categoryCall.addDeclaredParameter(new SqlParameter("showall", Types.BOOLEAN));
		categoryCall.addDeclaredParameter(new SqlParameter("parentid", Types.INTEGER));
		
		Map<String, Object> categoryParams = new HashMap<String, Object>();
		categoryParams.put("showall", false);
		categoryParams.put("parentid", null);
		
		return categoryCall.execute(categoryParams);
	}
	
	/**
	 * Get all Categories that have the given parentId
	 * @param parentId
	 * @return
	 */
	public List<Category> getChildren(int parentId) {
		PSqlStringMappedJdbcCall<Category> categoryCall =
				new PSqlStringMappedJdbcCall<Category>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getCategories");
		
		categoryCall.addDeclaredRowMapper(new CategoryMapper());
		
		categoryCall.addDeclaredParameter(new SqlParameter("showall", Types.BOOLEAN));
		categoryCall.addDeclaredParameter(new SqlParameter("parentid", Types.INTEGER));
		
		Map<String, Object> categoryParams = new HashMap<String, Object>();
		categoryParams.put("showall", false);
		categoryParams.put("parentid", parentId);
		
		return categoryCall.execute(categoryParams);
	}
	
	/**
	 * Predefined RowMapper for Category only
	 * @author Alex Fortier
	 *
	 */
	private static class CategoryMapper implements PSqlRowMapper<Category> {

		@Override
		public Category mapRow(SqlRowSet rs, int rowNum) throws SQLException {
			Category cat = new Category();
			
			cat.setId(rs.getInt("Id"));
			cat.setName(rs.getString("Name"));
			cat.setParentId(rs.getInt("ParentId"));
			cat.setSource(rs.getString("Source"));
			cat.setURL(rs.getString("URL"));
			cat.setVisible(rs.getBoolean("Visible"));
			
			return cat;
		}
		
	}
	
}
