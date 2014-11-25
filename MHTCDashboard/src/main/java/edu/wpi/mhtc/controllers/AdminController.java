package edu.wpi.mhtc.controllers;

import java.io.File;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
import org.springframework.web.servlet.ModelAndView;

import edu.wpi.mhtc.dashboard.pipeline.db.DBConnector;
import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;
import edu.wpi.mhtc.dashboard.pipeline.db.DBSaver;
import edu.wpi.mhtc.dashboard.pipeline.main.DataPipeline;
import edu.wpi.mhtc.dashboard.pipeline.main.MHTCException;
import edu.wpi.mhtc.dashboard.pipeline.scheduler.JobScheduler;
import edu.wpi.mhtc.dashboard.pipeline.scheduler.Schedule;
import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.admin.Admin;
import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;
//import edu.wpi.mhtc.persistence.JdbcProcedure;
import edu.wpi.mhtc.service.MetricService;

@Controller
public class AdminController {

    private DateFormat fileDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    
    private MetricService service;
    private JdbcTemplate template;
    
    @Autowired
    public AdminController(MetricService service, JdbcTemplate template) {
        this.service = service;
        this.template = template;
    }
    
    /********** Authentication **********/
    @RequestMapping(value = "admin/logout", method = RequestMethod.GET)
    public String logoutPage() {
        return "logoutPage";
    }
    
    @RequestMapping(value = "admin/login", method = RequestMethod.GET)
    public String loginPage() {
        return "admin_login";
    }
    /********** End authentication pages **********/
    
    /********** Admin manager page **********/
    @RequestMapping(value = "admin/manage", method = RequestMethod.GET)
    public String manage() {
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
    
 
    @RequestMapping(value = "/admin_help", method = RequestMethod.GET)
    public String help(Locale locale, Model model) throws Exception {
        
        return "admin_help";
    }
    
    
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
    
    @RequestMapping(value = "/admin_dbexplorer/getMetrics", method = RequestMethod.GET)
    public @ResponseBody List<ArrayList<String>> getMetricData(@RequestParam("categoryid") String categoryid) throws Exception {
    	
    	List<ArrayList<String>> metricData = DBLoader.getMetricData(categoryid);
    	return metricData;
    }
    
    @RequestMapping(value = "/admin_upload", method = RequestMethod.GET)
    public String admin_upload(Locale locale, Model model) throws Exception {
      
    	Map<String, String> categories = DBLoader.getCategoryInfo();
    	String title = "MATTERS: Manual Upload";
    	
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);

        return "admin_upload";
    }
    
    @RequestMapping(value = "/admin_addCategory", method = RequestMethod.POST, params = {"parentcategory", "categoryName", "source"})
    public String admin_addCategory(Locale locale, Model model, @
    		RequestParam("parentcategory") String parentid, @RequestParam("categoryName") String categoryName, @RequestParam("source") String source) throws SQLException 
    {
    	DBSaver.insertNewCategory(categoryName, parentid, source);
    	
    	Map<String, String> categories = DBLoader.getCategoryInfo();
    	String title = "MATTERS: Manual Upload";
    	
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);

        return "admin_upload";
    }
    
    @RequestMapping(value = "/admin_pipeline", method = RequestMethod.GET)
    public String admin_pipeline(Locale locale, Model model) throws Exception {
    	Map<String, String> categories = DBLoader.getCategoryInfo();
    	String title = "MATTERS: Pipeline Manager";
    	
    	model.addAttribute("categories", categories);
        model.addAttribute("title", title);
        
        return "admin_pipeline";
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
    public @ResponseBody String uploadAddFile(@RequestParam("file") MultipartFile file, @RequestParam("category") String categoryID) throws Exception {
    	
    	System.out.println("\n\nCategory id from admin panel: " + categoryID);
    	
        String name = "Upload - " + fileDateFormat.format(new Date()) + ".xlsx";
        if (!file.isEmpty()) {
               	File localFile = new File(name);
            	file.transferTo(localFile);
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(name));
//                stream.write(bytes);
//                stream.close();
//                
//                BufferedReader br = new BufferedReader(new FileReader(name));
////                String category = br.readLine().split(",")[0];
//                br.close();
                
                DataPipeline.run(localFile, categoryID);
                
        }
        
        return "You successfully uploaded " + name + " into " + name + "-uploaded !";
        
    }
    
    
    
    /**
     * Handles all MHTCExceptions that could occur during the pipeline execution
     * @param e the exception to catch and pass to the view
     * @return the appropriate error view
     */
    @ExceptionHandler(MHTCException.class)
    public ModelAndView handleCategoryException(HttpServletRequest req, Exception e){
    	
    	ModelAndView mav = new ModelAndView();
    	
    	mav.addObject("exception", e);
    	mav.addObject("url", req.getRequestURL());
    	mav.setViewName("error");
    	
    	return mav;
    }
    
}
