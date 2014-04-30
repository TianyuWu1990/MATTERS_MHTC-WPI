package edu.wpi.mhtc.service;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.LoggerFactory;

import edu.wpi.mhtc.model.Model;

/**
 * 
 * @author ted
 *
 */
public abstract class Service <T, M extends Model<T>>
{
	/**
	 * 
	 * @param params the params to query the db
	 * @return the model that is the result of the db query
	 */
	public M getAvailible(Object... params)
	{
		if (params.length == 0)
		{
			return null;
		}
		else
		{
			List<Method> methods = getMethods();
			LoggerFactory.getLogger(this.getClass()).info("there are {} methods", methods.size());
			for(Method m : methods)
			{
				if (m.getName().equals(params[0]))
				{
					Object[] newParams = new Object[params.length - 1];
					for(int i=0;i<newParams.length; i++)
					{
						newParams[i] = params[i+1];
					}
					
					return invokeThis(m, newParams);
				}
			}
			return null;
		}
	}
	
	/**
	 * Must be implemented in the subclasses to use the "getDeclaredMethods" function
	 * @return a list of all methods that have a return type of M
	 */ 
	public abstract List<Method> getMethods();
	
	/**
	 * must be implemented in the subclass for casting saftey
	 * 
	 * @param m the method to invoke
	 * @param params the parameters for the method
	 * @return the result of the invoked method
	 */
	public abstract M invokeThis(Method m, Object[] params);
}
