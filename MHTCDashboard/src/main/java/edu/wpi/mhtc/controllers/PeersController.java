package edu.wpi.mhtc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.wpi.mhtc.service.PeersService;

@Controller
public class PeersController {

	// TODO proper view naming scheme
	public static final String INITIALS_ENDPOINT = "/data/peers/initials";
	public static final String INITIALS_VIEW = "data_peers_initials";
	
	public static final String FULLNAME_ENDPOINT = "/data/peers/fullname";
	public static final String FULLNAME_VIEW = "data_peers_fullname";
	
	private PeersService service;
	
	@Autowired
	public PeersController(PeersService service) {
		
		this.service = service;
	}
	
	@RequestMapping(value = INITIALS_ENDPOINT, method = RequestMethod.GET)
	public String initialsEndpoint(Model model) {
		
		return INITIALS_VIEW;
	}
	
	@RequestMapping(value = INITIALS_ENDPOINT, method = RequestMethod.GET)
	public String fullnameEndpoint(Model model) {
		
		return FULLNAME_VIEW;
	}
}