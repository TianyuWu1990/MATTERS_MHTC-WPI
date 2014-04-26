package edu.wpi.mhtc.service;

import edu.wpi.mhtc.model.Model;

/**
 * 
 * @author ted
 *
 */
public interface Service <T, M extends Model<T>>
{
	/**
	 * 
	 * @param params the params to query the db
	 * @return the model that is the result of the db query
	 */
	public M getAvailible(Object... params);
}
