package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;

/**
 * Service for Categories
 * @author Alex Fortier
 *
 */
@Service
public class CategoryService {
	
	@Autowired private CategoryDAO dao;
	
	/**
	 * Save a newly-created category
	 * @param category
	 */
	public void save(String categoryName, int parentid, String source, String url) {
		Category c = new Category();
		c.setName(categoryName);
		c.setParentId(parentid);
		c.setSource(source);
		c.setURL(url);
		
		dao.save(c);
	}
	
	/**
	 * Update a category that already exists
	 * @param category
	 */
	public void update(Category category) {
		if (category.getId() != 0) {
			dao.update(category);
		}
	}
	
	/**
	 * Delete a category
	 * @param ID
	 */
	public void delete(int ID) {
		// TODO
	}
	
	/**
	 * Get a category
	 * @param ID
	 * @return
	 */
	public Category get(int ID) {
		return dao.get(ID);
	}
	
	/**
	 * Get all categories
	 * @return
	 */
	public List<Category> getAll() {
		return dao.getAll();
	}
	
	/**
	 * Get all children categories for a parent
	 * @param parentId
	 * @return
	 */
	public List<Category> getChilrden(int parentId) {
		return dao.getChildren(parentId);
	}

}
