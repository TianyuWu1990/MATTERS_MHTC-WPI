package edu.wpi.mhtc.service;

import edu.wpi.mhtc.model.Data.DataSource;
import edu.wpi.mhtc.model.state.State;

public interface StatsService extends Service<DataSource, State>{

	@Override
	State getAvailible(Object... params);

	State getStateBinData(String state, Integer binId);
}
