package com.springboot.latestthree.service;

import com.springboot.latestthree.dto.Result;

public interface TestService {
	
	Result testMehod();
	
	Result getAsyncData(String uuid);
	
}
