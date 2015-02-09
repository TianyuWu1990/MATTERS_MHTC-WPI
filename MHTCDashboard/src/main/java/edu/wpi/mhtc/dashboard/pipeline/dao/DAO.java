package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

/**
 * Interface for Data Access Object
 * Used for Categories, Metrics, and MetricValues
 * @author Alex Fortier
 *
 * @param <T> For whichever model you'd like to get from the DB
 */
public interface DAO<T> {

		/**
		 * Insert object into database
		 * @param object
		 */
		public void save(T object);
		
		/**
		 * Update object in database
		 * @param object
		 */
		public void update(T object);
		
		/**
		 * Delete object from database
		 * @param ID
		 */
		public void delete(int ID);
		
		/**
		 * Get object from database
		 * @param ID
		 * @return
		 */
		public T get(int ID);
		
		/**
		 * Return list of all objects from database
		 * @return
		 */
		public List<T> getAll();
}
