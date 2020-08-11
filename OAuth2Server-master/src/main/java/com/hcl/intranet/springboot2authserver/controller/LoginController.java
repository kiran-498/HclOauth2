package com.hcl.intranet.springboot2authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@Autowired
	private TokenStore tokenStore;
	
	

	
	
	 //@GetMapping
	    public String hello(OAuth2Authentication authentication) {
	     
		 System.out.println("hellow world......");
		 tokenStore.getAccessToken(authentication);
		 //tokenStore.tokenStore
		  return "lkjfgjldfl";
	    }
}
