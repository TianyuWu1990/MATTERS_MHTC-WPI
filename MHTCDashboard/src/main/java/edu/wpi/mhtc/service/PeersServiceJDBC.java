package edu.wpi.mhtc.service;

import org.springframework.jdbc.core.JdbcTemplate;

public class PeersServiceJDBC {
	
	private JdbcTemplate template;
	
	public PeersServiceJDBC(JdbcTemplate template) {
		this.template =  template;
		
		
	}
	
}
