package edu.wpi.mhtc.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class ProcedureTemplate {

	private JdbcTemplate template;
	
	public ProcedureTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public SimpleJdbcCall getCall(String name) {
		// TODO fill out with proper name
		SimpleJdbcCall call = new SimpleJdbcCall(template).withCatalogName("")
				.withSchemaName("").withProcedureName(name);
		
		return call;
	}

}
