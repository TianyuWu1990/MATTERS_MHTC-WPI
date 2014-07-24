package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.Data.DataSeries;

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
	
}
