package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
	
//	added by ck, called by category ID
	/*
	 * key is the metric name, value is the metric ID
	 */
	public static Map<String, String> getMetricInfo(int catID) throws Exception{
		
		HashMap<String, String> table = new HashMap<String, String>();
		Connection conn = DBConnector.getInstance().getConn();
		
		//String sql = "select * from mhtc_sch.getMetrics(5,FALSE)";
		String sql = "select * from mhtc_sch.getMetrics(?, FALSE)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setInt(1, catID); // set parameter 1 catID
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
	
	
	
	

	public static int getCategoryId(String categoryName) throws Exception{
		
		Connection conn = DBConnector.getInstance().getConn();
		String sql = "select * from mhtc_sch.getcategorybyname(?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, categoryName); 
		ResultSet rs = pstatement.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No ID found in DB for category "+categoryName);
		}
		
		else
			return Integer.parseInt(rs.getString("Id"));
		
	}
	
//	check for category before calling!!	
//	If this method passed a category name that is already present in the db
//	it will insert a duplicate with a different id

	public static boolean insertNewCategory(String name, String parentID, String source) throws Exception{
		
		Connection conn = DBConnector.getInstance().getConn();
		
		String sql = "select * from mhtc_sch.insertcategory(?,?,?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, name); 
		
		if(parentID == null){
			pstatement.setNull(2, Types.INTEGER);
		}
		else
			pstatement.setInt(2, Integer.parseInt(parentID));
		pstatement.setString(3, source); 
		ResultSet rs = pstatement.executeQuery();
		
		rs.next();
		
		if (Integer.parseInt(rs.getString("insertcategory")) == 1)
				return true;
		else 
			return false;
	}
	
//	Category must exist in db already !!!
	public static boolean insertNewMetric(String metricName, boolean b, int categoryID, String dataType) throws Exception{
		
		Connection conn = DBConnector.getInstance().getConn();
		
		String sql = "select * from mhtc_sch.insertmetric(?,?,?,?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, metricName); 
		pstatement.setBoolean(2, b);
		pstatement.setInt(3,categoryID);
		pstatement.setString(4, dataType);
		ResultSet rs = pstatement.executeQuery();
		rs.next();
		String tableHeader = rs.getString(1);
		
		if (Integer.parseInt(tableHeader) == 1)
				return true;
		else 
			return false;
	}
	
	 
}





















