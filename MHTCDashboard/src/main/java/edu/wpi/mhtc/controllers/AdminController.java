/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;
import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;
import edu.wpi.mhtc.dashboard.pipeline.db.DBSaver;
import edu.wpi.mhtc.dashboard.pipeline.main.DataPipeline;
import edu.wpi.mhtc.dashboard.pipeline.main.MHTCException;
import edu.wpi.mhtc.dashboard.pipeline.scheduler.JobScheduler;
import edu.wpi.mhtc.dashboard.pipeline.scheduler.Schedule;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.UnZip;
import edu.wpi.mhtc.dashboard.util.FileFinder;
import edu.wpi.mhtc.dashboard.util.Logger;
import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.admin.Admin;
//import edu.wpi.mhtc.persistence.JdbcProcedure;
import edu.wpi.mhtc.service.MetricService;

@Controller
public class AdminController {
	
    private DateFormat fileDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    
    private MetricService service;
    private JdbcTemplate template;
    @Autowired ServletContext servletContext=null;
    
    @Autowired
    public AdminController(MetricService service, JdbcTemplate template) {
        this.service = service;
        this.template = template;
    }

    /********** Admin manager page **********/
    @RequestMapping(value = "admin_manager", method = RequestMethod.GET)
    public String admin_manager() {
        return "admin_manager";
    }
    
    @RequestMapping(value = "admin/account/create", method = RequestMethod.POST, params = { "Username", "Password", "Email", "FirstName", "LastName"})
    public @ResponseBody String account_create(@RequestParam("Username") String username, @RequestParam("Password") String password, @RequestParam("Email") String email, @RequestParam("FirstName") String firstName, @RequestParam("LastName") String lastName) {
        Admin newAdmin = new Admin(username, password, email, firstName, lastName);
        
        try {
			newAdmin.insertToDB();
			return "Added";
		} catch (SQLException e) {
				
			return e.toString();
		}
    }
    
    @RequestMapping(value = "admin/account/change-password", method = RequestMethod.POST, params = {"oldPassword", "newPassword", "confirmPassword"})
    public @ResponseBody String account_create(Principal principal, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) throws SQLException {
        Admin current = new Admin(principal.getName());
        
        switch (current.changePassword(oldPassword, newPassword, confirmPassword)) {
        	case -1: return "Old password does not match";
        	case -2: return "Please reconfirm your new password.";
        	case -3: return "Cannot change the password. Please try again.";
        	default: return "Changed password successfully"; 
        }
    } 
    
    @RequestMapping(value = "admin/account/reset-password", method = RequestMethod.POST, params = {"resetUsername"})
    public @ResponseBody String reset_password(@RequestParam("resetUsername") String resetUsername) throws SQLException {
    	Admin resetAdmin = new Admin(resetUsername);
    	String newPassword = resetAdmin.resetPassword();
        return "The new password for " + resetUsername + " is " + newPassword;
    }        
    /********** End authentication pages **********/
    
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Locale locale, Model model) throws Exception {
        Map<String, String> categories = DBLoader.getCategoryInfo();
        String title = "MATTERS: Administration Center";
        
        model.addAttribute("categories", categories);
        model.addAttribute("title", title);
        
        return "admin_tool";
    }
    /*************************** HELP ***********************************/  
    @RequestMapping(value = "/admin_help", method = RequestMethod.GET)
    public String help(Locale locale, Model model) throws Exception {
    	
        return "admin_help";
    }
    
    /*********************** DB EXPLORER ********************************/
    @RequestMapping(value = "/admin_dbexplorer", method = RequestMethod.GET)
    public String admin_db(Locale locale, Model model) throws Exception {
      
    	Map<String, String> categories = DBLoader.getCategoryInfo();
    	String title = "MATTERS: Database Explorer";
    	
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);

        return "admin_dbexplorer";
    }
    
    @RequestMapping(value = "/getSubCategories", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getSubCategories(@RequestParam("categoryid") String categoryid) throws Exception {
    	
    	Map<String, String> subCategories = DBLoader.getSubCategories(categoryid);
    	return subCategories;
    }
    
    @RequestMapping(value = "/admin_dbexplorer/getMetricData", method = RequestMethod.GET)
    public @ResponseBody List<ArrayList<String>> getMetricData(@RequestParam("categoryid") String categoryid) throws Exception {
    	
    	List<ArrayList<String>> metricData = DBLoader.getMetricData(categoryid);
    	return metricData;
    }
    
    @RequestMapping(value ="/admin_dbexplorer/getDetailedMetricData", method = RequestMethod.GET)
    public @ResponseBody List<ArrayList<String>> getDetailedMetricData() throws SQLException {
    	
    	List<ArrayList<String>> metricData = DBLoader.getDetailedMetricData();
    	
		return metricData;
    }
    
    /*********************** UPLOAD ********************************/
    @RequestMapping(value = "/admin_upload", method = RequestMethod.GET)
    public String admin_upload(Locale locale, Model model) throws Exception {
      
    	Map<String, String> categories = DBLoader.getCategoryInfo();
    	Set<String> dataTypes = DBLoader.getMetricDataTypes();
    	String title = "MATTERS: Manual Upload";
    	
    	model.addAttribute("datatypes", dataTypes);
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);

        return "admin_upload";
    }
    
    @RequestMapping(value = "/admin_addCategory", method = RequestMethod.POST, params = {"parentcategory", "categoryName", "source"})
    public String admin_addCategory(Locale locale, Model model, RedirectAttributes redir, HttpServletRequest request,
    		@RequestParam("parentcategory") String parentid, @RequestParam("categoryName") String categoryName, 
    		@RequestParam("source") String source, @RequestParam("url") String url) throws SQLException 
    {
    	DBSaver.insertNewCategory(categoryName, parentid, source, url);
    	String referer = request.getHeader("Referer");
    	
    	// For status message
		redir.addFlashAttribute("category_success_add", true);

        return "redirect:"+referer;
    }
    
    @RequestMapping(value = "/admin_addMetric", method = RequestMethod.POST, params = {"parentcategory", "subcategory", "metricName", "metricDesc", "datatype", "isCalculated"})
    public String admin_addMetric(Locale locale, RedirectAttributes redir, HttpServletRequest request,
    		@RequestParam("subcategory") String subCategory, @RequestParam("metricName") String metricName, @RequestParam("metricDesc") String metricDesc, 
    		@RequestParam("datatype") String datatype, @RequestParam("isCalculated") String isCalculated, @RequestParam("parentcategory") String parentCategory) throws SQLException
    {
    	int categoryID;
    	
    	// Check if Metric is going under parent or sub
    	if (subCategory.isEmpty()) {
    		categoryID = Integer.parseInt(parentCategory);
    	} else {
    		categoryID = Integer.parseInt(subCategory);
    	}

    	boolean isCalc = Boolean.parseBoolean(isCalculated);
    	DBSaver.insertNewMetric(metricName, metricDesc, isCalc, categoryID, datatype);

    	String referer = request.getHeader("Referer");
    	
    	// For status message
		redir.addFlashAttribute("metric_success_add", true);
		
        return "redirect:"+referer;
    }
    
    @RequestMapping(value = "/admin/metrics", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getMetrics(@RequestParam("categoryid") String categoryid) throws Exception {
    	
    	Map<String, String> metricData = DBLoader.getMetricInfo(categoryid);
    	return metricData;
    }    
    
    /*********************** PIPELINE ********************************/
    @RequestMapping(value = "/admin_pipeline", method = RequestMethod.GET)
    public String admin_pipeline(Locale locale, Model model) throws Exception {
    	
    	Map<String, String> categories = DBLoader.getCategoryInfo();
    	Set<String> dataTypes = DBLoader.getMetricDataTypes();

    	String title = "MATTERS: Pipeline Manager";
    	
    	model.addAttribute("datatypes", dataTypes);
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);
        
        return "admin_pipeline";
    }
    
    @RequestMapping(value = "/admin_addPipeline", method = RequestMethod.POST)
    public String admin_addPipeline(Locale locale, Model model, RedirectAttributes redir, Principal principal,
    								@RequestParam("parentcategory") String parentCategory,
    								@RequestParam("subcategory") String subCategory, @RequestParam("script") MultipartFile script,
    								@RequestParam("pipelineName") String pipelineName, @RequestParam("pipelineDesc") String pipelineDesc) throws SQLException 
    {
    	// Create directory structure
    	final String DATA_DIRECTORY = "/matters/bin";
    	String parentDir = parentCategory.toLowerCase().replaceAll(" ", "_");
    	String childDir = subCategory.toLowerCase().replaceAll(" ", "_");
    	
    	Path dir = Paths.get(servletContext.getRealPath(""), DATA_DIRECTORY, parentDir, childDir);
    	String zipFile = dir.toString() + "/" + script.getOriginalFilename();
    	
    	boolean createFolderSuccess = new File(dir.toString()).mkdirs();
    	
    	if (!createFolderSuccess) {
    		// TODO: Yell at the user or something. 
    	}
    	
    	// Now save file to location
    	File dest = new File(zipFile);
    	
    	try {
			script.transferTo(dest);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Now unzip file to server in proper directory
    	if (!script.isEmpty()) {
    		UnZip unZipper = new UnZip();
    		unZipper.unZipIt(zipFile, dir.toString());
    	}
    	
    	// Need to find path to the .sh file
    	String pattern = "*.sh";
		FileFinder finder = new FileFinder(pattern);
		try {
			Files.walkFileTree(dir, finder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get the path to the file and set the file as executable
		Path scriptFilePath = finder.done();
		scriptFilePath.toFile().setExecutable(true);
		
		// Figure out what user submitted the pipeline
		String user = principal.getName();
		
    	// Now let's add the entry to the database if nothing has failed yet    	
    	DBSaver.insertPipeline(pipelineName, pipelineDesc, scriptFilePath.toString(), script.getOriginalFilename(), user);
    	
    	redir.addFlashAttribute("pipeline_add_success", true);
    	redir.addFlashAttribute("pipelineName", pipelineName);
    	
    	return "redirect:/admin_pipeline";
    }
    
    /**
     * Returns a JSON array of every pipeline jobs.
     * Used for Scheduler and Table Pipeline Listing
     */
    @RequestMapping(value = "/admin_pipeline/getPipelineData", method = RequestMethod.GET)
    public @ResponseBody List<HashMap<String,String>> admin_get_pipeline_data(Locale locale, Model model) throws Exception {
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		Connection conn = DBConnector.getInstance().getConn();
		
		String sql = "SELECT * FROM mhtc_sch.pipelines";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		ResultSet rs = pstatement.executeQuery();
		
		while (rs.next()) {
			HashMap<String,String> row = new HashMap<String,String>();
			String pipelineName = rs.getString("pipelinename");
			String pipelineDesc = rs.getString("pipelinedesc");	
			String filename = rs.getString("filename");
			String user = rs.getString("uploadedby");
			
			// Need to convert Date to String
			Date dateAdded = rs.getTimestamp("dateadded");
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String date = df.format(dateAdded);
			
			row.put("pipelinename",pipelineName);
			row.put("pipelinedesc", pipelineDesc);
			row.put("filename", filename);
			row.put("dateadded", date);
			row.put("user", user);
			
			data.add(row);
		}
		
		return data;
    }
    
    /**
     * Deletes pipeline from database with that name
     * TODO Does not wipe data from server
     * @param locale
     * @param model
     * @param pipelineName
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/admin_pipeline/delete", method = RequestMethod.POST, params = {"pipelineName"})
    public @ResponseBody boolean admin_pipeline_delete(Locale locale, Model model, @RequestParam("pipelineName") String pipelineName) throws SQLException
    {
		Connection conn = DBConnector.getInstance().getConn();

		String sql = "DELETE FROM mhtc_sch.pipelines WHERE pipelinename = ?";
		PreparedStatement pstatement = conn.prepareStatement(sql);
		
		pstatement.setString(1, pipelineName);
		
		// Will return false since there is no result, but just want to make sure it executed properly
		return !pstatement.execute();
		
    }
    
    /********************** SCHEDULER *******************************/
    @RequestMapping(value = "/admin_scheduler", method = RequestMethod.GET)
    public String admin_scheduler(Locale locale, Model model) throws Exception {
      
    	Map<String, String> categories = DBLoader.getCategoryInfo();
    	String title = "MATTERS: Scheduler";
    	List<Schedule> schedList = DBLoader.getSchedules();
    	
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);
		model.addAttribute("schedList", schedList);
		model.addAttribute("inStandbyMode", JobScheduler.isInStandbyMode());
		
        return "admin_scheduler";
    }
    
    @RequestMapping(value = "/admin_scheduler_add", method=RequestMethod.POST, params = {"sched_job", "sched_name", "sched_description", "sched_date", "sched_cron"})
    public String admin_scheduler_add(Locale locale, Model model, @RequestParam("sched_job") String sched_job, @RequestParam("sched_name") String sched_name, @RequestParam("sched_date") String sched_date, @RequestParam("sched_description") String sched_description, @RequestParam("sched_cron") boolean sched_cron) throws Exception {
		Schedule newSched = new Schedule(sched_name, sched_job, sched_description, sched_date, sched_cron);
		newSched.insertToDB();
		List<Schedule> schedList = DBLoader.getSchedules();
		
		// TODO: Error message/ Success message
		model.addAttribute("success_add", true);
		model.addAttribute("sched_name", sched_name);
		model.addAttribute("schedList", schedList);
		model.addAttribute("inStandbyMode", JobScheduler.isInStandbyMode());
		
		// Schedule the job
		JobScheduler.schedule(newSched);
		
		return "admin_scheduler";
    }
    
    @RequestMapping(value = "/admin_scheduler_stop", method=RequestMethod.GET, params = {"job_name"})
    public String admin_scheduler_stop(Locale locale, Model model, @RequestParam("job_name") String job_name) throws Exception {
		String sql = "DELETE FROM mhtc_sch.schedules WHERE job_name = ?";
		Connection conn = DBConnector.getInstance().getConn();
		PreparedStatement pstatement = conn.prepareStatement(sql);

		pstatement.setString(1, job_name);
		boolean success_stop = pstatement.execute();   
		
		// Actually stop the job
		JobScheduler.deleteJob(job_name);
		
		// Get the jobs
		List<Schedule> schedList = DBLoader.getSchedules();
		
		// TODO: Error message/ Success message
		model.addAttribute("success_stop", true);
		model.addAttribute("inStandbyMode", JobScheduler.isInStandbyMode());
		model.addAttribute("schedList", schedList);
		
        return "admin_scheduler";
    }
    
    @RequestMapping(value = "/admin_scheduler_toggle", method=RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody String admin_scheduler_toggle() throws SchedulerException {
    	  JobScheduler.toggle();  	
    	  return "Success";
    }
    /********************** REPORTS *******************************/
    @RequestMapping(value = "/admin_reports", method = RequestMethod.GET)
    public String admin_reports(Locale locale, Model model) throws Exception {
      
    	Map<String, String> categories = DBLoader.getCategoryInfo();
    	String title = "MATTERS: Reporting";
    	
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);
              
        return "admin_reports";
    }
    
    @RequestMapping(value = "/post_reports", method = RequestMethod.POST)
    public @ResponseBody String admin_post_reports(Locale locale, Model model, @RequestParam("data") String data) throws Exception {
    	// TODO: Add some security measures to prevent unauthorized users to access this RESTful service.
    	Logger.jsonTalendLog(data);
    	return "{\"success\" : true}";
    }    
    
    @RequestMapping(value = "/admin_get_logs", method = RequestMethod.GET)
    public @ResponseBody List<HashMap<String,String>> admin_get_logs(Locale locale, Model model) throws Exception {
    	return Logger.retriveLog();
    }    
    
    @RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
    public @ResponseBody List<Metric> categories() {
        //List<TreeViewNode> returnList = new LinkedList<TreeViewNode>();
        //returnList.add(service.getCategoriesAsTree());
        return null;
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/metrics/table")
    public String metricTable(Model model, @PathVariable("categoryid") int categoryId) {
        
        //model.addAttribute("jv_metrics", service.getMetricsForCategory(categoryId));
        
        return "admin_metrics_table";
    }
    
    @RequestMapping(value = "/admin/categories/new", method=RequestMethod.POST, params = { "parentid", "name", "source"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addCategory(@RequestParam("parentid") int parentId, @RequestParam("name") String name, @RequestParam("source") String source) {
        
        //service.storeCategory(name, parentId, source);
        
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/update", method=RequestMethod.POST, params = { "name", "visible", "source"})
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCategory(Model model, @PathVariable("categoryid") int categoryId, @RequestParam("visible") boolean visible, @RequestParam("name") String name, @RequestParam("source") String source) {

        //service.updateCategory(categoryId, name, visible, source);
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/metrics/new", method=RequestMethod.POST, params = { "name", "iscalculated", "type"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addMetric(@PathVariable("categoryid") int categoryId, @RequestParam("name") String name, @RequestParam("iscalculated") boolean isCalculated, @RequestParam("type") String type) {
        
        //service.storeMetric(categoryId, name, isCalculated, type);
        
    }
    
    @RequestMapping(value = "/admin/metrics/{metricid}/update", method=RequestMethod.POST, params = { "name", "visible", "iscalculated", "type"})
    @ResponseStatus(value = HttpStatus.OK)
    public void updateMetric(@PathVariable("metricid") int metricId, @RequestParam("name") String name, @RequestParam("visible") boolean visible, @RequestParam("iscalculated") boolean isCalculated, @RequestParam("type") String type) {
        
        //service.updateMetric(metricId, name, visible, isCalculated, type);
        
    }
    
    @RequestMapping(value = "/admin/upload/add", method=RequestMethod.POST)
    public String uploadAddFile(RedirectAttributes redir, @RequestParam("file") MultipartFile dataFile, @RequestParam("category") String subCategoryID, 
    		@RequestParam("parentcategory") String parentCategoryID) throws Exception 
    {
    	// In this function, the "parentcategory" parameter is the actual string name of the category
    	// whereas the "category" parameter is the ID of the subcategory
    	
    	// Need to get actual name of subcategory
    	String subCategory = DBLoader.getCategoryNameFromID(Integer.parseInt(subCategoryID));
    	String parentCategory = DBLoader.getCategoryNameFromID(Integer.parseInt(parentCategoryID));
    	
    	// Create directory structure
    	final String DATA_DIRECTORY = "/matters/data";
    	String parentDir = parentCategory.toLowerCase().replaceAll(" ", "_");
    	String childDir = subCategory.toLowerCase().replaceAll(" ", "_");
    	
    	Path dir = Paths.get(servletContext.getRealPath(""), DATA_DIRECTORY, parentDir, childDir);
    	String dataFileLocation = dir.toString() + "/" + dataFile.getOriginalFilename();
    	
    	boolean createFolderSuccess = new File(dir.toString()).mkdirs();
    	
    	if (!createFolderSuccess) {
    		// TODO: Yell at the user or something. 
    	}
    	
    	// Now save file to location
    	File localFile = new File(dataFileLocation);
    	dataFile.transferTo(localFile);
    	
        // Now that the file is saved, time to run it
        DataPipeline.run(localFile, subCategoryID);
        
        // Once completed, need to add entry to database for record keeping
        // TODO Need to somehow get the metric from the spreadsheet for DB record
        DBSaver.insertManualUpload(parentCategory, subCategory, "metric", dataFile.getOriginalFilename(), dir.toString());
        
        redir.addFlashAttribute("upload_file_success", true);
        redir.addFlashAttribute("filename", dataFile.getOriginalFilename());
        
        return "redirect:/admin_upload";
        
    }
    
    @RequestMapping(value = "/errorTest", method = RequestMethod.GET)
    public ModelAndView getErrorPage() throws Exception {
    	
    	MHTCException test = new CategoryException("You threw an MHTC Exception!");
    	test.setSolution("And here is the solution you set!");
    	
    	throw test;
    }
    
    /*********************** ERROR HANDLING **********************************/
    
    /**
     * Handles all Exceptions that could occur in the system
     * @param e the exception to catch and pass to the view
     * @return the appropriate error view
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e){
    	
    	String title = "MATTERS: Generic Exception Error";

    	ModelAndView mav = new ModelAndView("error/generic");
    	
    	mav.addObject("title", title);
    	mav.addObject("exception", e);
    	
    	return mav;
    }
    
    /**
     * Handles all SQL Exceptions that could occur in the system
     */
    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException(SQLException ex, HttpServletRequest request) {
    	
    	String title = "MATTERS: SQL Exception Error";
    	ModelAndView mav = new ModelAndView();		
    	
    	// This is a duplicate key, let's just inform the user
    	if (ex.getSQLState().equals("23505")) {
        	String referer = request.getHeader("Referer");
        	
        	RedirectView redirectView = new RedirectView(referer);
        	
        	mav.setView(redirectView);
        	
        	FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        	if (outputFlashMap != null) {
        		outputFlashMap.put("database_duplicate_key", true);
        	}
        	
    	} else {

	    	mav.setViewName("error/sql");
	    	mav.addObject("title", title);
	    	mav.addObject("sqlException", ex);
	    	
    	}

		return mav;

    }
    
    /**
     * Handles all specific MHTC Exceptions, which have suggested solutions
     */
    @ExceptionHandler(MHTCException.class)
    public ModelAndView handleMHTCException(MHTCException ex) {
    	
    	String title = "MATTERS: MHTC Exception Error";
    	
    	ModelAndView mav = new ModelAndView("error/mhtc");
    	
    	mav.addObject("title", title);
    	mav.addObject("mhtcEx", ex);
    	
    	return mav;
    }
}

