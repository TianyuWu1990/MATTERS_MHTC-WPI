package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;

public class CategoryDAO implements DAO<Category> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public CategoryDAO() {}

	@Override
	public void save(Category object) {
		String sql = "SELECT mhtc_sch.insertcategory(?, ?, ?, ?)";
		jdbcTemplate.update(sql, object.getName(), object.getParentId(), 
				object.getSource(), object.getURL());
	}

	@Override
	public void update(Category object) {
		String sql = "SELECT mhtc_sch.updatecategory(?, ?, ?, ?)";
		jdbcTemplate.update(sql, object.getId(), object.getName(), 
				object.getVisible(), object.getSource());
		
	}

	@Override
	public void delete(int ID) {
		// TODO Auto-generated method stub
	}

	@Override
	public Category get(int ID) {
		String sql = "SELECT mhtc_sch.getCategoryByID(?)";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Category>() {

			@Override
			public Category extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if (rs.next()) {
					Category category = new Category();
					category.setId(rs.getInt("Id"));
					category.setName(rs.getString("Name"));
					category.setParentId(rs.getInt("ParentId"));
					category.setVisible(rs.getBoolean("Visible"));
					category.setSource(rs.getString("Source"));
				}
				
				return null;
			}
			
		});
		
	}

	@Override
	public List<Category> getAll() {
		String sql = "CALL mhtc_sch.getCategories(FALSE, NULL)";
		List<Category> listCategory = jdbcTemplate.query(sql, new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category aCategory = new Category();
								
				aCategory.setId(rs.getInt("Id"));
				aCategory.setName(rs.getString("Name"));
				aCategory.setParentId(rs.getInt("ParentId"));
				aCategory.setVisible(rs.getBoolean("Visible"));
				aCategory.setSource(rs.getString("Source"));
				
				return aCategory;
			}
			
			
		});
		
		return listCategory;
	}
	
	public List<Category> getChildren(int parentId) {
		String sql = "SELECT mhtc_sch.getCategories(FALSE, ?)";
		
		List<Category> listCategory = jdbcTemplate.query(sql, new Object[] {parentId}, new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category aCategory = new Category();
				
				aCategory.setId(rs.getInt("Id"));
				aCategory.setName(rs.getString("Name"));
				aCategory.setParentId(rs.getInt("ParentId"));
				aCategory.setVisible(rs.getBoolean("Visible"));
				aCategory.setSource(rs.getString("Source"));
				
				return aCategory;		
			}
			
		});
		
		return listCategory;
	}

}
