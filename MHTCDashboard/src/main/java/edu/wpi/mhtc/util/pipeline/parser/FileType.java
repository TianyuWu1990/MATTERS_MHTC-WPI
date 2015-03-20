/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.parser;

/**
 *File formats of parsable input files. 
 *
 */
public enum FileType {
	csv, xls, xlsx, xml;
	
	/*
	 * check if the fileType exists in this enum.
	 */
	public static boolean isMember(String fileType){
	    for (FileType type : FileType.values()) {
	        if (type.name().equalsIgnoreCase((fileType))) {
	            return true;
	        }
	    }
	    return false;
	}
}
