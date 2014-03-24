package edu.wpi.mhtc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import edu.wpi.mhtc.service.StatsService;

@Controller
public class StatsController {

	public static final String AVAILABLE_ENDPOINT = "/data/stats/available";
	public static final String STAT_ENDPOINT = "/data/stats/{state}/{stat}";

	// TODO proper view naming scheme
	public static final String AVAILIBLE_VIEW = "data_stats_available";
	public static final String STAT_VIEW = "data_stats_stat";
	
	private StatsService service;
	
	@Autowired
	public StatsController(StatsService service) {
		
		this.service = service;
	}
	
	@RequestMapping(value = AVAILABLE_ENDPOINT, method = RequestMethod.GET)
	public String availableEndpoint(Model model) {
		
		return AVAILIBLE_VIEW;
	}
	
	@RequestMapping(value = STAT_ENDPOINT, method = RequestMethod.GET)
	public String statEndpoint(Model model, @PathVariable String state, @PathVariable String stat) {
		
		return STAT_VIEW;
	}
	
	@RequestMapping(value = STAT_ENDPOINT, method = RequestMethod.POST)
	public String statPostEndpoint(Model model, @PathVariable String state, @PathVariable String stat) {
	
		return STAT_VIEW;
	}
	
}
