/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.persistence;

import java.sql.SQLException;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.wpi.mhtc.model.dashboard.State;

public class StateRowMapper implements PSqlRowMapper<State>
{

	@Override
	public State mapRow(SqlRowSet rs, int rowNum) throws SQLException
	{
		return new State(rs.getInt("StateId"), rs.getString("StateName"), rs.getString("StateAbbreviation"),
				rs.getBoolean("StateIsPeer"));
	}

}
