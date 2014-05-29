package edu.wpi.mhtc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.StateService;

/**
 * A controller that provides endpoints that returns states or lists of states.
 * 
 * @author Stokes
 */
@Controller
public class StateController {

    private StateService service;

    @Autowired
    public StateController(StateService service) {
        this.service = service;
    }

    @RequestMapping(value = "/data/peers", method = RequestMethod.GET)
    public @ResponseBody
    List<State> initialsEndpoint(Model model) {
        return service.getAllPeers();
    }

    /**
     * Returns a list of all states supported by the system.
     */
    @RequestMapping(value = "/data/states/all", method = RequestMethod.GET)
    public @ResponseBody
    List<State> allStatesEndpoint() {
        return service.getAllStates();
    }

    @RequestMapping(value = "/data/peers/top", params = { "metric", "year" })
    public @ResponseBody
    List<State> topTenEndpoint(Model model, @RequestParam("metric") String metric, @RequestParam("year") int year) {
        return service.getTopTenPeersForMetric(metric, year);
    }

    @RequestMapping(value = "/data/peers/bottom", params = { "metric", "year" })
    public @ResponseBody
    List<State> bottomTenEndpoint(Model model, @RequestParam("metric") String metric, @RequestParam("year") int year) {
        return service.getBottomTenPeersForMetric(metric, year);
    }
}