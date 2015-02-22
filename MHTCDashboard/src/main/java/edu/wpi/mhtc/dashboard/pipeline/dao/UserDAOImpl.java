package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.wpi.mhtc.model.admin.User;
import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

public class UserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public UserDAOImpl() {}

	@Override
	public void save(User user) {
		PSqlStringMappedJdbcCall<User> call =
				new PSqlStringMappedJdbcCall<User>(jdbcTemplate)
				.withSchemaName("mhtc_sch");
	
		if (user.isAdmin()) {
			call.withProcedureName("insertadmin");
		} else {
			call.withProcedureName("insertuser");
		}
			
		call.addDeclaredParameter(new SqlParameter("username", Types.VARCHAR));
		call.addDeclaredParameter(new SqlParameter("email", Types.VARCHAR));
		call.addDeclaredParameter(new SqlParameter("firstname", Types.VARCHAR));
		call.addDeclaredParameter(new SqlParameter("lastname", Types.VARCHAR));
		call.addDeclaredParameter(new SqlParameter("password", Types.VARCHAR));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", user.getUsername());
		params.put("email", user.getEmail());
		params.put("firstname", user.getFirstName());
		params.put("lastname", user.getLastName());
		params.put("password", user.getPassword());
		
		call.execute(params);
	}

	@Override
	public void delete(int ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePassword(User user) {
		PSqlStringMappedJdbcCall<User> call =
				new PSqlStringMappedJdbcCall<User>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("updatepassword");
			
		call.addDeclaredParameter(new SqlParameter("username", Types.VARCHAR));
		call.addDeclaredParameter(new SqlParameter("password", Types.VARCHAR));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		
		call.execute(params);
	}

	@Override
	public User get(String username) {
		PSqlStringMappedJdbcCall<User> call =
				new PSqlStringMappedJdbcCall<User>(jdbcTemplate)
				.withSchemaName("mhtc_sch")
				.withProcedureName("getuser");
		
		call.addDeclaredRowMapper(new UserMapper());
			
		call.addDeclaredParameter(new SqlParameter("username", Types.VARCHAR));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		
		return (User) call.execute(params);
	}
	
	/**
	 * Predefined RowMapper for User only
	 * @author Alex Fortier
	 *
	 */
	private static class UserMapper implements PSqlRowMapper<User> {

		@Override
		public User mapRow(SqlRowSet rs, int rowNum) throws SQLException {
			User u = new User();
			
			u.setId(rs.getInt("Id"));
			u.setUsername(rs.getString("UserName"));
			u.setEmail(rs.getString("Email"));
			u.setPasswordHash(rs.getString("PasswordHashed"));
			u.setFirstName(rs.getString("FirstName"));
			u.setLastName(rs.getString("LastName"));
						
			return u;
		}
		
	}

}
