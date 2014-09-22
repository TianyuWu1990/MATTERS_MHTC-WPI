package edu.wpi.mhtc.model.state;

import java.sql.SQLException;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.wpi.mhtc.persistence.PSqlRowMapper;

public class StateRowMapper implements PSqlRowMapper<State>
{

	@Override
	public State mapRow(SqlRowSet rs, int rowNum) throws SQLException
	{
		return new State(rs.getInt("StateId"), rs.getString("StateName"), rs.getString("StateAbbreviation"),
				rs.getBoolean("StateIsPeer"));
	}

}
