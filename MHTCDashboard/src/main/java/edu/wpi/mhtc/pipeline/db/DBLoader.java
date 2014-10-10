package edu.wpi.mhtc.pipeline.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.mhtc.pipeline.data.CategoryException;
import edu.wpi.mhtc.pipeline.data.State;


public class DBLoader {
	
	/*
	 * key is the state name, value is the state ID
	 */
	public static List<State> getStateMapper() throws SQLException{
		List<State> stateList = new ArrayList<State>(52);
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
	/**
	 * Will change Category ID string to int
	 * @param catID
	 * @returnA Map of metrics associated with this category in the database.
	 * The key is the metric name, value is the metric ID
	 * @throws SQLException
	 * @throws CategoryException 
	 */
	public static Map<String, String> getMetricInfo(String catID) throws SQLException, CategoryException{
		int cid = Integer.parseInt(catID); 
		return getMetricInfo(cid);
	}
	
	/**
	 * 
	 * @param catID
	 * @return A Map of metrics associated with this category in the database.
	 * The key is the metric name, value is the metric ID
	 * @throws SQLException
	 * @throws CategoryException if no metrics are found for this category
	 */
	public static Map<String, String> getMetricInfo(int catID) throws SQLException, CategoryException {
		
		HashMap<String, String> table = new HashMap<String, String>();
		Connection conn = DBConnector.getInstance().getConn();
		
		//String sql = "select * from mhtc_sch.getMetrics(5,FALSE)";
		String sql = "select * from mhtc_sch.getMetrics(?, TRUE)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setInt(1, catID); // set parameter 1 catID
		ResultSet rs = pstatement.executeQuery();
		
		if(!rs.next()){
			throw new CategoryException("No metrics in DB for category "+catID);
		}
		else{
			do {
				String metricID = rs.getString("Id").toLowerCase();
				String metricName = rs.getString("Name").toLowerCase();
				table.put(metricName, metricID);
			}
			while (rs.next());  
		}
		return table;
	}
	
	/**
	 * 
	 * @param categoryName
	 * @return categoryID associated with this name in database
	 * @throws SQLException
	 * @throws CategoryException if no categoryID is found
	 */
	public static int getCategoryId(String categoryName) throws SQLException, CategoryException{
		
		Connection conn = DBConnector.getInstance().getConn();
		String sql = "select * from mhtc_sch.getcategorybyname(?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, categoryName); 
		ResultSet rs = pstatement.executeQuery();
		
		if(!rs.next()){
			throw new CategoryException("No ID found in DB for category "+categoryName);
		}
		
		else
			return Integer.parseInt(rs.getString("Id"));
		
	}
	
	/**
	 * Retrieves all categories from the database
	 * @return A map containing the category names as the key, and IDs as the value
	 * @throws Exception
	 */
	public static Map<String, String> getCategoryInfo() throws SQLException {
		HashMap<String, String> table = new HashMap<String, String>();
		Connection conn = DBConnector.getInstance().getConn();
		
		String sql = "SELECT * FROM mhtc_sch.getCategories(FALSE, NULL)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		ResultSet rs = pstatement.executeQuery();
		
		try {
			while (rs.next()) {
				String categoryID = rs.getString("Id").toLowerCase();
				String categoryName = rs.getString("Name");
				table.put(categoryName, categoryID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return table;
	}
	
	 
}
