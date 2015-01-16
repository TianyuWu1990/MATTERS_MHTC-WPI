/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.db;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;

public class TestDBConnection {

	@Test
	public void testDBConnection() throws SQLException {
		Connection conn = DBConnector.getInstance().getConn();
		assertNotNull(conn);
		//conn.close();
	}
}
