/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;
import edu.wpi.mhtc.helpers.Mailer;
import edu.wpi.mhtc.model.Data.DataSeries;
import edu.wpi.mhtc.model.state.PeerStates;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.StateService;
import edu.wpi.mhtc.service.StatsService;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private StatsService statsService;
	private StateService stateService;
	static Connection conn = DBConnector.getInstance().getConn();
	
	@Autowired
    ReCaptchaImpl reCaptcha;
	
	@Autowired
	public HomeController(StatsService statsService, StateService stateService)
	{
		this.statsService = statsService;
		this.stateService = stateService;
	}
	
	/**
	 * Returns the site landing page.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String landingPage(Locale locale, Model model)
	{
	    return "landingPage";
	}
	
	/**
	 * Returns the state profiles page.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String stateProfiles(Locale locale, Model model)
	{
	    return "stateProfiles";
	}
	
	/**
	 * Returns the about page.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about(Locale locale, Model model)
	{
	    return "about";
	}
	
	/**
	 * Returns the about page.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/methodology", method = RequestMethod.GET)
	public String methodology(Locale locale, Model model)
	{
	    return "methodology";
	}
	
	/**
	 * Returns the about page.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/howto", method = RequestMethod.GET)
	public String howto(Locale locale, Model model)
	{
	    return "howto";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/explore", method = RequestMethod.GET)
	public String home(Locale locale, Model model)
	{
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	      
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Home called");
		
		String maId = "21";
		
		List<DataSeries> massNational = statsService.getStateBinData(maId, 21);
		List<DataSeries> massTalent = statsService.getStateBinData(maId, 20);
		List<DataSeries> massCost = statsService.getStateBinData(maId, 37);
		List<DataSeries> massEconomy = statsService.getStateBinData(maId, 29);
		List<State> peers = stateService.getAllPeers();
		List<State> allstates= stateService.getAllStates(); 
		
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
 
        if(reCaptchaResponse.isValid()){
	    	// First, randomize the token
	    	Random rand = new Random();
	    	int randomNum = rand.nextInt(100) + 1;
	    	String unencryptedTokenString = email + randomNum;
	    	
	    	// SQL Query execution
			String sql = "UPDATE mhtc_sch.users SET \"Token\" = MD5(?) WHERE \"Email\" = ?";
			PreparedStatement pstatement = conn.prepareStatement(sql);
			pstatement.setString(1, unencryptedTokenString); // set parameter 1 (FIRST_NAME)
			pstatement.setString(2, email);
			pstatement.execute();
			
			if (pstatement.getUpdateCount() == 1) {
				model.addAttribute("completed", true);
				
				// Send the email for that user
		    	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
		    
		       	Mailer mm = (Mailer) context.getBean("mailMail");
		           mm.sendResetPasswordMail(email, unencryptedTokenString);
		    
			} else {
				model.addAttribute("completed", false);
			}
			
			pstatement.close();	
	    	
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
	    	// Query the token string in the database
	    	String sql = "SELECT * FROM mhtc_sch.users WHERE \"Token\" = ?";
			PreparedStatement pstatement = conn.prepareStatement(sql);
			pstatement.setString(1, token);
			ResultSet rs = pstatement.executeQuery();
			
			if (!rs.next()) {
				model.addAttribute("error", true);
			} else {
				model.addAttribute("error", false);
				model.addAttribute("email", rs.getString("Email"));
				model.addAttribute("token", token);
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
        	/* Reset password and clear the token*/
    		String sql = "UPDATE mhtc_sch.users SET \"PasswordHash\" = MD5(?), \"Token\" = '' WHERE \"Token\" = ?";
    		PreparedStatement pstatement = conn.prepareStatement(sql);
    		pstatement.setString(1, password); // set parameter 1 (FIRST_NAME)
    		pstatement.setString(2, token);
    		pstatement.execute();
    		
    		if (pstatement.getUpdateCount() == 1) {
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
    public String register_submit(Locale locale, Model model, @RequestParam("UserName") String username, @RequestParam("Email") String email, @RequestParam("Password") String password, @RequestParam("FirstName") String firstName, @RequestParam("LastName") String lastName) throws SQLException {
    	// First, create an entry in the user table.
		try {
			String sql = "INSERT INTO mhtc_sch.users VALUES (nextval('mhtc_sch.user_id_seq'), ?, ?, ?, ?, md5(?), '', true, 2);";
	    	sql += " INSERT INTO mhtc_sch.user_roles VALUES(?, 'USER');";
			PreparedStatement pstatement = conn.prepareStatement(sql);
			
			pstatement.setString(1, username); // set parameter 1 (FIRST_NAME)
			pstatement.setString(2, email);
			pstatement.setString(3, firstName);
			pstatement.setString(4, lastName);
			pstatement.setString(5, password); // set parameter 1 (FIRST_NAME)
			pstatement.setString(6, username);
			
			pstatement.execute();
			
			model.addAttribute("error", false);
		} catch (SQLException e) {
			model.addAttribute("error", true);
		}
		
        return "register_page_submit";
    } 
    
    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedbackPage(Locale locale, Model model)
    {
    	return "feedback";
    }
    
    /********** Feedback Page 
     * @throws SQLException **********/
    @RequestMapping(value = "/feedback_post", method = RequestMethod.POST)
    public String feedback_post(Locale locale, Model model, @RequestParam("name") String name,
    														@RequestParam("affiliation") String affiliation,
    														@RequestParam("email") String email,
    														@RequestParam("subject") String subject,
    														@RequestParam("comments") String comments, 
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

        	// Send the email
        	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        	String subjectText = "Feedback: " + subject; 
        	
        	String bodyText = "affiliation: " + affiliation + " \nname: " + name + "\nemail: " + email + "\ncomments:\n\n" + comments;
		    
	       	Mailer mm = (Mailer) context.getBean("mailMail");
	        mm.sendFeedbackEmail(subjectText, bodyText);
	        
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
