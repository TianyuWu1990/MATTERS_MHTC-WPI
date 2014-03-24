package edu.wpi.mhtc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.wpi.mhtc.service.HeadlinesService;

@Controller
public class HeadlinesController {
	
	// TODO proper view naming scheme
	public static final String HEADLINES_ENDPOINT = "/data/headlines";

	private HeadlinesService service;
	
	@Autowired
	public HeadlinesController(HeadlinesService service) {
		
		this.service = service;
	}
	
	@RequestMapping(value = HEADLINES_ENDPOINT, method = RequestMethod.GET)
	public String headlinesEndpoint(Model model) {	
		return "data_headlines";
	}
	
}