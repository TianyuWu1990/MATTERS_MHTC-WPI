package edu.wpi.mhtc.service;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.model.Data.DataSource;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBMetric;
import edu.wpi.mhtc.persistence.DBState;

/**
 * 
 * @author ted
 * 
 * put all methods that the stats service implementations need to use into this file
 *
 */
public abstract class StatsService extends Service<DataSource, State>
{
	
	protected abstract State getStateBinData(String state, Integer binId);
	protected abstract List<DataPoint> getAllYearsForStateAndMetric(final DBState state, final DBMetric metric);
	protected abstract State getDataForState(DBState state, List<DBMetric> metrics);
	protected abstract State getDataForStateByName(String state, String metrics);
	public abstract List<DBMetric> getListOfMetricsFromCommaSeparatedString(String metric);
	
	
	@Override
	public List<Method> getMethods()
	{
		Method[] methods = this.getClass().getDeclaredMethods();
		List<Method> result = new LinkedList<Method>();
		for(Method m : methods)
		{
			if (m.getReturnType().equals(State.class))
			{
				result.add(m);
			}
		}
		return result;
	}
}
