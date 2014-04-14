package edu.wpi.mhtc;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.wpi.mhtc.controllers.TalentTabController;
import edu.wpi.mhtc.model.Data.MassRanking;
import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.rson.RSON;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws ParseException {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		model.addAttribute("jv_talent_tab", RSON.parse(new TalentTabController().getTalents()));
		model.addAttribute("jv_peer_states", RSON.parse(PeerStates.getInstance().getAsGrid(4)));
		model.addAttribute("jv_rankings", RSON.parse(new MassRanking().data));
		
		return "home";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Locale locale, Model model) throws ParseException {
		
		return "admin_tool";
	}
}
