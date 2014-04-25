package edu.wpi.mhtc.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.Service;

public abstract class CachedData<CQ extends CachedData.CachedQuery, S extends Service, T>
{
	private static CachedStateBinData instance;
	private static Logger logger = LoggerFactory.getLogger(CachedData.class);
	
	private S service;
	private Cache<CQ, State> queryCache = new Cache<CQ, State>(16);
	
	protected CachedData(S service)
	{
		this.service = service;
	}
	
	public void query(Object... params)
	{
		CQ cq = createKey(params);
	}
	
	public abstract CQ createKey(Object... params);
	
	
	/**
	 * 
	 * The Key for the Cache
	 * 
	 * @author ted
	 *
	 */
	public static abstract class CachedQuery
	{
		protected Object[] objcontent;
		
		protected CachedQuery(Object... params)
		{
			this.objcontent = params;
		}
		
		public int hashCode()
		{
			int result = 1;
			for(Object o : objcontent)
			{
				result ^= o.hashCode();
			}
			return result ^ objcontent.length;
		}
		
		public boolean equals(Object other)
		{
			if (other instanceof CachedQuery)
			{
				return other.hashCode() == this.hashCode();
			}
			return false;
		}
	}
}
