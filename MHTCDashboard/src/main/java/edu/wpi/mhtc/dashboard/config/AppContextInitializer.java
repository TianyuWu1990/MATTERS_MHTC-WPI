package edu.wpi.mhtc.dashboard.config;

import java.io.IOException;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

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
