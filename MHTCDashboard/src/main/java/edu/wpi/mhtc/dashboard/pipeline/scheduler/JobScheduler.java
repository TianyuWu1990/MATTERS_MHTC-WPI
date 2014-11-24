package edu.wpi.mhtc.dashboard.pipeline.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobScheduler {
	private static SchedulerFactory sf ;
	private static Scheduler sched;
	
	public static void createScheduler() throws Exception {
    	sf = new StdSchedulerFactory();
    	sched = sf.getScheduler();		 	
	}
	/**************** Job scheduler 
	 * @throws Exception ****************/
	public static void schedule(Schedule sched) throws Exception {
		JobScheduler.createTalendJob(specificDateTimeTrigger(sched.getSched_date()), sched.getJob_name());
	}
	
	/**************** Job operations ****************/
	public static void start() throws SchedulerException {
		sched.start();
	}
	
	public static void shutdown() throws SchedulerException {
		sched.shutdown(true);
	}
	
	public static void deleteJob(String jobName) throws SchedulerException {
		sched.deleteJob(new JobKey(jobName));
	}
	
	/* Create job procedures */
    public static void createTalendJob(Trigger trigger, String jobName) throws Exception{

    	// Create Talend Job
    	JobDetail job = JobBuilder.newJob(TalendJob.class)
    		       .withIdentity(jobName)
    		       .build();        
    	job.getJobDataMap().put("jobName", jobName);
    	
    	//Trigger the job to run on the next round minute
    	sched.scheduleJob(job, trigger);
     }
    
    /************** Trigger builder methods ************************/
    
    /**
     * Return a trigger build for a specific date and time
     * @param date: In format 
     * @return
     */
    public static SimpleTrigger specificDateTimeTrigger(String dateStr) {
    	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

    	try {
     
    		Date date = formatter.parse(dateStr);
    		System.out.println("New one-time trigger created: " + date.toString());
    		
        	SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger() 
        			.startAt(date)
        			.build();
        	
        	return trigger;
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	
		return null;
    }


}
