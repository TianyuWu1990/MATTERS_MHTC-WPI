package edu.wpi.mhtc.service;

import edu.wpi.mhtc.model.state.State;

public interface StatsService {

	State getDataForState(String state, String metrics);

	State getStateBinData(String state, int binId);
}
