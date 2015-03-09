/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.parser;

import edu.wpi.mhtc.dashboard.pipeline.dao.Statistic;

public interface IParser extends Iterable<Statistic> {
	
	public boolean parseAll() throws Exception;

	public void getHeaderColumnNames() throws Exception;
}

