package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.data.Line;

/**
 * Handles the inserting of all data tuples from a source
 * @author Alex Fortier
 *
 */
public class TransactionManager {

	/**
	 * Uploads Line data to database
	 * @param parser
	 * @throws SQLException 
	 */
	public static void insertData(List<Line> data) throws SQLException {
		// Get the connection singleton
		Connection conn = DBConnector.getInstance().getConn();
		conn.setAutoCommit(false);

		Iterator<Line> iter = data.iterator();

		while (iter.hasNext()) {
			Line line = iter.next();
			DBSaver.saveLine(conn, line);
		}
		
		// If exception isn't thrown, data is good to go
		conn.commit();
		
	}
}
