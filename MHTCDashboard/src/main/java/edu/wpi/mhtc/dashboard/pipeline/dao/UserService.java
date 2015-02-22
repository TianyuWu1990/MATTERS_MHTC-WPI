package edu.wpi.mhtc.dashboard.pipeline.dao;

import org.springframework.beans.factory.annotation.Autowired;

import edu.wpi.mhtc.helpers.MD5;
import edu.wpi.mhtc.model.admin.User;

public class UserService {

	@Autowired
	private UserDAO dao;
	
	static final int SUCCESS = 0;
	static final int ERROR = -3;
	static final int OLD_PASSWORD_NOT_MATCHED = -1;
	static final int NEW_PASSWORD_NOT_MATCHED = -2;
	
	public User get(String username) {
		return dao.get(username);
	}
	
	public void createUser(String username, String password, String firstName, String lastName, String email, boolean isAdmin) {
		User u = new User(username, password, email, firstName, lastName, isAdmin);
		dao.save(u);
	}
	
	public int changePassword(String oldPassword, String newPassword, String confirmPassword, String username) {
		User u = get(username);
		
		if (u.getPasswordHash().equals(MD5.getMD5(oldPassword))) {
			if (newPassword.equals(confirmPassword)) {
				return setNewPassword(u, newPassword);
			} else {
				return NEW_PASSWORD_NOT_MATCHED;
			}
		} else {
			return OLD_PASSWORD_NOT_MATCHED;
		}
	}

	private int setNewPassword(User user, String newPassword) {
		user.setPassword(newPassword);
		dao.updatePassword(user);
		return SUCCESS;
	}
	
	public String resetPassword(String username) {
		User u = get(username);
		
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%";
		StringBuffer buffer = new StringBuffer();
		int charactersLength = characters.length();

		for (int i = 0; i < 10; i++) { // Generate a random 10-chars password
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		
		String newPassword = buffer.toString();
		
		if (setNewPassword(u, newPassword) == SUCCESS) {
			return newPassword;
		}
		
		return "";
	}
	
}
