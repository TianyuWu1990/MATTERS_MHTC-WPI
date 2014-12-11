package edu.wpi.mhtc.dashboard.pipeline.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.data.State;
import edu.wpi.mhtc.dashboard.pipeline.scheduler.Schedule;


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
        	int stateID = rs.getInt(1);
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
		
		if (!rs.next()) {
			// Oops! What went wrong?
			
			String categoryName = DBLoader.getCategoryNameFromID(catID);
			CategoryException c = new CategoryException("No metrics found for category \"" + categoryName + "\" with ID: " + catID);
			c.setSolution("Please confirm that this category has at least one metric associated with it");
			
			throw c;
		}
		else {
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
	 * Returns the category ID given a category name
	 * @param categoryName
	 * @return categoryID associated with this name in database
	 * @throws SQLException
	 * @throws CategoryException if no category with categoryName is found
	 */
	public static int getCategoryId(String categoryName) throws SQLException, CategoryException {
		Connection conn = DBConnector.getInstance().getConn();
		String sql = "select * from mhtc_sch.getcategorybyname(?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setString(1, categoryName); 
		ResultSet rs = pstatement.executeQuery();
		
		if (!rs.next()) {
			CategoryException c = new CategoryException("No ID found in database for category \"" + categoryName + "\"");
			c.setSolution("Please confirm you have spelled the category correctly");
			
			throw c;
		}
		else {
			return Integer.parseInt(rs.getString("Id"));
		}
		
	}
	
	/**
	 * Returns the category name given a category ID
	 * @param id
	 * @return category name associated with ID`
	 * @throws SQLException
	 * @throws CategoryException if no category with id is found
	 */
	public static String getCategoryNameFromID(int id) throws SQLException, CategoryException {
		Connection conn = DBConnector.getInstance().getConn();
		// TODO should be using one of the stored procedures, but below one is broken
		// String sql = "SELECT * FROM mhtc_sch.getcategorybyid(?)";
		String sql = "SELECT * FROM mhtc_sch.categories WHERE \"Id\" = "+id;
		PreparedStatement pstatement = conn.prepareStatement(sql);
		ResultSet rs = pstatement.executeQuery();
				
		if (!rs.next()) {
			// This should almost never get thrown, since we are making sure they select
			// the correct category by using the dropdown on the upload tool
			throw new CategoryException("No category found in the database for category ID: "+id);
		}
		else {
			return rs.getString("Name");
		}
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
		
		while (rs.next()) {
			String categoryID = rs.getString("Id").toLowerCase();
			String categoryName = rs.getString("Name");
			table.put(categoryName, categoryID);
		}
		
		return table;
	}
	
	/**
	 * Retrieves all categories with parent from database
	 * @param parent parent category
	 * @return
	 * @throws SQLException
	 */
	public static Map<String, String> getSubCategories(String parent) throws SQLException 
	{
		HashMap<String, String> table = new HashMap<String, String>();
		
		Connection conn = DBConnector.getInstance().getConn();
		
		String sql = "SELECT * FROM mhtc_sch.getCategories(FALSE, ?)";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		pstatement.setInt(1, Integer.parseInt(parent));
		ResultSet rs = pstatement.executeQuery();
		
		while (rs.next()) 
		{
			String categoryID = rs.getString("Id").toLowerCase();
			String categoryName = rs.getString("Name");
			table.put(categoryName, categoryID);
		}
		
		return table;
	}

	public static List<Schedule> getSchedules() throws SQLException, ParseException {
		List<Schedule> schedLlist = new ArrayList<Schedule>();

		Connection conn = DBConnector.getInstance().getConn();
		Statement statement = conn.createStatement();
		
		String sql = "select * from mhtc_sch.schedules";
		statement.execute(sql);
		
		ResultSet rs = statement.getResultSet();
        
        while (rs.next()) {
    		String job_name =  rs.getString("job_name");
    		String sched_name = rs.getString("sched_name");
    		String sched_job = rs.getString("sched_job");
    		String sched_description = rs.getString("sched_description");
    		String sched_date = rs.getString("sched_date");
    		boolean sched_cron = rs.getBoolean("sched_cron");
    		schedLlist.add(new Schedule(job_name, sched_name, sched_job, sched_description, sched_date, sched_cron));
        }
        
		return schedLlist;		
	}	 

	public static ArrayList<ArrayList<String>> getMetricData(String category) throws SQLException, CategoryException
	{
		// Get all metrics with associated category, in the form of key:name, value:id
		Map<String, String> metrics = getMetricInfo(category);
						
		Connection conn = DBConnector.getInstance().getConn();
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		for (Entry<String, String> e : metrics.entrySet())
		{
			String sql = "SELECT * FROM mhtc_sch.getDataByMetric(?)";
			PreparedStatement pstatement = conn.prepareStatement(sql);
			pstatement.setInt(1, Integer.parseInt(e.getValue()));
			ResultSet rs = pstatement.executeQuery();
			
			/* Long: Convert it into JSON format for DataTable processing */
			while (rs.next()) 
			{
				ArrayList<String> dataRows = new ArrayList<String>();
				dataRows.add(rs.getString("StateName"));
				dataRows.add(e.getKey());
				dataRows.add(rs.getString("Value"));
				dataRows.add(rs.getString("Year"));
				data.add(dataRows);
			}
		}
		
		return data;
	}
	
	/**
	 * Gets the list of DataTypes for metrics (numeric, rank, etc.)
	 * @return list of data types
	 * @throws SQLException
	 */
	public static Set<String> getMetricDataTypes() throws SQLException 
	{
		Set<String> dataTypes = new HashSet<String>();
		
		Connection conn = DBConnector.getInstance().getConn();
		
		String sql = "SELECT \"DataType\" FROM mhtc_sch.metrics";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		ResultSet rs = pstatement.executeQuery();

		while (rs.next()) {
			dataTypes.add(rs.getString("DataType"));
		}
		
		return dataTypes;
	}
	
}
