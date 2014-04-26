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

import edu.wpi.mhtc.cache.CachedPeerService;
import edu.wpi.mhtc.cache.CachedStatsService;
import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.rson.RSON;
import edu.wpi.mhtc.service.PeersService;
import edu.wpi.mhtc.service.StatsService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private StatsService statsService;
	private PeersService peersService;
	
	@Autowired
	public HomeController(StatsService ss, PeersService ps)
	{
		this.statsService = ss;
		this.peersService = ps;
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
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		CachedStatsService css = CachedStatsService.getInstance(statsService);
		CachedPeerService cps = CachedPeerService.getInstance(peersService);
		
		
		ObjectMapper om = new ObjectMapper();
		State massNational = css.query("getStateBinData", "MA", 21);
		State massTalent = css.query("getStateBinData", "MA", 20);
		State massCost = css.query("getStateBinData", "MA", 37);
		State massEconomy = css.query("getStateBinData", "MA", 29);
		PeerStates peers = cps.query("getPeers");
		
		logger.info("national datasize: {}", massNational.getParams().size());
		logger.info("talent datasize: {}",massTalent.getParams().size());
		logger.info("cost datasize: {}",massCost.getParams().size());
		logger.info("economy datasize: {}",massEconomy.getParams().size());
		
		
		// TODO un-hardcode these bin ids
		model.addAttribute("jv_stats_national", om.writeValueAsString(massNational.getParams()));
		model.addAttribute("jv_stats_talent", om.writeValueAsString(massTalent.getParams()));
		model.addAttribute("jv_stats_cost", om.writeValueAsString(massCost.getParams()));
		model.addAttribute("jv_stats_economy", om.writeValueAsString(massEconomy.getParams()));
		model.addAttribute("jv_peer_states", RSON.parse(peers.getAsGrid(4)));
		
		return "home";
	}
}
