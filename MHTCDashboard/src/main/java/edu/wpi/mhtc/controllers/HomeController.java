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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.wpi.mhtc.helpers.Mailer;
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
	private StateService stateService;
	
	@Autowired
	public HomeController(StatsService statsService, StateService stateService)
	{
		this.statsService = statsService;
		this.stateService = stateService;
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
		
		String maId = "21";
		
		List<DataSeries> massProfile = statsService.getStateBinData(maId, 45);
		List<DataSeries> massNational = statsService.getStateBinData(maId, 21);
		List<DataSeries> massTalent = statsService.getStateBinData(maId, 20);
		List<DataSeries> massCost = statsService.getStateBinData(maId, 37);
		List<DataSeries> massEconomy = statsService.getStateBinData(maId, 29);
		List<State> peers = stateService.getAllPeers();
		List<State> allstates= stateService.getAllStates(); 
		// TODO un-hardcode these bin ids
		model.addAttribute("jv_stats_profile",massProfile );
		model.addAttribute("jv_stats_national", massNational);
		model.addAttribute("jv_stats_talent", massTalent);
		model.addAttribute("jv_stats_cost", massCost);
		model.addAttribute("jv_stats_economy", massEconomy);
		model.addAttribute("jv_peer_states", new PeerStates(peers).getAsGrid(13));
		model.addAttribute("jv_all_states", new PeerStates(allstates).getAsGrid(13));
		
		
        model.addAttribute("jv_current_state", "MA");
		
		return "home";
	}
	
	@RequestMapping(value = "/{state}/table", method=RequestMethod.GET)
	public String home(Model model, @PathVariable("state") String state) {


        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("New state requested: " + state);

       // String stateId = "" + stateService.getStateByAbbreviation(state).getId();
        String stateId=state;
        
        List<DataSeries> profile = statsService.getStateBinData(stateId, 45);
		List<DataSeries> national = statsService.getStateBinData(stateId, 21);
		List<DataSeries> talent = statsService.getStateBinData(stateId, 20);
		List<DataSeries> cost = statsService.getStateBinData(stateId, 37);
		List<DataSeries> economy = statsService.getStateBinData(stateId, 29);
		List<State> peers = stateService.getAllPeers();
		List<State> allstates= stateService.getAllStates(); 
		
        model.addAttribute("jv_stats_profile",profile );
        model.addAttribute("jv_stats_national", national);
        model.addAttribute("jv_stats_talent", talent);
        model.addAttribute("jv_stats_cost", cost);
        model.addAttribute("jv_stats_economy", economy);
        model.addAttribute("jv_peer_states", new PeerStates(peers).getAsGrid(13));
        model.addAttribute("jv_all_states", new PeerStates(allstates).getAsGrid(13));
        model.addAttribute("jv_current_state", state);
        
        return "state_tab_display";
	    
	}
	
    @RequestMapping(value = "/spring_test", method = RequestMethod.GET)
    public @ResponseBody String help(Locale locale, Model model) throws Exception {
    	ApplicationContext context =  new ClassPathXmlApplicationContext("Spring-Mail.xml");

		return "Done!";
    }	
    
    /*************************** User services ***********************************/
    @RequestMapping(value = "/user/forgot", method = RequestMethod.GET)
    public String request_reset(Locale locale, Model model) throws Exception {
        
        return "forgot_password_form";
    }
    
    @RequestMapping(value = "/user/forgot/sent", method = RequestMethod.GET)
    public String request_reset_sent(Locale locale, Model model) throws Exception {
        // First, randomize the token
    	
    	// SQL Query execution
    	
        return "forgot_password_form";
    }  

    @RequestMapping(value = "/user/reset", method = RequestMethod.GET)
    public String reset_password_form(Locale locale, Model model, @RequestParam("token") String token) throws Exception {
    	model.addAttribute("token", token);
        return "reset_password";
    } 
    
    @RequestMapping(value = "/user/reset/password", method = RequestMethod.POST)
    public String reset_password(Locale locale, Model model, @RequestParam("token") String token, @RequestParam("password") String password) throws Exception {
    	/* Reset password and clear the token*/
        return "reset_password_completed";
    }
    
    /********** Authentication **********/
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage() {
        return "logoutPage";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "loginPage";
    }
    /********** End authentication pages **********/
    
}
