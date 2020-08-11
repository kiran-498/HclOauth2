package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entity.ClientDetails;

@Controller
public class LogInController {

	/*@GetMapping("/login")
	public String login(Model model,HttpServletRequest req,@RequestParam(name = "clientId") String clientId, @RequestParam("clientSecret") String clientSecret) {  
model.addAttribute("username", "");  
model.addAttribute("password", "");  
model.addAttribute("clientId", clientId); 
model.addAttribute("clientSecret", clientSecret);
model.addAttribute("grantType", "");
return "login";
		//return "login";
	} */
	@GetMapping("/login")
	public String login(Model model,HttpServletRequest req,@ModelAttribute("SpringWeb") ClientDetails  json)  {  
model.addAttribute("username", "dsfsdfsd");  
model.addAttribute("password", "");  
model.addAttribute("clientId", json.getClientId()); 
model.addAttribute("clientSecret", json.getClientSecret());
model.addAttribute("grantType", json.getGrantType());
return "login"; 
		//return "login";
	}
}
