package edu.wpi.mhtc.service;

import org.springframework.jdbc.core.JdbcTemplate;

public class StatsServiceJDBC implements StatsService {

	private JdbcTemplate template;
	
	public StatsServiceJDBC(JdbcTemplate template) {
		this.template =  template;
		
		
	}
	
}
