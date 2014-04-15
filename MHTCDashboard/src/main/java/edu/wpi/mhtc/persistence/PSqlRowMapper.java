package edu.wpi.mhtc.persistence;

import java.sql.SQLException;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface PSqlRowMapper<T> {

	T mapRow(SqlRowSet rs, int rowNum) throws SQLException;
}
