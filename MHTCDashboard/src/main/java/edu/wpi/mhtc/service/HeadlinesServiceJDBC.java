package edu.wpi.mhtc.service;

import org.springframework.jdbc.core.JdbcTemplate;

public class HeadlinesServiceJDBC {

private JdbcTemplate template;
	
	public HeadlinesServiceJDBC(JdbcTemplate template) {
		this.template =  template;
		
		
	}
}
