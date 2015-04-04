package edu.wpi.mhtc.util.pipeline.scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.SchedulerException;

public class QuartzServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Quartz Initialized.");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		try {
			JobScheduler.shutdown();
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			System.out.println("Quartz failed to shutdown");
		} catch (SchedulerException ex) {
			System.out.println("Quartz failed to shutdown");
		}
	}
}