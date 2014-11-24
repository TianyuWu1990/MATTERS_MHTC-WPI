package edu.wpi.mhtc.dashboard.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import edu.wpi.mhtc.dashboard.pipeline.scheduler.JobScheduler;

public class AppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		//WebApplicationContext context = getContext();
		
		//servletContext.get
		//servletContext.addListener(new ContextLoaderListener(context));
		//ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
       // dispatcher.setLoadOnStartup(1);
        //dispatcher.addMapping("/*");

		try {
			JobScheduler.createScheduler();
			JobScheduler.start();
			System.out.println("Scheduler started");
		} catch (Exception e) {
			System.out.println("Error starting the job scheduler.");
		}
		

	}
	
	@SuppressWarnings("unused")
	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("edu.wpi.mhtc.dashboard.config");
		
		return context;
		
	}

}
