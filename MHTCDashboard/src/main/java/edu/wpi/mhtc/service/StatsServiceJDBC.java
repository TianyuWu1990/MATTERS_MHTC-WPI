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

import edu.wpi.mhtc.model.Data.Data;
import edu.wpi.mhtc.model.Data.DataSource;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBMetric;
import edu.wpi.mhtc.persistence.DBState;
import edu.wpi.mhtc.persistence.MetricMapper;
import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;
import edu.wpi.mhtc.persistence.StateMapper;

@Service
public class StatsServiceJDBC implements StatsService {

	private JdbcTemplate template;
	private StateMapper stateMapper;
	private MetricMapper metricMapper;

	@Autowired
	public StatsServiceJDBC(JdbcTemplate template, StateMapper stateMapper, MetricMapper metricMapper) {
		this.template = template;
		this.stateMapper = stateMapper;
		this.metricMapper = metricMapper;
	}

	@Override
	public State getDataForState(String state, String metrics) {
		
		final DBState dbState = stateMapper.getStateFromString(state);
		List<DBMetric> dbMetrics = getListOfMetricsFromCommaSeparatedString(metrics);
		
		
		PSqlStringMappedJdbcCall<DataPoint> call = new PSqlStringMappedJdbcCall<DataPoint>(
				template).withSchemaName("mhtc_sch").withProcedureName(
				"getdatabystate");

		call.addDeclaredRowMapper(new PSqlRowMapper<DataPoint>() {

			@Override
			public DataPoint mapRow(SqlRowSet rs, int rowNum)
					throws SQLException {
				return new DataPoint(dbState, metricMapper.getMetricByID(rs.getInt("MetricId")), rs
						.getInt("Year"), rs.getDouble("Value"));
			}

		});

		call.addDeclaredParameter(new SqlParameter("stateid", Types.INTEGER));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stateid", dbState.getId());

		List<DataPoint> datapoints = call.execute(params);
		
		
		List<DataSource> datasources = new LinkedList<DataSource>();

		for (DBMetric metric : dbMetrics) {
			int id = metric.getId();
			DataSource source = new DataSource(metric.getName(), "", "NA", "");
			for (DataPoint datapoint : datapoints) {
				try{
					if (datapoint.getMetricid().getId() == id) {
						source.addData(new Data(datapoint.getYear(), (int)datapoint.getValue()));
					}
				}
				catch( Exception e )
				{
					
				}
			}
			datasources.add(source);
		}
		
		
		return new State(dbState.getName(), dbState.getInitial(), datasources.toArray(new DataSource[0]));

	}

	private List<DBMetric> getListOfMetricsFromCommaSeparatedString(String metric) {
		
		if (metric.equals("all"))
			return metricMapper.getAll();
		
		String[] splits = metric.split(",");
		
		List<DBMetric> metrics = new LinkedList<DBMetric>();
		
		for (String split : splits) {
			
			metrics.add(metricMapper.getMetricFromString(split));
		}
		
		return metrics;
	}
	
	

}
