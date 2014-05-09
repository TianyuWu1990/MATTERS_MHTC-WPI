package edu.wpi.mhtc.service;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBState;

public abstract class PeersService extends Service<State, PeerStates>{

	@Override
	public List<Method> getMethods()
	{
		Method[] methods = this.getClass().getDeclaredMethods();
		List<Method> result = new LinkedList<Method>();
		for(Method m : methods)
		{
			if (m.getReturnType().equals(PeerStates.class))
			{
				result.add(m);
			}
		}
		return result;
	}
	
	
	public abstract List<DBState> getPeersFull();
	public abstract List<State> getTopTenPeersForMetric(String metric, int year);
	public abstract List<State> getBottomTenPeersForMetric(String metric, int year);
	
	protected abstract PeerStates getPeers();
}
