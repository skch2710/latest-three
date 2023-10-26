package com.springboot.latestthree.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/test")
@SecurityRequirement(name = "bearerAuth")
public class TestController {

	@PostMapping("/test-post-with-jwt")
	public ResponseEntity<?> postS() {
		return ResponseEntity.ok("test-post-with-jwt");
	}

	@GetMapping("/test-get-with-jwt")
	public ResponseEntity<?> getS() {
		return ResponseEntity.ok("test-get-with-jwt");
	}
	
	@GetMapping("/get-authorities")
	public ResponseEntity<?> getHello(@RequestHeader(required=false,value = HttpHeaders.AUTHORIZATION) String authorization) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
//		Set<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//				.collect(Collectors.toSet());
		System.out.println(">>USER_NAME:>>>"+authentication.getName());
		System.out.println(">>Principal>>>"+authentication.getPrincipal());
		System.out.println(">>TOKEN>>>: "+authorization);
		
		String token = authorization.substring("Bearer ".length());
		
		System.out.println(">>TOKEN>>>: "+token);
		
		return ResponseEntity.ok("HELLO GET");
		
	}
	
	@GetMapping("/test-authority")
//	@PreAuthorize("hasAnyAuthority('Super User','Admin')")
//	@PreAuthorize("hasRole('Super User')")
	//@RolesAllowed("Admin")
	public ResponseEntity<?> getTestAutority(@RequestHeader(required=false,value = "Authorization") String authentication) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		System.out.println(authentication);
		System.out.println(auth);
		
		return ResponseEntity.ok("You are Access to this API.");
	}
	
}
