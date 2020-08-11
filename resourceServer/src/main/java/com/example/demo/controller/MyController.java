package com.example.demo.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.example.demo.service.LoginService;

@RestController
public class MyController {

@Autowired
private LoginService LoginService;
	
	
	@RequestMapping(value="/hello",method=RequestMethod.GET)
    public String helloSecured() {

        return "Hello - Secured";
    }

	@RequestMapping(value="/home",method=RequestMethod.POST)
    public String home(Model model,HttpServletRequest req) {
		return LoginService.checkUserNameAndPassword(req.getParameter("username"), req.getParameter("password")
				, req.getParameter("clientId"), req.getParameter("clientSecret"), req.getParameter("grantType"));
		
    }
	
	
	
}