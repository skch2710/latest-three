package com.springboot.latestthree.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//		@Bean
//		SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//			http
//				.securityMatcher("/api/v1/**")
//				.authorizeHttpRequests(authorize -> authorize
//					.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//					.anyRequest().authenticated())
//				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
//				.csrf(Customizer.withDefaults());
//			return http.build();
//		}
	
	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	String issuerUri;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.securityMatcher("/api/v1/**")
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri))))
//				.addFilter(null)
				.csrf(Customizer.withDefaults())
				.build();
	}

}
