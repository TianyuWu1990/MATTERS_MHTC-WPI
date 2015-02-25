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
	 * Sets a token for a user to reset their password
	 * @param token
	 */
	public void updateToken(String email, String token);
	
	/**
	 * Confirm token exists in database for user to reset password
	 * @param token
	 * @return
	 */
	public User confirmToken(String token);
	
	/**
	 * Update password when non-admin user resets their password on their own
	 * @param token
	 */
	public boolean updateUserPassword(String password, String token);
	
	/**
	 * Get a User from the database based on username
	 * @param username
	 * @return
	 */
	public User get(String username);
	
}
