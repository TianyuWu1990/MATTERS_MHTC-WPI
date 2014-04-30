package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.wpi.mhtc.dashboard.pipeline.data.State;


public class DBLoader {
	
	/*
	 * key is the state name, value is the state ID
	 */
	public static List<State> getStateMapper() throws Exception{
		List<State> stateList = new LinkedList<State>();
		Connection conn = DBConnector.getInstance().getConn();
		Statement statement = conn.createStatement();
		
		String sql = "select * from mhtc_sch.getStates(FALSE)";
		statement.execute(sql);
		
		ResultSet rs = statement.getResultSet();
        
		/*
		 * index 1 : StateID
		 * index 2 : State initial
		 * index 3 : StateName
		 */
        while (rs.next()) {
        	String stateID = rs.getString(1).toLowerCase();
        	String initial = rs.getString(2).toLowerCase();
        	String stateName = rs.getString(3).toLowerCase();
        	State state = new State(stateID, stateName, initial);
        	stateList.add(state);
        	//System.out.println(state.getFullName() + " " + state.getInitial());
        }
		return stateList;
	}
	
	
	/*
	 * key is the metric name, value is the metric ID
	 */
	public static Map<String, String> getMetricInfo(String catID) throws Exception{
		HashMap<String, String> table = new HashMap<String, String>();
		Connection conn = DBConnector.getInstance().getConn();
		
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
}
