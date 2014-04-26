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

import edu.wpi.mhtc.model.Data.Metrics;
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
	public List<DBMetric> getMetricsInCategory(final int categoryId) {
		PSqlStringMappedJdbcCall<DBMetric> metricCall = new PSqlStringMappedJdbcCall<DBMetric>(template)
                .withSchemaName("mhtc_sch").withProcedureName("getmetrics");

		String name = "";
		// TODO get rid of these terrible hard coded ids
		if (categoryId == 21) {
			name = "National";
		} else if (categoryId == 20) {
			name = "Talent";
		} else if (categoryId == 37) {
			name = "Cost";
		} else if (categoryId == 29) {
			name = "Economy";
		}
		
		final String finalName = name;
		
        metricCall.addDeclaredRowMapper(new PSqlRowMapper<DBMetric>() {

            @Override
            public DBMetric mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                return new DBMetric(rs.getInt("Id"), rs.getString("Name"), rs.getString("DataType"), categoryId, finalName);
            }

        });

        metricCall.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
        metricCall.addDeclaredParameter(new SqlParameter("showall", Types.BOOLEAN));

        Map<String, Object> metricParams = new HashMap<String, Object>();
        metricParams.put("categoryid", categoryId);
        metricParams.put("showall", false);

        return metricCall.execute(metricParams);
		
	}
	
    @Override
    public Metrics getAvailible(Object... params) {

    	// TODO pull bin ids into a property or something
    	List<DBMetric> metrics = new LinkedList<DBMetric>();
    	metrics.addAll(getMetricsInCategory(20));
    	metrics.addAll(getMetricsInCategory(21));
    	metrics.addAll(getMetricsInCategory(29));
    	metrics.addAll(getMetricsInCategory(37)); 

    	return new Metrics(metrics);
    }
}
