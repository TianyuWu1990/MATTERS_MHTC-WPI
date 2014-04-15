package edu.wpi.mhtc.service;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBState;
import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

@Service
public class PeersServiceJDBC implements PeersService {

	private JdbcTemplate template;

	@Autowired
	public PeersServiceJDBC(JdbcTemplate template) {
		this.template = template;
	}

	public List<State> getPeers() {

		PSqlStringMappedJdbcCall<State> call = new PSqlStringMappedJdbcCall<State>(
				template).withSchemaName("mhtc_sch").withProcedureName(
				"getstates");

		call.addDeclaredRowMapper(new PSqlRowMapper<State>() {

			@Override
			public State mapRow(SqlRowSet rs, int rowNum) throws SQLException {
				return new State(rs.getString("Name"), rs
						.getString("Abbreviation"));
			}

		});

		call.addDeclaredParameter(new SqlParameter("showonlypeerstates",
				Types.BOOLEAN));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("showonlypeerstates", true);

		return call.execute(params);

	}

	public List<DBState> getPeersFull() {

		PSqlStringMappedJdbcCall<DBState> call = new PSqlStringMappedJdbcCall<DBState>(
				template).withSchemaName("mhtc_sch").withProcedureName(
				"getstates");

		call.addDeclaredRowMapper(new PSqlRowMapper<DBState>() {

			@Override
			public DBState mapRow(SqlRowSet rs, int rowNum) throws SQLException {
				return new DBState(rs.getInt("Id"), rs.getString("Name"), rs
						.getString("Abbreviation"), rs
						.getBoolean("IsPeerState"));
			}

		});

		call.addDeclaredParameter(new SqlParameter("showonlypeerstates",
				Types.BOOLEAN));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("showonlypeerstates", false);

		return call.execute(params);

	}

}
