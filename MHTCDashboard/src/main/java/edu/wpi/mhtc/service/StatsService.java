package edu.wpi.mhtc.service;

import edu.wpi.mhtc.model.state.State;

public interface StatsService {

	public State getDataForState(String state, String metrics);
}
