package edu.wpi.mhtc.dashboard.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// TODO add profile 
@Configuration
@Profile("local")
public class LocalConfiguration {

	@Bean 
	public DataSource dataSource() throws SQLException {
		BasicDataSource dataSource = new BasicDataSource();
	
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		dataSource.setUrl("jdbc:postgresql://localhost:5432/mhtc_local");
		dataSource.setUsername("long");
		dataSource.setPassword("123");
	
		return dataSource;
	}
	
}
