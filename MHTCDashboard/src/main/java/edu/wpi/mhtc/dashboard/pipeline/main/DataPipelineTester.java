package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.sql.SQLException;

public class DataPipelineTester {

	static File inputFile = new File("TestUnifiedFile.xlsx"); //local test excel file
	
	static String categoryName = "Testing Category Name"; //this is supplied by the user through the admin panel
	
	
	public static void main(String[] args){
		
		
		try {
			
			DataPipeline.run(inputFile, "" + categoryName);
		} 
		
		catch (SQLException e) {
			
			System.out.println("DBSaver.saveLineData threw sql exception" + e.getMessage());
			
			e.printStackTrace();
		}
		catch (Exception e) {
			
			System.out.println("Something weird happened" + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	public static void run(){
		
	}
	

}
