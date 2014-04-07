package edu.wpi.mhtc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.model.StateMeta;
import edu.wpi.mhtc.persistence.HashMapRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

@Service
public class PeersServiceJDBC implements PeersService {

	private JdbcTemplate template;

	@Autowired
	public PeersServiceJDBC(JdbcTemplate template) {
		this.template = template;
	}

	public List<String> getPeersByInitials() {

		PSqlStringMappedJdbcCall<Map<String, String>> call = 
				new PSqlStringMappedJdbcCall<Map<String, String>>(template)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getstates");
		
		List<String> columns = new LinkedList<String>(); 
		columns.add("Id");
		columns.add("Abbreviation"); 
		columns.add("Name");
		columns.add("IsPeerState");

		call.addDeclaredRowMapper(new HashMapRowMapper(columns));

		List<Object> params = new LinkedList<Object>();

		params.add(true);
		
		List<Map<String, String>> results = call.execute(params);
		
		List<String> initials = new LinkedList<String>();
		
		for (Map<String, String> row : results) {
			initials.add(row.get("Abbreviation"));
		}
		
		return initials;

	}

	public List<StateMeta> getPeersByFullName() {

		PSqlStringMappedJdbcCall<Map<String, String>> call = 
				new PSqlStringMappedJdbcCall<Map<String, String>>(template)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getstates");
		
		List<String> columns = new LinkedList<String>(); 
		columns.add("Id");
		columns.add("Abbreviation"); 
		columns.add("Name");
		columns.add("IsPeerState");

		call.addDeclaredRowMapper(new HashMapRowMapper(columns));

		List<Object> params = new LinkedList<Object>();

		params.add(true);
		
		List<Map<String, String>> results = call.execute(params);
		
		List<StateMeta> names = new LinkedList<StateMeta>();
		
		for (Map<String, String> row : results) {
			names.add(new StateMeta(Integer.parseInt(row.get("Id")), row.get("Abbreviation"), row.get("Name")));
		}
		
		return names;
	}

}
