package edu.wpi.mhtc.model.Data;

import java.sql.SQLException;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.wpi.mhtc.persistence.PSqlRowMapper;

public class MetricRowMapper implements PSqlRowMapper<Metric>
{

	@Override
	public Metric mapRow(SqlRowSet rs, int rowNum) throws SQLException
	{
		return new Metric(rs.getInt("MetricId"), rs.getString("MetricName"), rs.getString("MetricDisplayName"),
				Metric.Bin.fromId(rs.getInt("MetricBinId")), rs.getString("MetricUrl"), rs.getString("MetricSource"),
				rs.getString("MetricType"), rs.getString("MetricTrendType"), rs.getBoolean("MetricVisible"),
				rs.getInt("MetricParentId"));
	}

}
