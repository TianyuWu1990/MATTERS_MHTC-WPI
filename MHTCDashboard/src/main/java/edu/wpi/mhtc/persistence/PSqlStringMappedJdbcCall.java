package edu.wpi.mhtc.persistence;

import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * There is a fun little bug with Jdbc that makes it not like to play nicely
 * with postgresql stored procedures so this class allows us to fall back on
 * simply passing a call string to the server. It's not an ideal solution and
 * the class needs to be cleaned significantly but it will work. Needs a
 * PSqlRowMapper to parse the results.
 * 
 * If the bug is fixed or someone determines a work around that still uses the
 * stored procedure through jdbc properly, this class should be scrapped.
 * 
 * Using this class is similar to a SimpleJdbcProcedure, however the interface
 * differs where it is necessary.
 * 
 * The type generic type is the type of object that each row in the results set
 * gets mapped to, this will depend on the PSqlRowMapper used.
 * 
 * @author Stokes
 * 
 */
public class PSqlStringMappedJdbcCall<T> {

	private JdbcTemplate template;
	private List<SqlParameter> declaredParams;
	private PSqlRowMapper<T> mapper;
	private String schemaName;
	private String procedureName;

	/**
	 * Creates a new PSqlStringMappedJdbcCall using the given template for
	 * queries.
	 */
	public PSqlStringMappedJdbcCall(JdbcTemplate template) {

		this.template = template;

		declaredParams = new LinkedList<SqlParameter>();
	}

	/**
	 * Sets name of the schema that contains the procedure to execute.
	 */
	public PSqlStringMappedJdbcCall<T> withSchemaName(String schemaname) {
		this.schemaName = schemaname;
		return this;
	}

	/**
	 * Sets the name of the procedure to execute.
	 */
	public PSqlStringMappedJdbcCall<T> withProcedureName(String procedurename) {
		this.procedureName = procedurename;
		return this;
	}

	/**
	 * Adds an input parameter to the procedure.
	 * 
	 */
	public PSqlStringMappedJdbcCall<T> addDeclaredParameter(SqlParameter param) {

		declaredParams.add(param);

		return this;
	}

	/**
	 * Adds a row mapper that is used to parse the results of the query.
	 */
	public PSqlStringMappedJdbcCall<T> addDeclaredRowMapper(PSqlRowMapper<T> mapper) {

		this.mapper = mapper;

		return this;
	}

	/**
	 * Executes the query, you must have given a procedure name, schema name and
	 * a rowmapper for this to work.
	 * 
	 * @param params
	 *            The values of the parameters
	 * @return A list of T as mapped by the row mapper
	 */
	public List<T> execute(Map<String, Object> params) {

		List<T> returnValues = new LinkedList<T>();
        
        try {

			String statement = buildQuery(params);

			SqlRowSet result = template.queryForRowSet(statement);

			// Get the column names out of the first row
			int i = 0;
			while (mapper != null && result.next()) {
				returnValues.add(mapper.mapRow(result, i));
				i++;
			}
		}
        catch (InvalidResultSetAccessException e)
        {
            throw new RuntimeException("mapping SQL result set to object -- INVALID SET ACCESS", e);
        }
        catch (SQLException e)
        {
            throw new RuntimeException("mapping SQL result set to object -- GENERAL SQL EXCEPTION", e);  
        }
        return returnValues;
	}

	private String buildQuery(Map<String, Object> params) {
		String statement = "SELECT * FROM ";
		statement += schemaName + ".";
		statement += procedureName + "(";

		for (int i = 0; i < declaredParams.size(); i++) {
			statement += declaredParams.get(i).getName();
			statement += " := ";

			if (declaredParams.get(i).getSqlType() == Types.VARCHAR) {
				statement += "'";
				statement += params.get(declaredParams.get(i).getName());
				statement += "'";

			} else if (declaredParams.get(i).getSqlType() == Types.INTEGER
					|| declaredParams.get(i).getSqlType() == Types.BOOLEAN) {
				statement += params.get(declaredParams.get(i).getName());
			}

			if (i < declaredParams.size()-1)
				statement += ",";
		}

		statement += ");";

		return statement;
	}
}
