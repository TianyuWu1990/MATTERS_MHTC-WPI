/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.steadystate.css.parser.ParseException;

import edu.wpi.mhtc.dashboard.pipeline.dao.UserService;
import edu.wpi.mhtc.helpers.Mailer;
import edu.wpi.mhtc.model.Data.DataSeries;
import edu.wpi.mhtc.model.admin.User;
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
	private UserService userService;
	
	@Autowired
    ReCaptchaImpl reCaptcha;
	
	@Autowired
	public HomeController(StatsService statsService, StateService stateService, UserService userService)
	{
		this.statsService = statsService;
		this.stateService = stateService;
		this.userService = userService;
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
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	      
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Home called");
		
		String maId = "21";
		
//		List<DataSeries> massProfile = statsService.getStateBinData(maId, 45);
		List<DataSeries> massNational = statsService.getStateBinData(maId, 21);
		List<DataSeries> massTalent = statsService.getStateBinData(maId, 20);
		List<DataSeries> massCost = statsService.getStateBinData(maId, 37);
		List<DataSeries> massEconomy = statsService.getStateBinData(maId, 29);
		List<State> peers = stateService.getAllPeers();
		List<State> allstates= stateService.getAllStates(); 
		// TODO un-hardcode these bin ids
//		model.addAttribute("jv_stats_profile",massProfile );
		model.addAttribute("jv_stats_national", massNational);
		model.addAttribute("jv_stats_talent", massTalent);
		model.addAttribute("jv_stats_cost", massCost);
		model.addAttribute("jv_stats_economy", massEconomy);
		model.addAttribute("jv_peer_states", new PeerStates(peers).getAsGrid(13));
		model.addAttribute("jv_all_states", new PeerStates(allstates).getAsGrid(13));
		model.addAttribute("username", username);
		
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
    
    /*************************** User services ***********************************/
    @RequestMapping(value = "/user/forgot", method = RequestMethod.GET)
    public String request_reset(Locale locale, Model model) throws Exception {
        return "forgot_password_form";
    }
    
    @RequestMapping(value = "/user/forgot/sent", method = RequestMethod.POST)
    public String request_reset_sent(Locale locale, Model model, @RequestParam("email") String email, 
    															@RequestParam("recaptcha_challenge_field") String challangeField,
    															@RequestParam("recaptcha_response_field") String responseField,
    															ServletRequest servletRequest,
    												            SessionStatus sessionStatus) throws Exception {
    	String remoteAddress = servletRequest.getRemoteAddr();
        ReCaptchaResponse reCaptchaResponse = this.reCaptcha.checkAnswer(remoteAddress, challangeField, responseField);
 
        if (reCaptchaResponse.isValid()){
        	
			if (userService.setToken(email)) {
				model.addAttribute("completed", true);		    
			} else {
				model.addAttribute("completed", false);
			}
    	
	        return "forgot_password_form_submit";
        } else {
        	
        	model.addAttribute("completed", false);
        	model.addAttribute("invalid_captcha", true);
        	
        	return "forgot_password_form_submit";
        }
    }  

    @RequestMapping(value = "/user/reset", method = RequestMethod.GET)
    public String reset_password_form(Locale locale, Model model, @RequestParam("token") String token) throws Exception {
    	if (!token.equals("")) {
	    	User u = userService.confirmToken(token);
			
			if (u == null) {
				model.addAttribute("error", true);
			} else {
				model.addAttribute("error", false);
				model.addAttribute("email", u.getEmail());
			}
    	} else {
    		model.addAttribute("error", true);
    	}
    	
    	model.addAttribute("token", token);
        return "reset_password";
    } 
    
    @RequestMapping(value = "/user/reset/submit", method = RequestMethod.POST)
    public String reset_password(Locale locale, Model model, @RequestParam("token") String token, @RequestParam("password") String password) throws Exception {
    	if (token.equals("")) {
    		model.addAttribute("error", true);
    	} else {

    		if (userService.resetUserPassword(password, token)) {
    			model.addAttribute("error", false);
    		} else {
    			model.addAttribute("error", true);
    		}
    	}
		
        return "reset_password_submit";
    }
    /********** Registration **********/
    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String register(Locale locale, Model model) {
        return "registerPage";
    }    
    
    @RequestMapping(value = "/user/register/submit", method = RequestMethod.POST)
    public String register_submit(Locale locale, Model model, @RequestParam("UserName") String username, @RequestParam("Email") String email, 
    		@RequestParam("Password") String password, @RequestParam("FirstName") String firstName, @RequestParam("LastName") String lastName) throws SQLException {
    	
    	userService.createUser(username, password, firstName, lastName, email, false);
			
		model.addAttribute("error", false);
        return "register_page_submit";
    } 
    
    /********** Feedback Page 
     * @throws SQLException **********/
    @RequestMapping(value = "/feedback_post", method = RequestMethod.POST)
    public String feedback_post(Locale locale, Model model, @RequestParam("subject") String subject, @RequestParam("comments") String comments, 
															    		@RequestParam("recaptcha_challenge_field") String challangeField,
																		@RequestParam("recaptcha_response_field") String responseField,
																		ServletRequest servletRequest,
															            SessionStatus sessionStatus) throws SQLException 
    {
    	
    	String remoteAddress = servletRequest.getRemoteAddr();
        ReCaptchaResponse reCaptchaResponse = this.reCaptcha.checkAnswer(remoteAddress, challangeField, responseField);
 
        if(reCaptchaResponse.isValid()){
        	// Retrieve necessary information
    	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        	// Get user who submitted
        	User u = userService.get(auth.getName());
        	
        	// Send the email
        	try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml")) {
        		Mailer mm = (Mailer) context.getBean("mailMail");
    	        mm.sendFeedbackEmail(u.getEmail(), subject, comments);
        	} 			    
	        	        
        	// Load the notification
        	model.addAttribute("completed", true);
        	model.addAttribute("invalid_captcha", false);
	        return "feedback_submit";
        } else {
        	model.addAttribute("completed", false);
        	model.addAttribute("invalid_captcha", true);
        	return "feedback_submit";
        }
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
    /********** Excel exporter 
     * Exports excel file for the chart data
     * @throws IOException **********/
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public @ResponseBody String excel_exporter(Locale locale, Model model, @RequestParam("data") String data, HttpServletResponse response) throws IOException {	
		Workbook wb;
		wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();

    	JSONObject json = new JSONObject(data);
    	String year = json.getString("year");
		JSONArray rows =  json.getJSONArray("rows");
		
		// For the first row, put the year number into
		Row yearRow = sheet.createRow(0);
		Cell sheetCell = yearRow.createCell(0);
		sheetCell.setCellValue(year);
		
		
		//Write header row, first col State followed by metric names
		Row headerRow = sheet.createRow(1);
		Cell cell = headerRow.createCell(0);
		cell.setCellValue("State");
		
		JSONArray row = rows.getJSONArray(0);
		for (int j = 1; j < row.length(); j++) {
			cell = headerRow.createCell(j);
			cell.setCellValue(row.getString(j));
		}
		
		// Read and put data into the Excel file
		for (int i = 0; i < rows.length()-1; i++) {
			row = rows.getJSONArray(i+1);
			Row sheetRow = sheet.createRow(i+2);
			for (int j = 0; j < row.length(); j++) {
				cell = sheetRow.createCell(j);
				cell.setCellValue(row.getString(j));
			}
		}
		
		response.setHeader( "Content-Disposition", "attachment;filename=matters_data.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
		
    	return "";
    }
    
}
