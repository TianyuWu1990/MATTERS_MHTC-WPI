package edu.wpi.mhtc.model.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;

public class Admin {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	
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
			String sql = "insert into mhtc_sch.admins(Username, Password, Email, FirstName, LastName) values(?, md5(?), ?, ?, ?) ";
			Connection conn = DBConnector.getInstance().getConn();
			PreparedStatement pstatement = conn.prepareStatement(sql);
			
			pstatement.setString(1, this.username);
			pstatement.setString(2, this.password);
			pstatement.setString(3, this.email);
			pstatement.setString(4, this.firstName);
			pstatement.setString(5, this.lastName);
			return pstatement.execute();
		} else {
			return false;
		}
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
