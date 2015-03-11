/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */

package edu.wpi.mhtc.dashboard.pipeline.cleaner;

public interface ICleaner {
	String clean(String val) throws Exception;
}