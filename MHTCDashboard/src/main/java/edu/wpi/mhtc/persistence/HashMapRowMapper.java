package edu.wpi.mhtc.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class HashMapRowMapper implements PSqlRowMapper<Map<String, String>> {

	private List<String> columns;
	
	public HashMapRowMapper(List<String> columns) {
		this.columns = columns;
		
	}
	
	@Override
	public Map<String, String> mapRow(String result, int rowNum)
			throws SQLException {
		String shortened = result.substring(1, result.length()-1);
		
		String[] splits = shortened.split(",");
		
		Map<String, String> values = new HashMap<String, String>();
		
		int i = 0;
		for (String split : splits) {
			if (split.startsWith("\""))
				split = split.substring(1);
			if (split.endsWith("\""))
				split = split.substring(0, split.length()-1);
			
			
			values.put(columns.get(i), split);
			
			i++;
		}
		
		return values;
	}

}
