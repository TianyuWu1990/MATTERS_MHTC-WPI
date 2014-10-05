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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("wpi").password("mhtc").roles("ADMIN");
		LocalConfiguration cfg = new LocalConfiguration();
		
		auth.jdbcAuthentication().dataSource(cfg.dataSource())
		.usersByUsernameQuery(
			"select \"UserName\" as username, \"PasswordHash\" as password, 'true' as enabled from mhtc_sch.admins where \"UserName\"=?")
		.authoritiesByUsernameQuery(
			"select \"UserName\" as username, 'ROLE_ADMIN' as authorities from mhtc_sch.admins where \"UserName\"=?")
		.passwordEncoder(new Md5PasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.and()
		.logout()
			.logoutUrl("/admin/logout")
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
			.and()
		.formLogin()
			.defaultSuccessUrl("/admin")
			.loginPage("/admin/login")
			.permitAll();
	}
}