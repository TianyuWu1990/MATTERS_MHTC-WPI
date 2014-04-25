package edu.wpi.mhtc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wpi.mhtc.model.state.State;
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
}