package com.springboot.latestthree.service;

import com.springboot.latestthree.dto.LoginRequest;
import com.springboot.latestthree.dto.Result;

public interface LoginService {

	Result login(LoginRequest request);

	Result verifyOTP(LoginRequest request);
	
}
