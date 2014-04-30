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

import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBMetric;
import edu.wpi.mhtc.persistence.DBState;
import edu.wpi.mhtc.persistence.StateMapper;
import edu.wpi.mhtc.service.MetricsService;
import edu.wpi.mhtc.service.StatsService;
import edu.wpi.mhtc.service.StatsServiceJDBC;

@Controller
public class StatsController {

	public static final String AVAILABLE_ENDPOINT = "/data/stats/available";
	public static final String STAT_ENDPOINT = "/data/stats/query";

	private StatsServiceJDBC statsService;
	private MetricsService metricsService;
	private StateMapper stateMapper;

	@Autowired
	public StatsController(StatsServiceJDBC service, MetricsService mservice) {

		this.statsService = service;
		this.metricsService = mservice;
	}

	@RequestMapping(value = AVAILABLE_ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody
	List<DBMetric> availableEndpoint(Model model) {

		return metricsService.getAvailible().getMetrics();
	}

	@RequestMapping(value = STAT_ENDPOINT, method = RequestMethod.GET, params = { "states", "metrics" })
	public @ResponseBody
	List<State> statEndpoint(Model model, @RequestParam(value = "states") String states,
			@RequestParam(value = "metrics") String metrics) {

		return getDataForSpecificStates(splitStateNames(states), metrics);
	}

	private List<String> splitStateNames(String states) {

		if (states.equals("all")) {

			List<String> stateNames = new LinkedList<String>();

			List<DBState> dbStates = stateMapper.getAllStates();

			for (DBState dbState : dbStates) {
				stateNames.add(dbState.getName());
			}

			return stateNames;
		} else {

			String[] splits = states.split(",");

			return Arrays.asList(splits);
		}

	}

	private List<State> getDataForSpecificStates(List<String> stateNames, String metrics) {

		List<State> states = new LinkedList<State>();

		for (String state : stateNames) {
			states.add(statsService.getDataForState(state, metrics));
			//states.add(statsService.getAvailible("getDataForState", state, metrics));
		}

		return states;
	}

}
