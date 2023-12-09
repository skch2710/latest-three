package com.springboot.latestthree.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.springboot.latestthree.dto.JwtDTO;
import com.springboot.latestthree.dto.LoginRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
	
	@Value("${app.url}")
	private String url;
	
	@Value("${app.auth-cred}")
	private String clientCred;
	
	public JwtDTO getToken(LoginRequest request) {
		JwtDTO dto = null;
		try {
			Map<String,String> values = new HashMap<>();
			values.put("url", url);
			values.put("clientCred", clientCred);
			values.put("userName", request.getEmailId());
			values.put("password", request.getPassword());
			values.put("grantType", "password");
			
			dto = RestClientHelper.getTokens(values,JwtDTO.class);
		}catch(Exception e) {
			log.error("Error in getToken method...::",e);
		}
		return dto;
	}

}
