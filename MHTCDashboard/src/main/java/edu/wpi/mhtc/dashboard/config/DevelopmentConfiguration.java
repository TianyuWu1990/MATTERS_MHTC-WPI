package edu.wpi.mhtc.dashboard.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DevelopmentConfiguration {

	@Bean 
	public DataSource dataSource() throws SQLException {
		BasicDataSource dataSource = new BasicDataSource();
	
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		dataSource.setUrl("jdbc:postgresql://mhtc.cs.wpi.edu:5432/mhtc");
		dataSource.setUsername("dev");
		dataSource.setPassword("password");
	
		return dataSource;
	}
}
