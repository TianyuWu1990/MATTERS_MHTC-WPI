/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.service.dashboard;

import java.util.List;

import edu.wpi.mhtc.model.dashboard.DataSeries;

/**
 * 
 * @author ted
 * 
 * put all methods that the stats service implementations need to use into this file
 *
 */
public interface StatsService
{
	
    List<DataSeries> getStateBinData(String state, Integer binId);
	List<DataSeries> getDataForStateByName(String state, String metrics);
	List<DataSeries> getStateProfile(String state);
	
}
