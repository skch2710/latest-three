package com.springboot.latestthree.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.latestthree.dto.Result;
import com.springboot.latestthree.service.TestService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

//	@Autowired
//	private JdbcClient jdbcClient;
	
//	@Autowired
//	private EmailSender emailSender;
	
	@Autowired
	private AsycClass asycClass;
	
	@Autowired
	private CompletableFutureEx futureEx;

	@Autowired
	private TaskEx taskEx;
	
	@Autowired
	private CompletableFutureSaveAll futureSaveAll;

	private Map<Long, String> testReturn;
	
	@Override
	public Result testMehod() {
		log.info("starting asyncMehod ::::");
//		CompletableFuture<Result> resultFuture = new CompletableFuture<>();
		Result result = new Result();
		try {
			result.setStatusCode(HttpStatus.OK.value());
			result.setSuccessMessage("API is Triggered....");
			
//			Map<String, Object> model = new HashMap<>();
//			model.put("toMail", "skch2710@gmail.com");
//			model.put("subject", "Welcome to Employee - sathish");
//			model.put("htmlFile", "welcome.html");
//			model.put("fullName", "welcome");
//			
//			emailSender.sendEmailWelcome(model);
			
			//This is Async Method is running in Background
			
//			if (asycClass.isComplete()) {
//				if (asycClass.isTriggerd()) {
//					asycClass.methodAsync();
//					System.out.println("After Async Method trigger ::");
//					asycClass.setTriggerd(false);
//				}
//			} else {
//				result.setStatusCode(HttpStatus.OK.value());
//				result.setSuccessMessage("Getting Data");
//				result.setData(asycClass.getData());
//				asycClass.setComplete(true);
//			}
			
			String uuid = UUID.randomUUID().toString();
			result.setData("uuid : "+uuid);
			asycClass.methodAsync(uuid);
			
			log.info("Ending asyncMehod ::::");
			
		} catch (Exception e) {
			log.error("Error in asyncMehod :: " + e.getMessage());
		}
		return result;
	}

	@Override
	public Result getAsyncData(String uuid) {
		Result result = new Result();
		try {
			if(asycClass.getComplete().get(uuid) != null) {
				if(asycClass.getComplete().get(uuid)) {
					result.setStatusCode(HttpStatus.OK.value());
					result.setSuccessMessage("Getting Data");
					result.setData(asycClass.getDataMap().get(uuid));
					asycClass.getDataMap().remove(uuid);
					asycClass.getComplete().remove(uuid);
				}else {
					result.setStatusCode(HttpStatus.PROCESSING.value());
					result.setSuccessMessage("Pending");
				}
			}else {
				result.setStatusCode(HttpStatus.BAD_REQUEST.value());
				result.setSuccessMessage("BAD Request...");
			}
			System.out.println("??"+asycClass.getComplete());
			System.out.println(">>"+asycClass.getDataMap());
			
		} catch (Exception e) {
			log.error("Error in getAsyncData :: "+ e.getMessage());
		}
		return result;
	}

	@Override
	public Result testCompletableFutureMehod() {
		Result result = new Result();
		try {
			long initialValue = System.currentTimeMillis();
			System.out.println("Intialize....."+initialValue);
			result.setStatusCode(HttpStatus.PROCESSING.value());
			result.setSuccessMessage("Pending");
//			result.setData("Running in Background");

			String apiInput = "input";
	        
//			futureEx.methodCompletableFuture(apiInput);
//			taskEx.task2(apiInput);
//			String res = taskEx.task3(apiInput);
//			result.setData(res);
//			taskEx.finalTask(apiInput);
			
//			List<String> itemList = Arrays.asList("aaa","bbb","cccc","dddd");
//			List<String> itemList = new ArrayList<>();
//			for(int i = 0 ; i<=100000;i++) {
//				itemList.add("s"+i);
//			}
			
//			List<CompletableFuture<Void>> features = new ArrayList<>();
//			for (String item : itemList) {
//				futureSaveAll.testAsync(item,features);
//				Thread.sleep(3000);
//			}
//			CompletableFuture<Void> allFeatures = CompletableFuture.allOf(features.toArray(new CompletableFuture[0]));
//			allFeatures.join();
			
//			List<String> serItemList = futureSaveAll.batchInsertRecords(itemList);
			
			testReturn = futureEx.testReturnExeguter(apiInput);
			
			System.out.println("Data ...??? : "+testReturn.get(1L));
			
			result.setData(testReturn);
			
			long finalValue = System.currentTimeMillis();
			
			System.out.println("Total Time Taken :: "+(finalValue-initialValue));
			
		} catch (Exception e) {
			log.error("Error in testCompletableFutureMehod :: ,"+ e);
		}
		return result;
	}

}
