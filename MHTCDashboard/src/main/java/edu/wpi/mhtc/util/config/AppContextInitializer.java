/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.config;

import java.io.IOException;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * Responsible for loading the proper profile (local, dev, prod, etc.)
 * Specifically used for the database connection
 * @author Alex Fortier
 *
 */
public class AppContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment enviro = applicationContext.getEnvironment();
		
		try {
			enviro.getPropertySources().addFirst(new ResourcePropertySource("classpath:config.properties"));
		} catch (IOException e) {
			// TODO
		}
		
	}

}
