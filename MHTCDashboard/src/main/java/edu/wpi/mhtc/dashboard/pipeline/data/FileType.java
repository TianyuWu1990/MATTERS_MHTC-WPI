package edu.wpi.mhtc.dashboard.pipeline.data;

public enum FileType {
	csv, xls, xlsx, xml;
	
	/*
	 * check if the fileType exists in this enum
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
