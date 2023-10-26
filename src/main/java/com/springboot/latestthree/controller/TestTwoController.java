package com.springboot.latestthree.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.latestthree.dto.JwtDTO;
import com.springboot.latestthree.util.RestClientHelper;

@RestController
@RequestMapping("/test2")
public class TestTwoController {
	
	@GetMapping("/get-test-without-jwt")
	public ResponseEntity<?> getHi(){
		return ResponseEntity.ok("get-test-without-jwt");
	}

	@PostMapping("/post-test-without-jwt")
	public ResponseEntity<?> getS() {
		return ResponseEntity.ok("post-test-without-jwt");
	}
	
	@GetMapping("/get-jwt-token")
	public ResponseEntity<?> getToken() {
		Map<String,String> values = new HashMap<>();
		values.put("url", "http://localhost:8060/oauth2/token");
		values.put("clientId", "sathish_ch");
		values.put("clientSecret", "password");
		values.put("userName", "skch2710@gmail.com");
		values.put("password", "S@thi$+b27");
		values.put("grantType", "password");
		
		JwtDTO dto = RestClientHelper.getTokens(values,JwtDTO.class);
		
		return ResponseEntity.ok(dto);
	}
}
