package edu.wpi.mhtc;

import java.io.IOException;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.wpi.mhtc.cache.CachedStateBinData;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.service.StatsService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private StatsService service;
	
	@Autowired
	public HomeController(StatsService service)
	{
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
	public String home(Locale locale, Model model) throws JsonGenerationException, JsonMappingException, IOException, ParseException
	{
		
		
		CachedStateBinData db = CachedStateBinData.getInstance(service);
		
		
		ObjectMapper om = new ObjectMapper();
		State massNational = db.query("getStateBinData", "MA", 21);
		State massTalent = db.query("getStateBinData", "MA", 20);
		State massCost = db.query("getStateBinData", "MA", 37);
		State massEconomy = db.query("getStateBinData", "MA", 29);
		
		
		
		// TODO un-hardcode these bin ids
		// TODO cache this. a req to the db every time is why we keep going down
		model.addAttribute("jv_stats_national", om.writeValueAsString(massNational.getParams()));
		model.addAttribute("jv_stats_talent", om.writeValueAsString(massTalent.getParams()));
		model.addAttribute("jv_stats_cost", om.writeValueAsString(massCost.getParams()));
		model.addAttribute("jv_stats_economy", om.writeValueAsString(massEconomy.getParams()));
		
		//model.addAttribute("jv_peer_states", RSON.parse(peerStates.getAsGrid(4)));
		
		return "home";
	}
}
