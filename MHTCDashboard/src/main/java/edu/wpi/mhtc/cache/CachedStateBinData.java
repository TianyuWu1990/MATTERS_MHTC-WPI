package edu.wpi.mhtc.cache;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.StatsService;

/**
 * 
 * @author ted
 *
 */
public class CachedStateBinData
{
	private static Cache<StateQuery, State> queryCache = new Cache<StateQuery, State>(16);
	private static Map<StatsService, CachedStateBinData> instances = new HashMap<StatsService, CachedStateBinData>();
	
	
	private StatsService service;
	
	/**
	 * 
	 * @param service
	 */
	private CachedStateBinData(StatsService service)
	{
		this.service = service;
	}
	
	/**
	 * 
	 * @param service
	 * @return
	 */
	public static CachedStateBinData getInstance(StatsService service)
	{
		CachedStateBinData instance = instances.get(service);
		if (instance == null)
		{
			instance = new CachedStateBinData(service);
			instances.put(service, instance);
		}
		return instance;
	}
	
	/**
	 * 
	 * @param service
	 */
	private static void killInstance(StatsService service)
	{
		instances.remove(service);
	}
	
	public void kill()
	{
		killInstance(this.service);
	}
	
	/**
	 * 
	 * @param nameOrInit the name or initial of the state
	 * @param bin the bin for the data
	 * @param service the data service
	 * @return the State, cached or imported into the cache
	 */
	public State query(String nameOrInit, int bin)
	{
		StateQuery sq = new StateQuery(nameOrInit, bin);
		State s = queryCache.get(sq);
		if (s == null)
		{
			s = service.getStateBinData(nameOrInit, bin);
			queryCache.put(sq, s);
		}
		return s;
	}
	
	
	/**
	 * 
	 * @author ted
	 *
	 */
	private class StateQuery
	{
		//TODO: expire by time
		private String initials;
		private int binID;
		
		public StateQuery(String initials, int bin)
		{
			this.initials = initials;
			this.binID = bin;
		}
		
		@Override
		public boolean equals(Object other)
		{
			if (other instanceof StateQuery)
			{
				StateQuery sq = (StateQuery)other;
				return sq.initials.equals(initials) && binID == sq.binID;
			}
			return false;
		}
	}
}
