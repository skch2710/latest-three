package com.springboot.latestthree.util;

import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestClientHelper {
	
	
	public static <T> T get(String url, String auth, Class<T> responseType) {
		ResponseEntity<T> response = RestClient.create().get()
				.uri(url).header(HttpHeaders.AUTHORIZATION, auth)
				.retrieve().toEntity(responseType);
		return response.getBody();
	}
	
	public static <T> T post(String url, String auth,Object object, Class<T> responseType) {
		ResponseEntity<T> response = RestClient.create().post()
				.uri(url)
//				.header(HttpHeaders.AUTHORIZATION, auth)
				.body(object)
				.retrieve().toEntity(responseType);
		return response.getBody();
	}
	
	public static <T> T getTokens(Map<String,String> values, Class<T> responseType) {
		ResponseEntity<T> response = null;
		try {
			String encodedCredentials = new String(Base64.getEncoder().encode(values.get("clientCred").getBytes()));

			// Create the request body with username and password
			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("grant_type", values.get("grantType"));
			requestBody.add("username", values.get("userName"));
			requestBody.add("password", values.get("password"));
			
			response= RestClient.create().post()
					  .uri(values.get("url"))
					  .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
					  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
					  .body(requestBody)
					  .retrieve()
					  .toEntity(responseType);
			
		} catch (Exception e) {
			log.error("Error in getTokens...",e);
		}
		return response!=null ? response.getBody() : null;
	}

}
