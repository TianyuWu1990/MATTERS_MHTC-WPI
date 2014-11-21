package edu.wpi.mhtc.dashboard.pipeline.scheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.util.Date;

@PersistJobDataAfterExecution 
public class TalendJob implements Job {
   public void execute(JobExecutionContext context) throws JobExecutionException{
	   // Process job parameters
	   JobDataMap parameters = context.getJobDetail().getJobDataMap();
	   String jobName = parameters.get("jobName").toString();
	   
	   // Run the job
       System.out.println("Talend Job "  + jobName + " completed at:"  + new Date());
   }
}