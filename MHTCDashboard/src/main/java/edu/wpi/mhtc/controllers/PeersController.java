package edu.wpi.mhtc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.DBState;
import edu.wpi.mhtc.service.PeersService;

@Controller
public class PeersController {

	public static final String ENDPOINT = "/data/peers";
	
	private PeersService service;
	
	@Autowired
	public PeersController(PeersService service) {
		this.service = service;
	}
	
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody List<State> initialsEndpoint(Model model)
	{
		return service.getAvailible("getPeers").getStates();
	}
	
	@RequestMapping(value = "/data/peers/top", params = { "metric", "year" })
	public @ResponseBody List<State> topTenEndpoint(Model model, @RequestParam("metric") String metric, @RequestParam("year") int year) {
	    return service.getTopTenPeersForMetric(metric, year);
	}
	
	@RequestMapping(value = "/data/peers/bottom", params = { "metric", "year" })
    public @ResponseBody List<State> bottomTenEndpoint(Model model, @RequestParam("metric") String metric, @RequestParam("year") int year) {
        return service.getBottomTenPeersForMetric(metric, year);
    }
}