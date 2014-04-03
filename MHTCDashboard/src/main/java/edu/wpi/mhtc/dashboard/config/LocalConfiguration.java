package edu.wpi.mhtc.dashboard.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// TODO add profile 
@Configuration
public class LocalConfiguration {
/*
	<bean id="dataSource" destroy-method="close" class="org.apache.commons.dbcp.BasicDataSource">
    <property name="driverClassName" value="${jdbc.driverClassName}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>

*
*
*DriverManagerDataSource dataSource = new DriverManagerDataSource();
dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
dataSource.setUrl("jdbc:hsqldb:hsql://localhost:");
dataSource.setUsername("sa");
dataSource.setPassword("");
*
*
*/

	/**
	 * Local in memory database.
	 */
	@Bean 
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
	
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost:");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
	
		return dataSource;
	}
	
}
