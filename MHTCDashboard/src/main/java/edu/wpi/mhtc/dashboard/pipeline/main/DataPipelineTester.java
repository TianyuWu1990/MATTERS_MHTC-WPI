package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;

public class DataPipelineTester {

	static File inputFile = new File("TestUnifiedFile.xlsx"); //local test excel file
	
	static String categoryName = "Testing Category Name"; //this is supplied by the user through the admin panel
	
	
	public static void main(String[] args){
		
		
		
//		to test db method get category id by name
//		try{
//			int i = DBLoader.getCategoryId("NoWayThisIsInDB");
//			System.out.println("Id for category is "+i);
//			System.out.println("Id for category should not be found");
//		}
//		catch(Exception e){
//			System.out.println("No Category Id found");
//		}
//		try{
//			int i = DBLoader.getCategoryId("Population");
//			System.out.println("Id for category is "+i);
//			System.out.println("Id for category should be 8");
//		}
//		catch(Exception e){
//			System.out.println("No Category Id found");
//		}
		
		
		try{
			boolean b = DBLoader.insertNewCategory("TestCategoryFromPipeline", null, "testing.com");
			System.out.println("Insert returned "+b);
			
			int i = DBLoader.getCategoryId("TestCategoryFromPipeline");
			System.out.println("Get Id returned "+i);
			
			
		}
		
		
		catch(Exception e){
			System.out.println(e.getMessage());
		}
//		
//		
//		
//		
//		try {
//			
//			DataPipeline.run(inputFile, "" + categoryName);
//		} 
//		
//		catch (SQLException e) {
//			
//			System.out.println("DBSaver.saveLineData threw sql exception" + e.getMessage());
//			
//			e.printStackTrace();
//		}
//		catch (Exception e) {
//			
//			System.out.println("Something weird happened" + e.getMessage());
//			e.printStackTrace();
//		}
//		
		
	}
	
	public static void run(){
		
	}
	

}
