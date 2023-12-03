package com.springboot.latestthree.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

//@EnableAsync ( This is already in Main Class )
@Service
@Data
@Slf4j
public class AsycClass {
	
//	private boolean isComplete = true;
	
	private Map<String, Boolean> complete = new HashMap<>();
	
	private Map<String, String> dataMap = new HashMap<>();
	
//	private boolean isTriggerd = true;
	
//	private ByteArrayOutputStream data;
	private String data = "";
	
	@Async
	public void methodAsync(String uuid) {
		try {
			Thread.sleep(80000);
//			isComplete = false;
//			isTriggerd = true;
//			data = Utility.createExcel();
			data = "Data is Completed.";
			dataMap.put(uuid, data);
			complete.put(uuid, true);
			System.out.println(">>>>>>>> Inside Asyc ::");
		} catch (Exception e) {
			log.error("Error in methodAsync :: " + e.getMessage());
		}
	}

}
