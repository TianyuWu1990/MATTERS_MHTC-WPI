package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.mhtc.dashboard.pipeline.data.DBData;

public class DBConnector {

	private static DBConnector dbConnector;
	private String url = "jdbc:postgresql://127.0.0.1:5432/mhtc_local";
	private String user = "postgres";
	private String password = "12345";
	private Connection conn = null;

	private DBConnector() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static DBConnector getInstance() {
		if (dbConnector == null) {
			dbConnector = new DBConnector();
		}
		return dbConnector;
	}

	/*
	 * getters and setters
	 */
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	
//	ck - maybe consolidate all db accessor methods in this class ?? copied from other classes below

	
	/*
	 * key is the metric name, value is the metric ID
	 */
//	getMetricInfo copied from DBLoader
	public static Map<String, String> getMetricInfo(String catID) throws Exception{
		
		
		HashMap<String, String> table = new HashMap<String, String>();
		Connection conn = new DBConnector();
		
		//String sql = "select * from mhtc_sch.getMetrics(5,FALSE)";
		String sql = "select * from mhtc_sch.getMetrics(?, FALSE)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		int cid = Integer.parseInt(catID); 
		pstatement.setInt(1, cid); // set parameter 1 catID
		ResultSet rs = pstatement.executeQuery(); 
		
		try {	
			while (rs.next()) {
				String metricID = rs.getString("Id").toLowerCase();
				String metricName = rs.getString("Name").toLowerCase();
				table.put(metricName, metricID);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}
	
	
	
	
//	save LineData copied from DBSaver
	public static boolean saveLineData(DBData dbData) throws SQLException {
		
		if(!isDBDataValid(dbData)){
			System.out.println("Data is not valid");
			return false;
		}
		int year = Integer.parseInt(dbData.getYear());
		int stateID = Integer.parseInt(dbData.getState().getStateID());
		Map<String, String> dbMap = dbData.getMap();

		Connection conn = DBConnector.getInstance().getConn();
		String sql = "insert into mhtc_sch.statistics values(?, ?, ?, ?) ";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		for (Entry<String, String> entry : dbMap.entrySet()) {
			int metricID = Integer.parseInt(entry.getKey());
			
			if(entry.getKey() == null || entry.getValue() == null){
				
				System.out.println("there is nothing in this map - dbData.getMap() \n Data never made it here");
				return false;
//				continue; this is a bad trap
			}
			float metricValue = Float.parseFloat(entry.getValue());

			pstatement.setInt(1, stateID); // set parameter 1 (FIRST_NAME)
			pstatement.setInt(2, metricID); // set parameter 2 (ID)
			pstatement.setInt(3, year);
			pstatement.setFloat(4, metricValue);
			pstatement.execute();
		}
		return true;
	}
	

}
