/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.parser;

import edu.wpi.mhtc.model.admin.Statistic;

public interface IParser extends Iterable<Statistic> {
	
	public boolean parseAll() throws Exception;

	public void getHeaderColumnNames() throws Exception;
}

