package edu.wpi.mhtc.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.wpi.mhtc.model.Data.DataSeries;
import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.StateService;
import edu.wpi.mhtc.service.StatsService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private StatsService statsService;
	private StateService peersService;
	
	@Autowired
	public HomeController(StatsService ss, StateService ps)
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
	public String home(Locale locale, Model model)
	{
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Home called");
		
		List<DataSeries> massProfile = statsService.getStateBinData("MA", 45);/**AAEJ**/
		List<DataSeries> massNational = statsService.getStateBinData("MA", 21);
		List<DataSeries> massTalent = statsService.getStateBinData("MA", 20);
		List<DataSeries> massCost = statsService.getStateBinData("MA", 37);
		List<DataSeries> massEconomy = statsService.getStateBinData("MA", 29);
		List<State> peers = peersService.getAllStates();
		
		// TODO un-hardcode these bin ids
		model.addAttribute("jv_stats_profile",massProfile );/**AAEJ**/
		model.addAttribute("jv_stats_national", massNational);
		model.addAttribute("jv_stats_talent", massTalent);
		model.addAttribute("jv_stats_cost", massCost);
		model.addAttribute("jv_stats_economy", massEconomy);
		model.addAttribute("jv_peer_states", new PeerStates(peers).getAsGrid(13));
		
        model.addAttribute("jv_current_state", "MA");
		
		return "home";
	}
	
	@RequestMapping(value = "/{state}/table", method=RequestMethod.GET)
	public String home(Model model, @PathVariable("state") String state) {


        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("New state requested: " + state);
	    
        List<DataSeries> massProfile = statsService.getStateBinData(state, 45);/**AAEJ**/
        List<DataSeries> massNational = statsService.getStateBinData(state, 21);
        List<DataSeries> massTalent = statsService.getStateBinData(state, 20);
        List<DataSeries> massCost = statsService.getStateBinData(state, 37);
        List<DataSeries> massEconomy = statsService.getStateBinData(state, 29);
        List<State> peers = peersService.getAllStates();

        model.addAttribute("jv_stats_profile",massProfile ); /**AAEJ**/
        model.addAttribute("jv_stats_national", massNational);
        model.addAttribute("jv_stats_talent", massTalent);
        model.addAttribute("jv_stats_cost", massCost);
        model.addAttribute("jv_stats_economy", massEconomy);
        model.addAttribute("jv_peer_states", new PeerStates(peers).getAsGrid(13));
        
        model.addAttribute("jv_current_state", state);
        
        return "state_tab_display";
	    
	}
	
}
