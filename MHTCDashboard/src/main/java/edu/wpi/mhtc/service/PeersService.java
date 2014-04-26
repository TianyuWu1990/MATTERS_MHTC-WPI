package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBState;

public interface PeersService extends Service<State, PeerStates>{

	@Override
	public PeerStates getAvailible(Object... params);
	
	public List<DBState> getPeersFull();
}
