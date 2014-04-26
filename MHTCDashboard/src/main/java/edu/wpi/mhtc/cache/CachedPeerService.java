package edu.wpi.mhtc.cache;

import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.PeersService;

/**
 * 
 * @author ted
 *
 */
public class CachedPeerService extends CachedData<State, PeerStates, CachedPeerService.StateQuery, PeersService>
{
	private static CachedPeerService instance;

	
	/**
	 * 
	 * @param service
	 */
	protected CachedPeerService(PeersService service)
	{
		super(service);
	}
	
	/**
	 * 
	 * @param service
	 * @return
	 */
	public static CachedPeerService getInstance(PeersService service)
	{
		if (instance == null)
		{
			instance = new CachedPeerService(service);
		}
		return instance;
	}
	
	
	/**
	 * 
	 * @author ted
	 *
	 */
	class StateQuery extends CachedData.CachedQuery
	{
		protected StateQuery(Object... params)
		{
			super(params);
		}
	}


	@Override
	public StateQuery createKey(Object... params)
	{
		return new StateQuery(params);
	}
}
