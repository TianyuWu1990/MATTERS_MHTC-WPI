/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevelopmentConfiguration {

	@Bean 
	public DataSource dataSource() throws SQLException {
		BasicDataSource dataSource = new BasicDataSource();
	
		dataSource.setDriverClassName("org.postgresql.Driver");
		

		dataSource.setUrl("jdbc:postgresql://localhost:5432/mhtc-dev");
		dataSource.setUsername("server");
		dataSource.setPassword("mhtcboxofducks");
	    
/********************************************************/
/** Configure your own local connection ***/
/********************************************************/
		
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/mhtc_local");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("12345");
  		
		return dataSource;
	}
}
