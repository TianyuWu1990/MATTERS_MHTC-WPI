package edu.wpi.mhtc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.model.StatsItem;
import edu.wpi.mhtc.model.StatsMeta;
import edu.wpi.mhtc.persistence.HashMapRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

@Service
public class StatsServiceJDBC implements StatsService {

	private JdbcTemplate template;

	@Autowired
	public StatsServiceJDBC(JdbcTemplate template) {
		this.template = template;

	}

	@Override
	public List<StatsMeta> getAvailibleStatistics() {
		// TODO Auto-generated method stub

		PSqlStringMappedJdbcCall<Map<String, String>> call = 
				new PSqlStringMappedJdbcCall<Map<String, String>>(template)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getcategories");
		
		List<String> columns = new LinkedList<String>(); 
		columns.add("Id");
		columns.add("Name"); 
		columns.add("ParentId");
		columns.add("Visible");
		columns.add("Source");

		call.addDeclaredRowMapper(new HashMapRowMapper(columns));

		List<Object> params = new LinkedList<Object>();

		params.add(false);
		params.add(null);
		
		List<Map<String, String>> results = call.execute(params);
		
		List<String> categoryIds = new LinkedList<String>();
		
		for (Map<String, String> row : results) {
			categoryIds.add(row.get("Id"));
		}
		
		List<StatsMeta> metrics = new LinkedList<StatsMeta>();
		
		for (String categoryId : categoryIds) {
		
			PSqlStringMappedJdbcCall<Map<String, String>> call2 = 
					new PSqlStringMappedJdbcCall<Map<String, String>>(template)
					.withSchemaName("mhtc_sch")
					.withProcedureName("getmetrics");
			
			List<String> columns2 = new LinkedList<String>(); 
			columns2.add("Id");
			columns2.add("Name"); 
			columns2.add("Visible");
			columns2.add("IsCalculated");
	
			call2.addDeclaredRowMapper(new HashMapRowMapper(columns2));
	
			List<Object> params2 = new LinkedList<Object>();
	
			params2.add(categoryId);
			params2.add(false);
			
			List<Map<String, String>> results2 = call2.execute(params2);
			
			for (Map<String, String> row : results2) {
				metrics.add(new StatsMeta(row.get("Id"), row.get("Name")));
			}
		}
		
		return metrics;
	}

	@Override
	public List<StatsItem> getStatistics(String statistic, String state) {
		if (state.equals("all")) {
		
			PSqlStringMappedJdbcCall<Map<String, String>> call = 
					new PSqlStringMappedJdbcCall<Map<String, String>>(template)
					.withSchemaName("mhtc_sch")
					.withProcedureName("getdatabymetric");
			
			List<String> columns = new LinkedList<String>(); 
			columns.add("Stateid");
			columns.add("StateName"); 
			columns.add("Abbreviation");
			columns.add("Year");
			columns.add("Value");
	
			call.addDeclaredRowMapper(new HashMapRowMapper(columns));
	
			List<Object> params = new LinkedList<Object>();
	
			params.add(statistic);
			
			List<Map<String, String>> results = call.execute(params);
			
			List<StatsItem> values = new LinkedList<StatsItem>();
			StatsMeta meta = findMeta(statistic);
			
			for (Map<String, String> row : results) {
				values.add(new StatsItem(meta, row.get("StateName"),Integer.parseInt(row.get("Year")), Double.parseDouble(row.get("Value"))));
			}
			
			return values;
		} else {
			PSqlStringMappedJdbcCall<Map<String, String>> call = 
					new PSqlStringMappedJdbcCall<Map<String, String>>(template)
					.withSchemaName("mhtc_sch")
					.withProcedureName("getdatabystate");
			
			List<String> columns = new LinkedList<String>(); 
			columns.add("MetricId");
			columns.add("MetricName"); 
			columns.add("Year");
			columns.add("Value");
	
			call.addDeclaredRowMapper(new HashMapRowMapper(columns));
	
			List<Object> params = new LinkedList<Object>();
	
			params.add(state);
			
			List<Map<String, String>> results = call.execute(params);
			
			List<StatsItem> values = new LinkedList<StatsItem>();
			StatsMeta meta = findMeta(statistic);
			
			for (Map<String, String> row : results) {
				if (row.get("MetricId").equals(statistic))
					values.add(new StatsItem(meta, state,Integer.parseInt(row.get("Year")), Double.parseDouble(row.get("Value"))));
			}
			
			return values;
		}
	}
	
	private StatsMeta findMeta(String stat) {
		List<StatsMeta> allMeta = getAvailibleStatistics();
		
		for (StatsMeta meta : allMeta) {
			if (meta.getName().equals(stat))
				return meta;
		}
		
		return null;
		
	}

	@Override
	public List<StatsItem> getStatistics(String state) {
		// TODO Auto-generated method stub
		return null;
	}

}
