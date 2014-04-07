package edu.wpi.mhtc.persistence;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * There is a fun little bug with Jdbc that makes it not like to play nicely
 * with postgresql stored procedures so this class allows us to fall back on
 * simply passing a call string to the server. It's not an ideal solution and
 * the class needs to be cleaned significantly but it will work. Needs a
 * HasMapRowMapper to parse the results.
 * 
 * @author Stokes
 * 
 * @param <T>
 */
public class PSqlStringMappedJdbcCall<T> {

	private JdbcTemplate template;
	private List<SqlParameter> declaredParams;
	private PSqlRowMapper<T> mapper;
	private String schemaname;
	private String procedurename;

	public PSqlStringMappedJdbcCall(JdbcTemplate template) {

		this.template = template;

		declaredParams = new LinkedList<SqlParameter>();
	}

	public PSqlStringMappedJdbcCall<T> withSchemaName(String schemaname) {
		this.schemaname = schemaname;
		return this;
	}

	public PSqlStringMappedJdbcCall<T> withProcedureName(String procedurename) {
		this.procedurename = procedurename;
		return this;
	}

	/*
	 * public void addDeclatedParameter(SqlParameter param) {
	 * 
	 * declaredParams.add(param); }
	 */

	public void addDeclaredRowMapper(PSqlRowMapper<T> mapper) {

		this.mapper = mapper;
	}

	public List<T> execute(List<Object> params) {
		try {

			String statement = "SELECT ";
			statement += schemaname + ".";
			statement += procedurename + "(";

			int i = 0;
			for (Object param : params) {
				if (param == null)
					statement += "null";
				else
					statement += param.toString();

				i++;
				if (i < params.size())
					statement += ",";
			}

			statement += ");";

			SqlRowSet result = template.queryForRowSet(statement);

			List<T> returnValues = new LinkedList<T>();

			i = 0;
			while (result.next()) {
				returnValues.add(mapper.mapRow(result.getString(1), i));

				i++;
			}

			return returnValues;

		} catch (InvalidResultSetAccessException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
