package com.hcl.intranet.springboot2authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.intranet.springboot2authserver.entity.Users;
import com.hcl.intranet.springboot2authserver.service.CustomUserDetailsService;

@RestController
@RequestMapping("contr")
public class RestControllerEx {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@RequestMapping("/home")
	public void homeService() {
		
		System.out.println("home service");
	}
	
	@PostMapping("/save")
	public void saveUser(@RequestBody Users users) {
		customUserDetailsService.save(users);
		
	}
}
