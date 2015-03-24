/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.scheduler;

public class SchedulerMain {

	public static void main(String[] args) throws Exception {
		JobScheduler.createScheduler();
		JobScheduler.start();
	}

}
