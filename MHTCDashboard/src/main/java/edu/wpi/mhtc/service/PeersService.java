package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBState;

public interface PeersService{

	List<DBState> getPeersFull();
	List<State> getTopTenPeersForMetric(String metric, int year);
	List<State> getBottomTenPeersForMetric(String metric, int year);
	
	List<State> getAllPeers();
}
