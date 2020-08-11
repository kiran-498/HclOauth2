package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


@Configuration


@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyResourceServerConfiguration   extends ResourceServerConfigurerAdapter
{
	
	
	/*@Autowired
	private RemoteTokenServices tokenServices;*/
	
	
	@Override 
    public void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().antMatchers("/**").fullyAuthenticated();
		 http
	        .authorizeRequests()
	          .antMatchers("/", "/home", "/login","/postlogin").permitAll() // (3)
	          .anyRequest().authenticated() // (4)
	          .and()
	       .formLogin() // (5)
	         .loginPage("/login") // (5)
	         .permitAll()
	         .and()
	      .logout() // (6)
	        .permitAll()
	        .and()
	      .httpBasic();
	
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
         resources.resourceId("USER_ADMIN_RESOURCE");
    }
	
	
	/*@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		
	
		resources.tokenServices(tokenServices);
		
		//resources.tokenStore(tokenStore());
		///resources.resourceId(resourceIds).tokenStore(tokenStore());
		
	}*/
	
	

	
	
	
	
	
	

}
