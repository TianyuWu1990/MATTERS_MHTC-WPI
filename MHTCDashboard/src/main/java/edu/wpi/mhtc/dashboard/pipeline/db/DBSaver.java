package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.mhtc.dashboard.pipeline.data.DBData;
import edu.wpi.mhtc.dashboard.pipeline.main.Line;

public class DBSaver {

	/*
	 * save line data into DB
	 */
	
//	TODO
	public static boolean saveLine(Line line) throws SQLException {
		
//		if(!isDBDataValid(dbData)){
//			System.out.println("Data is not valid");
//			return false;
//		}
		
//		
//		int year = Integer.parseInt(dbData.getYear());
//		int stateID = Integer.parseInt(dbData.getState().getStateID());
//		Map<String, String> dbMap = dbData.getMap();
//
//		Connection conn = DBConnector.getInstance().getConn();
//		String sql = "insert into mhtc_sch.statistics values(?, ?, ?, ?) ";
//		PreparedStatement pstatement = conn.prepareStatement(sql);
//		for (Entry<String, String> entry : dbMap.entrySet()) {
//			int metricID = Integer.parseInt(entry.getKey());
//			
//			if(entry.getKey() == null || entry.getValue() == null){
//				
//				System.out.println("there is nothing in this map - dbData.getMap() \n Data never made it here");
//				return false;
////				continue; this is a bad trap
//			}
//			float metricValue = Float.parseFloat(entry.getValue());
//
//			pstatement.setInt(1, stateID); // set parameter 1 (FIRST_NAME)
//			pstatement.setInt(2, metricID); // set parameter 2 (ID)
//			pstatement.setInt(3, year);
//			pstatement.setFloat(4, metricValue);
//			pstatement.execute();
//		}
		return true;
	}
	
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

	public static boolean isDBDataValid(DBData dbData){
		return dbData.getState() != null && !dbData.getYear().isEmpty();
	}
	
}
