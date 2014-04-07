package edu.wpi.mhtc.persistence;

import java.sql.SQLException;

public interface PSqlRowMapper<T> {

	T mapRow(String rs, int rowNum) throws SQLException;
}
