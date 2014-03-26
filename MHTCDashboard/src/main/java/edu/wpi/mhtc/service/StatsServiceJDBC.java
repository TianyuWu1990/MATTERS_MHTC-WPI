package edu.wpi.mhtc.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.model.StatsItem;
import edu.wpi.mhtc.model.StatsMeta;

public class StatsServiceJDBC implements StatsService {

	private JdbcTemplate template;

	public StatsServiceJDBC(JdbcTemplate template) {
		this.template = template;

	}

	@Override
	public List<StatsMeta> getAvailibleStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatsItem> getStatistics(String statistic, String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatsItem> getStatistics(String state) {
		// TODO Auto-generated method stub
		return null;
	}

}
