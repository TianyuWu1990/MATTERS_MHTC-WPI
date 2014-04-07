package edu.wpi.mhtc.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

@Component
public class ProcedureTemplate {

	private JdbcTemplate template;
	
	@Autowired
	public ProcedureTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public SimpleJdbcCall getCall(String name) {
		// TODO fill out with proper name
		SimpleJdbcCall call = new SimpleJdbcCall(template).withCatalogName("mhtc")
				.withSchemaName("mhtc_sch").withProcedureName(name);
		
		return call;
	}

}
