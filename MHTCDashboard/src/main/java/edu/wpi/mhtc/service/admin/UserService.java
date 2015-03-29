/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.service.admin;

import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.dao.admin.UserDAO;
import edu.wpi.mhtc.model.admin.User;
import edu.wpi.mhtc.util.helpers.MD5;
import edu.wpi.mhtc.util.helpers.Mailer;

/**
 * Implementation of Service layer for User DAO
 * @author Alex Fortier
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDAO dao;
	
	static final int SUCCESS = 0;
	static final int ERROR = -3;
	static final int OLD_PASSWORD_NOT_MATCHED = -1;
	static final int NEW_PASSWORD_NOT_MATCHED = -2;
	
	public User get(String username) {
		return dao.get(username).get(0);
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
	
	public boolean setToken(String email) throws MessagingException {
    	// First, randomize the token
    	Random rand = new Random();
    	int randomNum = rand.nextInt(100) + 1;
    	String unencryptedTokenString = email + randomNum;
    	
    	dao.updateToken(email, unencryptedTokenString);
		
    	// Send reset password email to user
    	try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml")) {
    		Mailer mm = (Mailer) context.getBean("mailMail");
            mm.sendResetPasswordMail(email, unencryptedTokenString);
    	} 			    
    	
    	return true;
	}
	
	public User confirmToken(String token) {
		return dao.confirmToken(token);
	}
	
	public boolean resetUserPassword(String password, String token) {
		return dao.updateUserPassword(password, token);
	}
		
}
