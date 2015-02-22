package edu.wpi.mhtc.dashboard.pipeline.dao;

import edu.wpi.mhtc.model.admin.User;

/**
 * Interface for Data Access Object
 * Used for Admins/Users
 * @author Alex Fortier
 *
 */
public interface UserDAO {

	/**
	 * Save a user into the database
	 * @param user
	 */
	public void save(User user);
	
	/**
	 * Delete a user from the database by ID
	 * @param ID
	 */
	public void delete(int ID);
	
	/**
	 * Update a user password in the database
	 * @param user
	 */
	public void updatePassword(User user);

	/**
	 * Get a User from the database based on username
	 * @param username
	 * @return
	 */
	public User get(String username);
	
}
