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
	private SchedulerFactory sf;
	private Scheduler sched;
	public JobScheduler() throws Exception {
    	sf = new StdSchedulerFactory();
    	sched = sf.getScheduler();		 	
    	
    	// Start the scheduler
    	sched.start();
    	
    	// Test job
    	this.createTalendJob(specificDateTimeTrigger(11,20,2014,20,34,0),"Talend 1");
	}
	
	/* Job operations */
	public void shutdown() throws SchedulerException {
		sched.shutdown(true);
	}
	
	public void deleteJob(String jobName) throws SchedulerException {
		sched.deleteJob(new JobKey(jobName));
	}
	
	/* Create job procedures */
    public void createTalendJob(Trigger trigger, String jobName) throws Exception{

    	// Create Talend Job
    	JobDetail job = JobBuilder.newJob(TalendJob.class)
    		       .withIdentity(jobName)
    		       .build();        
    	job.getJobDataMap().put("jobName", jobName);
    	
    	//Trigger the job to run on the next round minute
    	sched.scheduleJob(job, trigger);
     }
    
    /************** Trigger builder methods ************************/
    private SimpleTrigger specificDateTimeTrigger(int month, int day, int year, int hours, int minutes, int seconds) {
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
