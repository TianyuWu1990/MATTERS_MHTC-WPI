package edu.wpi.mhtc.service;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.persistence.DBMetric;
import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

@Service
public class MetricsServiceJdbc implements MetricsService {

	private JdbcTemplate template;

	@Autowired
	public MetricsServiceJdbc(JdbcTemplate template) {
		this.template = template;

	}

	@Override
	public List<DBMetric> getAvailibleStatistics() {

		PSqlStringMappedJdbcCall<Integer> call = new PSqlStringMappedJdbcCall<Integer>(
				template).withSchemaName("mhtc_sch").withProcedureName(
				"getcategories");

		call.addDeclaredRowMapper(new PSqlRowMapper<Integer>() {

			@Override
			public Integer mapRow(SqlRowSet rs, int rowNum) throws SQLException {
				return rs.getInt("Id");
			}

		});

		call.addDeclaredParameter(new SqlParameter("showall", Types.BOOLEAN));
		call.addDeclaredParameter(new SqlParameter("parentid", Types.INTEGER));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("showall", false);
		params.put("parentid", null);

		List<Integer> categories = call.execute(params);
		List<DBMetric> metrics = new LinkedList<DBMetric>();

		for (int i : categories) {
			PSqlStringMappedJdbcCall<DBMetric> metricCall = new PSqlStringMappedJdbcCall<DBMetric>(
					template).withSchemaName("mhtc_sch").withProcedureName(
					"getmetrics");

			metricCall.addDeclaredRowMapper(new PSqlRowMapper<DBMetric>() {

				@Override
				public DBMetric mapRow(SqlRowSet rs, int rowNum)
						throws SQLException {
					return new DBMetric(rs.getInt("Id"), rs.getString("Name"), rs.getString("DataType"));
				}

			});

			metricCall.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
			metricCall.addDeclaredParameter(new SqlParameter("showall", Types.BOOLEAN));

			Map<String, Object> metricParams = new HashMap<String, Object>();
			metricParams.put("categoryid", i);
			metricParams.put("showall", false);

			List<DBMetric> categorymetrics = metricCall.execute(metricParams);
			
			metrics.addAll(categorymetrics);
		}
		
		return metrics;

	}
}
