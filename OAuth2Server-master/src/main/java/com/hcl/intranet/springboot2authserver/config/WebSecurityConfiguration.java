package com.hcl.intranet.springboot2authserver.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	 @Autowired
	    private AuthenticationManager authenticationManager;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
		.antMatchers("/oauth/token").permitAll()
		.antMatchers("/oauth/authorize")
			.authenticated()
			.and().formLogin()
		.and().requestMatchers()
        	.antMatchers("/login","/oauth/authorize");	
		/*http.requestMatchers()
           .antMatchers("/**")
           .and()
           .authorizeRequests()
           .anyRequest()
           .authenticated()
           .and()
           .formLogin()
           .permitAll();*/
		   
	/*	http.csrf().disable().exceptionHandling()
		.authenticationEntryPoint(
				(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
		.and().authorizeRequests().antMatchers("/**").authenticated().and().httpBasic();*/
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.parentAuthenticationManager(authenticationManager)
        .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
