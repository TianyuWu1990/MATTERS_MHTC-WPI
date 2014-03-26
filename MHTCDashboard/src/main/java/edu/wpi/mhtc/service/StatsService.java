package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.StatsItem;
import edu.wpi.mhtc.model.StatsMeta;

public interface StatsService {

	public List<StatsMeta> getAvailibleStatistics();
	public List<StatsItem> getStatistics(String statistic, String state);
	public List<StatsItem> getStatistics(String state);
}
