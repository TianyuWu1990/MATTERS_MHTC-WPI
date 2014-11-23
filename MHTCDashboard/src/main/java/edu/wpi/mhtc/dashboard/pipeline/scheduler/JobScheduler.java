package edu.wpi.mhtc.dashboard.pipeline.scheduler;

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
    	
    	// Test job
    	//JobScheduler.createTalendJob(specificDateTimeTrigger(11,23,2014,13,31,30),"Talend 1");
	}
	
	/* Job operations */
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
    public static SimpleTrigger specificDateTimeTrigger(int month, int day, int year, int hours, int minutes, int seconds) {
    	Calendar cal = new java.util.GregorianCalendar(year, month-1, day);
    	cal.set(Calendar.HOUR, hours);
    	cal.set(Calendar.MINUTE, minutes);
    	cal.set(Calendar.SECOND, seconds);
    	cal.set(Calendar.MILLISECOND, 0);
    	
    	System.out.println("New one-time trigger created: " + cal.getTime().toString());

    	SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger() 
    			.startAt(cal.getTime())
    			.build();
    	
    	return trigger;
    	
    }


}
