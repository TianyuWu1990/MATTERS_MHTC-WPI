package edu.wpi.mhtc.dashboard.pipeline.cleaner;

public class YearCleaner implements ICleaner {

//	TODO: One file we found had 20121 20131 for years - need to remove extraneous digits
	
	
	@Override
	public String clean(String val) throws Exception {
		
		int year = Integer.parseInt(val);
		if(1900 <= year && year <= 2015) return String.valueOf((int) year);
		else throw new Exception("Not a valid year");
	}
	
	public String clean(double val) throws Exception{
		if(1900 <= val && val <= 2015) return String.valueOf((int) val);
		
		else throw new Exception("Not a valid year");
	}

}
