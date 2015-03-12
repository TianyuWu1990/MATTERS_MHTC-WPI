/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;

/**
 * Interface for Data Access Object
 * Used for Categories
 * @author Alex Fortier
 *
 */
public interface CategoryDAO {

		/**
		 * Insert category into database
		 * @param category
		 * TODO we may want to change this, as an category T won't be complete
		 * without an ID, which is given on entry to the database
		 */
		public void save(Category category);
		
		/**
		 * Update category in database
		 * @param category
		 */
		public void update(Category category);
		
		/**
		 * Delete category from database
		 * @param ID
		 */
		public void delete(int ID);
		
		/**
		 * Get category from database
		 * @param ID
		 * @return
		 */
		public Category get(int ID);
		
		/**
		 * Return list of all categories from database
		 * @return
		 */
		public List<Category> getAll();
		
		/**
		 * Get all children categories for given parent from database
		 * @param parentId
		 * @return
		 */
		public List<Category> getChildren(int parentId);
}
