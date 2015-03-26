/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.service.dashboard;

import java.util.List;

import edu.wpi.mhtc.model.dashboard.State;

public interface StateService{

	List<State> getPeersFull();
	List<State> getTopTenPeersForMetric(String metric, int year);
	List<State> getBottomTenPeersForMetric(String metric, int year);
	
	List<State> getAllPeers();
    List<State> getAllStates();
}
