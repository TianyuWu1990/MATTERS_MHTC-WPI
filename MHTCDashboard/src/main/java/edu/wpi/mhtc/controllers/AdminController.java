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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

import edu.wpi.mhtc.dashboard.pipeline.dao.CategoryService;
import edu.wpi.mhtc.dashboard.pipeline.dao.Metric;
import edu.wpi.mhtc.dashboard.pipeline.dao.MetricService;
import edu.wpi.mhtc.dashboard.pipeline.dao.Pipeline;
import edu.wpi.mhtc.dashboard.pipeline.dao.PipelineService;
import edu.wpi.mhtc.dashboard.pipeline.dao.ScheduleService;
import edu.wpi.mhtc.dashboard.pipeline.dao.Statistic;
import edu.wpi.mhtc.dashboard.pipeline.dao.StatisticService;
import edu.wpi.mhtc.dashboard.pipeline.dao.TalendLog;
import edu.wpi.mhtc.dashboard.pipeline.dao.TalendLogService;
import edu.wpi.mhtc.dashboard.pipeline.dao.UserService;
import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.main.DataPipeline;
import edu.wpi.mhtc.dashboard.pipeline.main.MHTCException;
import edu.wpi.mhtc.dashboard.pipeline.scheduler.JobScheduler;
import edu.wpi.mhtc.dashboard.pipeline.scheduler.Schedule;
import edu.wpi.mhtc.dashboard.pipeline.scheduler.TalendJob;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.UnZip;
import edu.wpi.mhtc.dashboard.util.FileFinder;

@Controller
public class AdminController {
	
    private DateFormat fileDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    
    @Autowired private ServletContext servletContext;
    
    private CategoryService categoryService;
    private StatisticService statService;
    private UserService userService;
    private ScheduleService schedService;
    private PipelineService pipelineService;
    private MetricService metricService;
    private TalendLogService logService;
        
    @Autowired
    public AdminController(CategoryService categoryService,
			StatisticService statService, UserService userService,
			ScheduleService schedService, PipelineService pipelineService,
			MetricService metricService, TalendLogService logService) {
		this.categoryService = categoryService;
		this.statService = statService;
		this.userService = userService;
		this.schedService = schedService;
		this.pipelineService = pipelineService;
		this.metricService = metricService;
		this.logService = logService;
	}

	/********** Admin manager page **********/
    @RequestMapping(value = "admin_manager", method = RequestMethod.GET)
    public String admin_manager(Model model) {
    	
    	String title = "MATTERS: Administrator Management";
        model.addAttribute("title", title);

        return "admin_manager";
    }
    
    @RequestMapping(value = "admin/account/create", method = RequestMethod.POST, params = { "Username", "Password", "Email", "FirstName", "LastName"})
    public @ResponseBody String account_create(@RequestParam("Username") String username, @RequestParam("Password") String password, 
    		@RequestParam("Email") String email, @RequestParam("FirstName") String firstName, @RequestParam("LastName") String lastName) {
    	
    	userService.createUser(username, password, firstName, lastName, email, true);
    	
		return "Admin created successfully";
    }
    
    @RequestMapping(value = "admin/account/change-password", method = RequestMethod.POST, params = {"oldPassword", "newPassword", "confirmPassword"})
    public @ResponseBody String account_create(Principal principal, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, 
    		@RequestParam("confirmPassword") String confirmPassword) throws SQLException {
        
        switch (userService.changePassword(oldPassword, newPassword, confirmPassword, principal.getName())) {
        	case -1: return "Old password does not match";
        	case -2: return "Please reconfirm your new password.";
        	case -3: return "Cannot change the password. Please try again.";
        	default: return "Changed password successfully"; 
        }
        
    } 
    
    @RequestMapping(value = "admin/account/reset-password", method = RequestMethod.POST, params = {"resetUsername"})
    public @ResponseBody String reset_password(@RequestParam("resetUsername") String resetUsername) throws SQLException {
    	
    	String newPassword = userService.resetPassword(resetUsername);
    	
        return "The new password for " + resetUsername + " is " + newPassword;
    }
    
    /********** End authentication pages **********/
    
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Locale locale, Model model) throws Exception {
        String title = "MATTERS: Administration Center";
        
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
      
    	List<Category> categories = categoryService.getAll();
    	String title = "MATTERS: Database Explorer";
    	
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);

        return "admin_dbexplorer";
    }
    
    @RequestMapping(value = "/admin/getSubCategories", method = RequestMethod.GET)
    public @ResponseBody List<Category> getSubCategories(@RequestParam("categoryid") String categoryid) throws Exception {
    	return categoryService.getChilrden(Integer.parseInt(categoryid));
    }
    
    @RequestMapping(value = "/admin_dbexplorer/getDataByCategory", method = RequestMethod.GET)
    public @ResponseBody List<Statistic> getDataByCategory(@RequestParam("categoryid") String categoryid) throws Exception {
    	return statService.getStatsByCategory(Integer.parseInt(categoryid));
    }
        
    @RequestMapping(value ="/admin_dbexplorer/getAllMetrics", method = RequestMethod.GET)
    public @ResponseBody List<Metric> getDetailedMetrics() throws SQLException {
    	return metricService.getAll();
    }
    
    /*********************** UPLOAD ********************************/
    @RequestMapping(value = "/admin_upload", method = RequestMethod.GET)
    public String admin_upload(Locale locale, Model model) throws Exception {
      
    	List<Category> categories = categoryService.getAll();
    	Set<String> dataTypes = metricService.getMetricDataTypes();
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
    	if (parentid.isEmpty()) {
    		parentid = "-1";
    	}
    	
    	categoryService.save(categoryName, Integer.parseInt(parentid), source, url);
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
    	
    	metricService.save(metricName, metricDesc, isCalc, categoryID, datatype);

    	String referer = request.getHeader("Referer");
    	
    	// For status message
		redir.addFlashAttribute("metric_success_add", true);
		
        return "redirect:"+referer;
    }
    
    @RequestMapping(value = "/admin/metrics", method = RequestMethod.GET)
    public @ResponseBody List<Metric> getMetrics(@RequestParam("categoryid") String categoryid) throws Exception {
    	
    	return metricService.getMetricsForCategory(Integer.parseInt(categoryid));
    }    
    
    /*********************** PIPELINE ********************************/
    @RequestMapping(value = "/admin_pipeline", method = RequestMethod.GET)
    public String admin_pipeline(Locale locale, Model model) throws Exception {
    	
    	List<Category> categories = categoryService.getAll();
    	Set<String> dataTypes = metricService.getMetricDataTypes();

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
    		System.out.println("Error! Can't create folder.");
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
    	
    	// Determine if system is Windows vs Unix/Linux/Mac
    	// .bat vs .sh
    	String OS = System.getProperty("os.name").toLowerCase();
    	String pattern;
    	
    	if (OS.contains("win")) {
        	pattern = "*.bat";
    	} else {
        	pattern = "*.sh";
    	}
    	
    	// Need to find path to the file
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
    	pipelineService.save(pipelineName, pipelineDesc, scriptFilePath.toString(), script.getOriginalFilename(), user);
    	
    	redir.addFlashAttribute("pipeline_add_success", true);
    	redir.addFlashAttribute("pipelineName", pipelineName);
    	
    	return "redirect:/admin_pipeline";
    }
    
    /**
     * Returns a JSON array of every pipeline jobs.
     * Used for Scheduler and Table Pipeline Listing
     */
    @RequestMapping(value = "/admin_pipeline/getPipelineData")
    public @ResponseBody List<Pipeline> admin_get_pipeline_data(Locale locale, Model model) throws Exception 
    {
		return pipelineService.getAll();
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
		pipelineService.delete(pipelineName);
		
		return true;
    }
    
    @RequestMapping(value = "/admin/execute", method = RequestMethod.POST, params = {"pipelineName"})
    public @ResponseBody String admin_pipleline_run(Locale locale, Model model, @RequestParam("pipelineName") String pipelineName) throws IOException {
    	Pipeline p = pipelineService.get(pipelineName);
    	
    	TalendJob.runPipeline(p.getPipelineName(), p.getPath());
    	
    	return "Pipeline successfully ran!";
    }
    
    /********************** SCHEDULER *******************************/
    @RequestMapping(value = "/admin_scheduler", method = RequestMethod.GET)
    public String admin_scheduler(Locale locale, Model model) throws Exception 
    {
      
    	String title = "MATTERS: Scheduler";
    	List<Schedule> schedList = schedService.getAll();
    	
        model.addAttribute("title", title);
		model.addAttribute("schedList", schedList);
		model.addAttribute("inStandbyMode", JobScheduler.isInStandbyMode());
		
        return "admin_scheduler";
    }
    
    @RequestMapping(value = "/admin_scheduler_add", method=RequestMethod.POST, params = {"sched_job", "sched_name", "sched_description", "sched_date", "sched_cron"})
    public String admin_scheduler_add(Locale locale, Model model, @RequestParam("sched_job") String sched_job, @RequestParam("sched_name") String sched_name, 
    		@RequestParam("sched_date") String sched_date, @RequestParam("sched_description") String sched_description, @RequestParam("sched_cron") boolean sched_cron) throws Exception 
    {
		schedService.save(sched_name, sched_job, sched_description, sched_date, sched_cron);
		List<Schedule> schedList = schedService.getAll();
		
    	String title = "MATTERS: Scheduler";
		
		// TODO: Error message/ Success message
        model.addAttribute("title", title);
		model.addAttribute("success_add", true);
		model.addAttribute("sched_name", sched_name);
		model.addAttribute("schedList", schedList);
		model.addAttribute("inStandbyMode", JobScheduler.isInStandbyMode());
		
		// Schedule the job
		JobScheduler.schedule(new Schedule(sched_name, sched_job, sched_description, sched_date, sched_cron));
		
		return "admin_scheduler";
    }
    
    @RequestMapping(value = "/admin_scheduler_stop", method=RequestMethod.GET, params = {"job_name"})
    public String admin_scheduler_stop(Locale locale, Model model, @RequestParam("job_name") String job_name) throws Exception {
    	// Delete from DB
    	schedService.delete(job_name);
    	
		// Actually stop the job
		JobScheduler.deleteJob(job_name);
		
		// Get the jobs
		List<Schedule> schedList = schedService.getAll();
		
    	String title = "MATTERS: Scheduler";
		
		// TODO: Error message/ Success message
        model.addAttribute("title", title);
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
      
    	String title = "MATTERS: Reporting";
    	
        model.addAttribute("title", title);
              
        return "admin_reports";
    }
    
    @RequestMapping(value = "/admin_reports_detail", method = RequestMethod.GET)
    public String admin_reports_detail(Locale locale, Model model, @RequestParam("job") String job) throws Exception {
      
    	String title = "MATTERS: Reporting";
    	
        model.addAttribute("title", title);
        model.addAttribute("job", job);      
        
        return "admin_reports_detail";
    }
    
    @RequestMapping(value = "/post_reports", method = RequestMethod.POST)
    public @ResponseBody String admin_post_reports(Locale locale, Model model, 
    												@RequestParam("moment") String moment,
    												@RequestParam("message") String message,
    												@RequestParam("priority") int priority,
    												@RequestParam("job") String job,
    												@RequestParam("origin") String origin,
    												@RequestParam("code") int code) throws Exception {
    	// TODO: Add some security measures to prevent unauthorized users to access this RESTful service.
    	logService.save(job, code, message, origin, moment, priority);
    	return "{\"success\" : true}";
    }    
    
    @RequestMapping(value = "/admin_get_logs", method = RequestMethod.GET)
    public @ResponseBody List<TalendLog> admin_get_logs(Locale locale, Model model, @RequestParam("job") String job) throws Exception {
    	if (job.equals("")) {
    		return logService.getSummary();
    	} else {
    		return logService.get(job);
    	}
    }    
    
    @RequestMapping(value = "/admin/upload/add", method=RequestMethod.POST)
    public String uploadAddFile(RedirectAttributes redir, @RequestParam("file") MultipartFile dataFile, @RequestParam("category") String subCategoryID, 
    		@RequestParam("parentcategory") String parentCategoryID, @RequestParam(value = "overwrite", required=false) boolean overwrite) throws Exception 
    {
    	String fileName = dataFile.getOriginalFilename();
    	String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
    	
    	String newFileName = tokens[0] + "_" + fileDateFormat.format(new Date()) + "." + tokens[1];
    	
    	// In this function, the "parentcategory" parameter is the actual string name of the category
    	// whereas the "category" parameter is the ID of the subcategory
    	
    	// Need to get actual name of subcategory
    	Category subCategory = categoryService.get(Integer.parseInt(subCategoryID));
    	Category parentCategory = categoryService.get(Integer.parseInt(parentCategoryID));
    	
    	// Create directory structure
    	final String DATA_DIRECTORY = "/matters/data";
    	String parentDir = parentCategory.getName().toLowerCase().replaceAll(" ", "_");
    	String childDir = subCategory.getName().toLowerCase().replaceAll(" ", "_");
    	
    	Path dir = Paths.get(servletContext.getRealPath(""), DATA_DIRECTORY, parentDir, childDir);
    	String dataFileLocation = dir.toString() + "/" + newFileName;
    	
    	boolean createFolderSuccess = new File(dir.toString()).mkdirs();
    	
    	if (!createFolderSuccess) {
    		// TODO: Yell at the user or something. 
    	}
    	
    	// Now save file to location
    	File localFile = new File(dataFileLocation);
    	dataFile.transferTo(localFile);
    	
        // Now that the file is saved, time to run it
        DataPipeline.run(localFile, subCategoryID, overwrite);
        
        // TODO Once completed, need to add entry to database for record keeping
        // TODO Need to somehow get the metric from the spreadsheet for DB record
        
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
    	
    	e.printStackTrace();
    	
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
	    	
	    	ex.printStackTrace();
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
    	
    	ex.printStackTrace();
    	
    	return mav;
    }
}

