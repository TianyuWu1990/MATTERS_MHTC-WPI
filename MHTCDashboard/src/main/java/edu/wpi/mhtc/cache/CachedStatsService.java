package edu.wpi.mhtc.cache;

import edu.wpi.mhtc.model.Data.DataSource;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.StatsService;

/**
 * 
 * @author ted
 *
 */
public class CachedStatsService extends CachedData<DataSource, State, CachedStatsService.StateQuery, StatsService>
{
	private static CachedStatsService instance;

	
	/**
	 * 
	 * @param service
	 */
	protected CachedStatsService(StatsService service)
	{
		super(service);
	}
	
	/**
	 * 
	 * @param service
	 * @return
	 */
	public static CachedStatsService getInstance(StatsService service)
	{
		if (instance == null)
		{
			instance = new CachedStatsService(service);
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
