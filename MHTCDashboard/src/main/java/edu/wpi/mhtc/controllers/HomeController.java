/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
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

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;
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
	static Connection conn = DBConnector.getInstance().getConn();
	
	@Autowired
    ReCaptchaImpl reCaptcha;
	
	@Autowired
	ServletContext context;
		
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
    
    /* Excel exporter 
     * Exports excel file for the chart data
     * @throws IOException */
    
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public @ResponseBody String excel_exporter(Locale locale, Model model, @RequestParam("data") String data, HttpServletResponse response) throws IOException {	
    	/***************** JSON Preprocessor ***************************/
    	JSONObject json = new JSONObject(data);
    	String title = json.getString("title");
    	boolean metricTitle = json.getBoolean("metricTitle");
		JSONArray jsonTable =  json.getJSONArray("rows");
		JSONArray jsonMetrics =  json.getJSONArray("metrics");
		
		/***************** Excel Data Preparation ***************************/
    	Workbook wb;
		wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		
	    XSSFFont boldFont= (XSSFFont) wb.createFont(); // Init Bold fonts
	    boldFont.setFontHeightInPoints((short)10);
	    boldFont.setFontName("Arial");
	    boldFont.setBold(true);
	    boldFont.setItalic(false);
	    
	    CellStyle boldStyle = wb.createCellStyle();
	    boldStyle.setFont(boldFont);
	    /***************** MISC INIT ***************************/
	    
	    
		/* For (1,1), put MHTC logo */
	    InputStream mhtc_logo = context.getResourceAsStream("/css/img/excel-logo.jpg");
	    byte[] bytes = IOUtils.toByteArray(mhtc_logo);
	    int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
	    mhtc_logo.close();
	    
	    CreationHelper helper = wb.getCreationHelper();
	    Drawing drawing = sheet.createDrawingPatriarch();
	    
	    Row logoRow = sheet.createRow(0);
		logoRow.setHeight((short) 1000);
		
	    ClientAnchor anchor = helper.createClientAnchor();
	    anchor.setAnchorType(ClientAnchor.DONT_MOVE_AND_RESIZE);
	    anchor.setCol1(0);
	    anchor.setRow1(0);
	    Picture pict = drawing.createPicture(anchor, pictureIdx);
 
		
		
		/* For (2,1), put date */
		Row dateRow = sheet.createRow(1);
		Cell dateCell = dateRow.createCell(0);
		SimpleDateFormat downloadDateFormat = new SimpleDateFormat("MMM d, yyyy hh:mm:ss aaa");
		dateCell.setCellValue("Date: "+ downloadDateFormat.format(new Date()));
		
		/* For (4,1), a title cell */
		Row titleRow = sheet.createRow(3);
		Cell titleCell = titleRow.createCell(0);
		
		// If there's only 1 metric, multiple states, set the metric font weight normal
		if (!metricTitle) { 
			titleCell.setCellValue(title);
		} else {
			RichTextString richString = new XSSFRichTextString(title);
			richString.applyFont(0, 5, boldFont);
			titleCell.setCellValue(richString);
		}
		
		titleCell.setCellStyle(boldStyle);
		
		/* We can now put the rows from the table in, the table header row should be in bold font.
		 * This will go from row 4 to row [4 + (table_length) - 1]
		 */
		for (int i = 0; i < jsonTable.length(); i++) {
			JSONArray jsonDataRow = jsonTable.getJSONArray(i);
			Row dataRow = sheet.createRow(i+4);
			for (int j = 0; j < jsonDataRow.length(); j++) {
				Cell cell = dataRow.createCell(j);
				cell.setCellValue(jsonDataRow.getString(j));
				
				if (i == 0) {
					cell.setCellStyle(boldStyle);
				}
			}
			
			if (i > 0) {
				sheet.autoSizeColumn(i);
			}
		}
		sheet.setColumnWidth(0, 3000);
	    
		/* "Original Data Sources" row: */
		int currentPtr = 4 + jsonTable.length() + 1;
		Row dsTextRow = sheet.createRow(currentPtr);
		Cell dsCell = dsTextRow.createCell(0);
		dsCell.setCellValue("Original Data Sources");
		
		dsCell.setCellStyle(boldStyle);
	    
	    /* Now, list all the metrics and its sources */
		for (int i = 0; i < jsonMetrics.length(); i++) {
			currentPtr++;
			JSONArray jsonMetricRow = jsonMetrics.getJSONArray(i);
			Row metricRow = sheet.createRow(currentPtr);
			
			Cell metricCell = metricRow.createCell(0);
			Cell metricUrlCell = metricRow.createCell(1);
			
			metricCell.setCellValue(jsonMetricRow.getString(0));
			metricUrlCell.setCellValue(jsonMetricRow.getString(1));
		}	    
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		
		// Copyrights stuff
		currentPtr = currentPtr + 2;
		Row copyrightRow = sheet.createRow(currentPtr);
		Cell copyrightCell = copyrightRow.createCell(0);
		copyrightCell.setCellValue("Generated from http://matters.mhtc.org/. Copyright 2015 Worcester Polytechnic Institute. Sponsored by the Massachusetts High Technology Council.");
		
		// Disclaimer stuff
		currentPtr = currentPtr + 2;
		Row disclaimerRow = sheet.createRow(currentPtr);
		Cell disclaimerCell = disclaimerRow.createCell(0);
		disclaimerCell.setCellValue("Please note that in any tax-related information, data or metrics are reflective of the tax policy that is generally applicable to the broadest set of taxpayers. Please note that in many instances and jurisdictions, various and differing tax regimes may be applicable. Similarly, tax-related information contained within MATTERS is not intended to be tax advice. Neither MATTERS nor the Massachusetts High Technology Council is a tax advice expert offering tax advice.");
		
		// Disclaimer stuff 2
		currentPtr = currentPtr + 2;
		Row disclaimerRow2 = sheet.createRow(currentPtr);
		Cell disclaimerCell2 = disclaimerRow2.createCell(0);
		disclaimerCell2.setCellValue("Neither MATTERS nor the Massachusetts High Technology Council makes any representations, warranties, or assurances as to the accuracy, currency or completeness of the content contain in MATTERS or any sites linked to this site.");
		
		pict.resize();
		response.setHeader( "Content-Disposition", "attachment;filename=matters_data.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
		
    	return "";
    }
    
}
