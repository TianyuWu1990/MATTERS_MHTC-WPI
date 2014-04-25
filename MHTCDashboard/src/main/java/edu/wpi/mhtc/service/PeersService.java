package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBState;

public interface PeersService extends Service{

	public List<State> getPeers();
	public List<DBState> getPeersFull();
}
