/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;

public class DataPipelineTester {

	static File inputFile = new File("testfiles/State Minimum Wage Rates formatted.xlsx"); //local test excel file
//	static String categoryName = "Testing Category Name"; //this is supplied by the user through the admin panel
	static int catID = 54;
	static String metricName = "minimum wage"; //for testing
	
	
	public static void main(String[] args) throws SQLException{
		

		ResultSet rs = DBLoader.getMetricsByParent(21, 20, 29);

		 while (rs.next()) {
	    		System.out.println(rs.getString("Name"));
		 }
		
		int categoryID = 0;
		int metricID = 0;
		
		try{
//			boolean categoryAdded = DBLoader.insertNewCategory(categoryName, null, "testing.com");
//			System.out.println("Insert returned "+categoryAdded);
//			
//			categoryID = DBLoader.getCategoryId(categoryName);
//			System.out.println("Get Id returned "+categoryID);
//			
//			boolean metricAdded = DBLoader.insertNewMetric(metricName, false, categoryID, "numeric" );
//			System.out.println("Insert returned "+metricAdded);
			
			Map<String, String> metric = DBLoader.getMetricInfo(catID);
			metricID = Integer.parseInt(metric.get(metricName));
			System.out.println("Get metricID returned "+metricID);	
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		
		
		try {
			
			DataPipeline.run(inputFile, String.valueOf(catID), false);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		String query = "select * from mhtc_sch.statistics where \"MetricId\"=" + metricID;
		System.out.println("To check if successfully loaded in db, execute this query in from pgadmin: \n\n"+query);

	}

//		
//		
//		
////		to test db method get category id by name
////		try{
////			int i = DBLoader.getCategoryId("NoWayThisIsInDB");
////			System.out.println("Id for category is "+i);
////			System.out.println("Id for category should not be found");
////		}
////		catch(Exception e){
////			System.out.println("No Category Id found");
////		}
////		try{
////			int i = DBLoader.getCategoryId("Population");
////			System.out.println("Id for category is "+i);
////			System.out.println("Id for category should be 8");
////		}
////		catch(Exception e){
////			System.out.println("No Category Id found");
////		}
//		
////		
////		try {
////			
////			DataPipeline.run(inputFile, "" + categoryName);
////		} 
////		
////		catch (SQLException e) {
////			
////			System.out.println("DBSaver.saveLineData threw sql exception" + e.getMessage());
////			
////			e.printStackTrace();
////		}
////		catch (Exception e) {
////			
////			System.out.println("Something weird happened" + e.getMessage());
////			e.printStackTrace();
////		}
////		
	

}
