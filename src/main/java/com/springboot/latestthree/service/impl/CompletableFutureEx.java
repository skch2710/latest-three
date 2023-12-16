package com.springboot.latestthree.service.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompletableFutureEx {

	public void methodCompletableFuture(String apiInput) {

		try {
			CompletableFuture<String> result1 = CompletableFuture.supplyAsync(() -> {
				try {
					return method1(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture<String> result2 = CompletableFuture.supplyAsync(() -> {
				try {
					return method2(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture<String> result3 = CompletableFuture.supplyAsync(() -> {
				try {
					return method3(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture.runAsync(() -> {
	            // Combine the results and perform any additional processing if needed
	            combineMethods(result1.join(), result2.join(), result3.join());
	            System.out.println("Final combined result: ");
	        });
			
//			CompletableFuture<String> finalResult =CompletableFuture.allOf(result1, result2, result3)
//					.thenApplyAsync(ignored -> combineMethods(result1.join(), result2.join(), result3.join()));
		
		} catch (Exception e) {
			log.error("Error in methodCompletableFuture,"+e);
		}

	}

	// Independent method 1
	public String method1(String input1) throws InterruptedException {
		System.out.println("started ... m1--->");
		Thread.sleep(8000);
		System.out.println("m1--->");
		return "Result from method 1" + input1;
	}

	// Independent method 2
	public String method2(String input2) throws InterruptedException {
		System.out.println("started ... m2--->");
		Thread.sleep(5000);
		System.out.println("m2--->");
		return "Result from method 2" + input2;
	}

	// Independent method 3
	public String method3(String input3) throws InterruptedException {
		System.out.println("started ... m3--->");
		Thread.sleep(6000);
		System.out.println("m3--->");
		return "Result from method 3" + input3;
	}

	// Dependent method combining results of method1, method2, and method3
	public void combineMethods(String result1, String result2, String result3) {
		System.out.println(result1 + " + " + result2 + " + " + result3);
//		return result1 + " + " + result2 + " + " + result3;
	}
	
//	public String combineMethods(String result1, String result2, String result3) {
//		System.out.println(result1 + " + " + result2 + " + " + result3);
//		return result1 + " + " + result2 + " + " + result3;
//	}

}
