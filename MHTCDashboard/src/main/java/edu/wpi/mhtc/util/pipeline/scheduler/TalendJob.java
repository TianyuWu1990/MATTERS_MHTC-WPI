/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TalendJob implements Job  {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Process job parameters
		JobDataMap parameters = context.getJobDetail().getJobDataMap();
		String jobPipelineName = parameters.get("jobPipelineName").toString();
		String jobPath = parameters.get("jobPath").toString();
		
		try {
			runPipeline(jobPipelineName, jobPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void runPipeline(String pipelineName, String path) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(path);
		Process p = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		System.out.println("==== Talend job" + pipelineName + " shell script log ====");
		
		while ((line = reader.readLine()) != null)
		{
			System.out.println(line);
		}
		
		reader.close();
	}
}