package edu.wpi.mhtc;

import java.io.IOException;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.rson.RSON;
import edu.wpi.mhtc.service.StatsService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private PeerStates peerStates;
	private StatsService service;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	public HomeController(PeerStates peerStates, StatsService service)
	{
		this.peerStates = peerStates;
		this.service = service;
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws ParseException, JsonGenerationException, JsonMappingException, IOException
	{
		model.addAttribute("jv_peer_states", RSON.parse(peerStates.getAsGrid(4)));
		
		// TODO un-hardcode these bin ids
		// TODO cache this. a req to the db every time is why we keep going down
		model.addAttribute("jv_stats_national", new ObjectMapper().writeValueAsString(service.getStateBinData("MA", 21).getParams()));
		model.addAttribute("jv_stats_talent", new ObjectMapper().writeValueAsString(service.getStateBinData("MA", 20).getParams()));
		model.addAttribute("jv_stats_cost", new ObjectMapper().writeValueAsString(service.getStateBinData("MA", 37).getParams()));
		model.addAttribute("jv_stats_economy", new ObjectMapper().writeValueAsString(service.getStateBinData("MA", 29).getParams()));
		
		
		return "home";
	}
}
