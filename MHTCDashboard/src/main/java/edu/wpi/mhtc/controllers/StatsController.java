package edu.wpi.mhtc.controllers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wpi.mhtc.model.Data.DataSeries;
import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.StateMapper;
import edu.wpi.mhtc.service.MetricsService;
import edu.wpi.mhtc.service.StatsService;
import edu.wpi.mhtc.service.StatsServiceJDBC;

@Controller
public class StatsController {

	private StatsService statsService;
	private MetricsService metricsService;
	private StateMapper stateMapper;

	@Autowired
	public StatsController(StatsServiceJDBC service, MetricsService mservice) {

		this.statsService = service;
		this.metricsService = mservice;
	}

	@RequestMapping(value = "/data/stats/available", method = RequestMethod.GET)
	public @ResponseBody
	List<Metric> availableEndpoint(Model model) {

		return metricsService.getAllMetrics();
	}

	@RequestMapping(value = "/data/stats/query", method = RequestMethod.GET, params = { "states", "metrics" })
	public @ResponseBody
	List<List<DataSeries>> statEndpoint(Model model, @RequestParam(value = "states") String states,
			@RequestParam(value = "metrics") String metrics) {

		return getDataForSpecificStates(splitStateNames(states), metrics);
	}

	private List<String> splitStateNames(String states) {

		if (states.equals("all")) {

			List<String> stateNames = new LinkedList<String>();

			List<State> dbStates = stateMapper.getAllStates();

			for (State dbState : dbStates) {
				stateNames.add(dbState.getName());
			}

			return stateNames;
		} else {

			String[] splits = states.split(",");

			return Arrays.asList(splits);
		}

	}

	private List<List<DataSeries>> getDataForSpecificStates(List<String> stateNames, String metrics) {

		List<List<DataSeries>> states = new LinkedList<List<DataSeries>>();

		for (String state : stateNames) {
			states.add(statsService.getDataForStateByName(state, metrics));
		}

		return states;
	}

}
