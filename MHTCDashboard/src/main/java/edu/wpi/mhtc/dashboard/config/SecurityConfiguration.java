/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("wpi").password("mhtc").roles("ADMIN");
		DevelopmentConfiguration cfg = new DevelopmentConfiguration();
		
		auth.jdbcAuthentication().dataSource(cfg.dataSource())
		.usersByUsernameQuery(
			"select \"UserName\" as username, \"PasswordHash\" as password, 'true' as enabled from mhtc_sch.users where \"UserName\"=?")
		.authoritiesByUsernameQuery(
			"select \"UserName\" as username, \"Authorities\" as authorities from mhtc_sch.user_roles where \"UserName\"=?")
		.passwordEncoder(new Md5PasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().
		authorizeRequests()
			.antMatchers("/admin**").access("hasRole('ADMIN')")
			.antMatchers("/admin/**").access("hasRole('ADMIN')")
			.and()
		.logout()
			.logoutUrl("/logout/")
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
			.and()
		.formLogin()
			.defaultSuccessUrl("/", true)
			.loginPage("/login")
			.permitAll();
	}
}