package edu.wpi.mhtc.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class PeersServiceJDBC {
	
	private JdbcTemplate template;
	
	public PeersServiceJDBC(JdbcTemplate template) {
		this.template =  template;
	}
	
	public List<String> getPeersByInitials() {
		
		return null;
	}
	
	public List<String> getPeersByFullName() {
		
		return null;
	}
	
}
