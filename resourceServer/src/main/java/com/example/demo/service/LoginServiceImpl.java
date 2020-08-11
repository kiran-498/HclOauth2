package com.example.demo.service;

import java.util.Arrays;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Users;
import com.example.demo.repository.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository;
	// @Autowired
	// private PasswordEncoder passwordEncoder;

	@Override
	public String checkUserNameAndPassword(String name, String password, String clientId, String clientSecret,
			String grantType) {

		try {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			System.out.println("password: " + passwordEncoder.encode(password));
			Optional<Users> user = loginRepository.findByUsername(name);

			if (!user.isPresent()) {
				throw new Exception("InvalidUser");

			}
			String DbPassword = user.get().getPassword();
			if (user.get().getPassword().startsWith("{")) {
				DbPassword = DbPassword.substring(8);
			}
			if (!BCrypt.checkpw(password, DbPassword)) {
				throw new Exception("InvalidUser");
			}
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return this.getOauthToken(name, password, clientId, clientSecret, grantType);

	}

	public String getOauthToken(String name, String password, String clientId, String clientSecret, String grantType) {

		// According OAuth documentation we need to send the client id and secret key in
		// the header for authentication
		String credentials = clientId+":"+clientSecret;//"USER_CLIENT_APP:passwo";
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", name);
		map.add("password", password);
		map.add("grant_type", grantType);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		RestTemplate restTemplate = new RestTemplate();
		String access_token_url = "http://localhost:8080/oauth/token";
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(access_token_url, request, String.class);

			System.out.println("Access Token Response ---------" + response.getBody());
			return response.getBody();
		} catch (Exception ex) {
			return "client details invalid";
		}

	}

}
