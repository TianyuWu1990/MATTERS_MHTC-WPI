package edu.wpi.mhtc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wpi.mhtc.model.StateMeta;
import edu.wpi.mhtc.service.PeersService;

@Controller
public class PeersController {

	public static final String INITIALS_ENDPOINT = "/data/peers/initials";
	public static final String FULLNAME_ENDPOINT = "/data/peers/fullname";
	
	private PeersService service;
	
	@Autowired
	public PeersController(PeersService service) {
		this.service = service;
	}
	
	@RequestMapping(value = INITIALS_ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody List<String> initialsEndpoint(Model model) {
		
		return service.getPeersByInitials();
	}
	
	@RequestMapping(value = FULLNAME_ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody List<StateMeta> fullnameEndpoint(Model model) {
		
		return service.getPeersByFullName();
	}
}