package edu.wpi.mhtc.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import edu.wpi.mhtc.persistence.ProcedureTemplate;

public class PeersServiceJDBC {
	
	private ProcedureTemplate template;
	
	public PeersServiceJDBC(ProcedureTemplate template) {
		this.template =  template;
	}
	
	public List<String> getPeersByInitials() {
		
		// TODO get call name
		SimpleJdbcCall call = template.getCall("");
		
		return null;
	}
	
	public List<String> getPeersByFullName() {
		
		// TODO method stub
		
		return null;
	}
	
}
