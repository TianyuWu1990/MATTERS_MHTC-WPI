/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobScheduler {
	private static SchedulerFactory sf ;
	private static Scheduler sched;
	private static boolean isRunning = true;
	
	public static void createScheduler() throws Exception {
    	sf = new StdSchedulerFactory();
    	sched = sf.getScheduler();		 	
	}
	/**************** 
	 * Job scheduler 
	 * @throws Exception 
	 *****************/
	public static void schedule(Schedule sched) throws Exception {
		if (sched.isCronJob()) {
			JobScheduler.createTalendJob(cronTrigger(sched.getSched_date()), sched);
		} else {
			JobScheduler.createTalendJob(specificDateTimeTrigger(sched.getSched_date()), sched);
		}
	}

	/**************** Job operations ****************/
	public static void start() throws SchedulerException {
		sched.start();
	}
	public static void shutdown() throws SchedulerException {
		sched.shutdown(true);
	}
	
	public static boolean isInStandbyMode() throws SchedulerException {
		return !(isRunning);
	}
	
	public static void toggle() {
		try {
			if (isRunning) {
				sched.standby();
				System.out.println("The scheduler has been paused.");
			} else {
				sched.start();
				System.out.println("The scheduler has been restarted.");
			}
			
			isRunning = !isRunning;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteJob(String jobName) throws SchedulerException {
		sched.deleteJob(new JobKey(jobName));
	}
	
	/* Create job procedures */
    public static void createTalendJob(Trigger trigger, Schedule job_schedule) throws Exception{

    	// Create Talend Job
    	JobDetail job = JobBuilder.newJob(TalendJob.class)
    		       .withIdentity(job_schedule.getJob_name())
    		       .build();        
    	
    	job.getJobDataMap().put("jobPipelineName",  job_schedule.getSched_job());
    	//Trigger the job to run on the next round minute
    	sched.scheduleJob(job, trigger);
     }
    
    /************** Trigger builder methods ************************/
    
    /**
     * Return a trigger build for a specific date and time
     * @param date: In format 
     * @return The trigger
     */
    public static SimpleTrigger specificDateTimeTrigger(String dateStr) {
    	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

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

    /**
     * Return a trigger build for UNIX cron schedule
     * @param sched_date: The cron string
     * @return The trigger
     */
    private static Trigger cronTrigger(String sched_date) {
    	Trigger trigger = TriggerBuilder.newTrigger()
						    			.withSchedule(CronScheduleBuilder.cronSchedule(sched_date))
						    			.build();
    	return trigger;
    }
}
