package edu.wpi.mhtc.dashboard.pipeline.scheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	public static void runPipeline(String pipelineName, String pipelineDesc, String path, String filename) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(path);
		Process p = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		System.out.println("==== Talend job" + pipelineName + " shell script log ====");
		while ((line = reader.readLine()) != null)
		{
			System.out.println(line);
		}	   
	}
}