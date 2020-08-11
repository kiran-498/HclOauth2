package com.example.demo.service;

public interface LoginService {

	public String checkUserNameAndPassword(String name, String password, String clientId, String clientSecret,
			String grantType);
}
