package com.springboot.latestthree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	
	private Boolean isOtpEnable;
	private String otp;
	private EmployeeDTO employee;
	private JwtDTO jwtDTO;
	
}
