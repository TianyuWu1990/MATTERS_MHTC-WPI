package edu.wpi.mhtc.dashboard.config;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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
		ProductionConfiguration.class, SecurityConfiguration.class })
public class GlobalConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
		registry.addResourceHandler("/css/**").addResourceLocations(
				"/css/");
		registry.addResourceHandler("/js/**").addResourceLocations(
				"/js/");
		registry.addResourceHandler("/fonts/**").addResourceLocations(
				"/fonts/");
		registry.addResourceHandler("/font-awesome-4.1.0/**").addResourceLocations(
				"/font-awesome-4.1.0/");
		registry.addResourceHandler("/less/**").addResourceLocations(
				"/less/");
	}
	
	@Bean
	public PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
		props.setLocation(new ClassPathResource("config.properties"));
		
		return props;
	}
	@Bean
	public InternalResourceViewResolver viewResolver() {
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setOrder(0);
		return resolver;
	}

	@Bean
	@Autowired
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		return template;
	}
	
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigElement element = new MultipartConfigElement("uploads", 1048576, 1048576, 1048576);
        return element;
    }
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver mr = new CommonsMultipartResolver();
	    return mr;
	}

}
