package edu.wpi.mhtc.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.mhtc.model.Model;
import edu.wpi.mhtc.service.Service;

/**
 * 
 * @author ted
 *
 * @param <T> The type of data in the list (for dust)
 * @param <M> the model that contains and manipulates a list of this data
 * @param <CQ> a query building class
 * @param <S> the database service for extracting the class
 */
public abstract class CachedData<T, M extends Model<T>, CQ extends CachedData.CachedQuery, S extends Service<T, M>>
{
	private static Logger logger = LoggerFactory.getLogger(CachedData.class);
	
	private S service;
	private Cache<CQ, M> queryCache = new Cache<CQ, M>(64);
	
	/**
	 * 
	 * @param service the service for getting data
	 */
	protected CachedData(S service)
	{
		this.service = service;
	}
	
	/**
	 * 
	 * @param params the list of params:
	 * 			first param is the function type
	 * 			rest of the params are the params for the provided function
	 * @return the model that contins the data
	 */
	public M query(Object... params)
	{
		CQ cq = createKey(params);
		M model = queryCache.get(cq);
		if (model == null)
		{
			model = service.getAvailible(params);
			if (queryCache.put(cq, model))
			{
				logger.info("cache full: swapping");
			}
		}
		return model;
		
	}
	
	/**
	 * 
	 * @param params create a key object
	 * @return a new key object
	 */
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
		
		/**
		 * 
		 * @param params the params to hash together for this key
		 */
		protected CachedQuery(Object... params)
		{
			this.objcontent = params;
		}
		
		/**
		 * generate a hashcode
		 */
		public int hashCode()
		{
			int result = 1;
			for(Object o : objcontent)
			{
				result ^= o.hashCode();
			}
			return result ^ objcontent.length;
		}
		
		/**
		 * check if these two are equal
		 */
		public boolean equals(Object other)
		{
			if (other instanceof CachedQuery)
			{
				Object[] othercontent = ((CachedQuery)other).objcontent;
				if (objcontent.length != othercontent.length)
				{
					return false;
				}
				for(int i=0; i<objcontent.length; i++)
				{
					if (othercontent[i] == null ||  !(othercontent[i].equals(objcontent[0])))
					{
						return false;
					}
				}
				return true;
			}
			return false;
		}
	}
}
