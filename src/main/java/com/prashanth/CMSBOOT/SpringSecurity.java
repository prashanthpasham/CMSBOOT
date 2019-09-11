package com.prashanth.CMSBOOT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.prashanth.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser(
		 * "prashanth") .password(passwordEncoder.encode("welcome")).roles("USER");
		 */
		auth.authenticationProvider(daoAuthentication());
	}
	/*
	 * @Bean public UserDetailsService userDetailsService1() throws Exception { //
	 * ensure the passwords are encoded properly UserBuilder users =
	 * User.withDefaultPasswordEncoder(); InMemoryUserDetailsManager manager = new
	 * InMemoryUserDetailsManager();
	 * manager.createUser(users.username("user").password("password").roles("USER").
	 * build());
	 * manager.createUser(users.username("admin").password("password").roles("USER",
	 * "ADMIN").build()); return manager; }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/secure/**").authenticated()
		.and().httpBasic();
		http.authorizeRequests().antMatchers("/login/**").permitAll();
		http.csrf().disable();

	}

	@Bean
	public DaoAuthenticationProvider daoAuthentication() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		try {
			dao.setUserDetailsService(userDetailsService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao.setPasswordEncoder(getPasswordEncoder());
		return dao;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
