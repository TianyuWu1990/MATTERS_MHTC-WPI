package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.StatsItem;
import edu.wpi.mhtc.model.StatsMeta;
import edu.wpi.mhtc.persistence.ProcedureTemplate;

public class StatsServiceJDBC implements StatsService {

	private ProcedureTemplate template;

	public StatsServiceJDBC(ProcedureTemplate template) {
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
