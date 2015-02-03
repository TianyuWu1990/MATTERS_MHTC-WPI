package edu.wpi.mhtc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;

public class UserService {
	public static String getEmailByUser(String username) throws SQLException {
		Connection conn = DBConnector.getInstance().getConn();
		
		String sql = "SELECT \"Email\" FROM mhtc_sch.users WHERE \"UserName\"= ?"; 
		String email = "";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, username);
		ResultSet rs = pstatement.executeQuery();
		
		while (rs.next()) {
			email = rs.getString("Email");
		}
		
		return email;			
	}
}
