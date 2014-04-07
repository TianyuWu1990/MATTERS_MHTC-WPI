package edu.wpi.mhtc.service.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.wpi.mhtc.httpexceptions.ResourceNotFoundException;
import edu.wpi.mhtc.model.StatsItem;
import edu.wpi.mhtc.model.StatsMeta;
import edu.wpi.mhtc.service.StatsService;

public class StatsServiceMock implements StatsService {

	private static final StatsMeta local = new StatsMeta("localtaxburden", "Local Tax Burden");
	
	@Override
	public List<StatsMeta> getAvailibleStatistics() {
		List<StatsMeta> stats = new ArrayList<StatsMeta>();
		stats.add(local);

		return stats;
	}

	@Override
	public List<StatsItem> getStatistics(String statistic, String state) {
		if (!statistic.equals("localtaxburden"))
			throw new ResourceNotFoundException();
		
		List<StatsItem> stats = new ArrayList<StatsItem>();
		
		stats.add(new StatsItem(local, state, 2009, 10));
		stats.add(new StatsItem(local, state, 2010, 11));
		stats.add(new StatsItem(local, state, 2011, 12));
		stats.add(new StatsItem(local, state, 2012, 13));
		stats.add(new StatsItem(local, state, 2013, 14));
		
		return stats;
		
	}

	@Override
	public List<StatsItem> getStatistics(String state) {
		List<StatsItem> stats = new ArrayList<StatsItem>();
		
		stats.add(new StatsItem(local, state, 2009, 10));
		stats.add(new StatsItem(local, state, 2010, 11));
		stats.add(new StatsItem(local, state, 2011, 12));
		stats.add(new StatsItem(local, state, 2012, 13));
		stats.add(new StatsItem(local, state, 2013, 14));
		
		return stats;
	}

}
