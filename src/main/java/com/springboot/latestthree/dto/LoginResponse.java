package com.springboot.latestthree.dto;

import com.springboot.latestthree.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	
	private Boolean isOtpEnable;
	private String otp;
	private Employee employee;
	private JwtDTO jwtDTO;
	
}
