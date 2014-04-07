package edu.wpi.mhtc.dashboard.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fergiggles.giggledust.dust.DustViewResolver;

/**
 * Provides configuration that is global to all profiles for the web dashboard.
 * This configuration class is the entry configuration class that is specified
 * for the servlet in the web.xml. This configuration specifies the other
 * configuration files via the import annotation and these will only be loaded
 * if the proper profiles are in effect.
 * 
 * @author Stokes
 * 
 */
@EnableWebMvc
@ComponentScan("edu.wpi.mhtc")
@Configuration
@Import({ DevelopmentConfiguration.class, LocalConfiguration.class,
		ProductionConfiguration.class })
public class GlobalConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}
	
	@Bean
	public PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
		props.setLocation(new ClassPathResource("config.properties"));
		
		return props;
	}

	@Bean
	public DustViewResolver viewResolver() {
		DustViewResolver resolver = new DustViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".dust");
		return resolver;
	}

	@Bean
	@Autowired
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		return template;
	}

}
