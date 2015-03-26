/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */

package edu.wpi.mhtc.util.pipeline.cleaner;

/**
 * Cleaner to validate year values.
 *
 */
public class YearCleaner implements ICleaner {

//	TODO: One file we found had 20121 20131 for years - need to remove extraneous digits
	
	
	@Override
	public String clean(String val) {
		double year = Double.parseDouble(val);
		return clean(year);
	}
	
	public String clean(double val) {
		if(1900 <= val && val <= 2100) {
			return String.valueOf((int) val);
		}
		return null;
		
		
		
	}

}
