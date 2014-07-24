package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.state.State;

public interface StateService{

	List<State> getTopTenStatesForMetric(Metric metric);
	List<State> getBottomTenStatesForMetric(Metric metric);
	
	List<State> getAllPeers();
    List<State> getAllStates();
    
    State getStateById(int id);
    
}
