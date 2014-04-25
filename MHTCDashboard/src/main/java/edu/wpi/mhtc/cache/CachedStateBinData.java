package edu.wpi.mhtc.cache;

import edu.wpi.mhtc.model.Data.DataSource;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.StatsService;

/**
 * 
 * @author ted
 *
 */
public class CachedStateBinData extends CachedData<DataSource, State, CachedStateBinData.StateQuery, StatsService>
{
	private static CachedStateBinData instance;

	
	/**
	 * 
	 * @param service
	 */
	protected CachedStateBinData(StatsService service)
	{
		super(service);
	}
	
	/**
	 * 
	 * @param service
	 * @return
	 */
	public static CachedStateBinData getInstance(StatsService service)
	{
		if (instance == null)
		{
			instance = new CachedStateBinData(service);
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
