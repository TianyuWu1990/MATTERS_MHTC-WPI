/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;

public class Admin {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	
	public Admin(String username) {
		this.username = username;
	}
	
	public Admin(String username,String password, String email,String firstName,String lastName) {
		this.id = 0;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Admin(int id, String username,String password,String firstName,String lastName,String email) {
		this.id = 0;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	// Push into database
	public boolean insertToDB() throws SQLException {
		if (this.id == 0) { // Just to make sure this Admin class is not DB-retrieved.
			String sql = "INSERT INTO mhtc_sch.users(\"UserName\", \"PasswordHash\", \"Email\", \"FirstName\", \"LastName\", \"IsApproved\", \"GroupId\", \"Token\") VALUES (?, md5(?), ?, ?, ?, true, 1, '');";
			sql += " INSERT INTO mhtc_sch.user_roles VALUES(?, 'USER'), (?, 'ADMIN');";
			Connection conn = DBConnector.getInstance().getConn();
			PreparedStatement pstatement = conn.prepareStatement(sql);
			
			pstatement.setString(1, this.username);
			pstatement.setString(2, this.password);
			pstatement.setString(3, this.email);
			pstatement.setString(4, this.firstName);
			pstatement.setString(5, this.lastName);
			pstatement.setString(6, this.username);
			pstatement.setString(7, this.username);
			
			System.out.println(pstatement.toString());
			boolean insertStatus = pstatement.execute();
			
			String roles_sql = "INSERT INTO mhtc_sch.user_roles VALUES (?, 'ADMIN'), (?, 'USER')";
			PreparedStatement prolestatement = conn.prepareStatement(roles_sql);
			prolestatement.setString(1, this.username);
			prolestatement.setString(2, this.username);
			System.out.println(prolestatement.toString());
			boolean roleStatus = prolestatement.execute();
			
			return insertStatus && roleStatus;
		} else {
			return false;
		}
	}
	
	// Change password
	static final int SUCCESS = 0;
	static final int ERROR = -3;
	static final int OLD_PASSWORD_NOT_MATCHED = -1;
	static final int NEW_PASSWORD_NOT_MATCHED = -2;
	public int changePassword(String oldPassword, String newPassword, String confirmPassword) throws SQLException {
		// Security validation
		String sql = "SELECT \"UserName\", \"PasswordHash\" FROM mhtc_sch.users WHERE \"UserName\"=? AND \"PasswordHash\"=md5(?)";
		Connection conn = DBConnector.getInstance().getConn();
		PreparedStatement pstatement = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		pstatement.setString(1, this.username);
		pstatement.setString(2, oldPassword);
		pstatement.execute();
		if (!pstatement.getResultSet().last()) { // Check if there is any matched row
			return OLD_PASSWORD_NOT_MATCHED;
		}
		
		if (!newPassword.equals(confirmPassword)) {
			return NEW_PASSWORD_NOT_MATCHED;
		}
		
		// Begin changing the password
		return setNewPassword(newPassword);
	}
	
	public String resetPassword() throws SQLException {
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%";
		StringBuffer buffer = new StringBuffer();
		int charactersLength = characters.length();

		for (int i = 0; i < 10; i++) { // Generate a random 10-chars password
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		
		String newPassword = buffer.toString();
		
		if (this.setNewPassword(newPassword) == 0) {
			return newPassword;
		}
		
		return "";
	}
	
	private int setNewPassword(String newPassword) throws SQLException {
		String update_sql = "UPDATE mhtc_sch.users SET \"PasswordHash\"=md5(?) WHERE \"UserName\"=?";
		Connection conn = DBConnector.getInstance().getConn();
		PreparedStatement pstatement = conn.prepareStatement(update_sql);
		pstatement.setString(1, newPassword);
		pstatement.setString(2, this.username);
		pstatement.execute();	
	
		return (pstatement.getUpdateCount() == 1) ? 0 : -3;
	}

	// Getters
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
}
