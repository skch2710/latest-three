package com.springboot.latestthree.service.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
//@Slf4j
public class TaskEx {
	
	//This is Using Without Threads 
//	public void finalTask (String inp) throws InterruptedException {
//		String m1 = method1(inp);
//		String m2 = method2(inp);
//		String m3 = method3(inp);
//		
//		combineMethods(m1, m2, m3);
//		System.out.println("After Last Method......");
//	}
	
	
	public String task3(String apiInput) {

		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			try {
				return method1(apiInput);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				return method2(apiInput);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			try {
				return method3(apiInput);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		    CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
		    String res = "";
		    try {
		        combinedFuture.get(); // Wait for all CompletableFuture to complete
				res = combineMethods(future1.get(), future2.get(), future3.get());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    return res;
		}

	
	//This is Using With Threads
	public void finalTask(String inp) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(3);

//		List<Callable<String>> tasks = new ArrayList<>();
//		tasks.add(() -> method1(inp));
//		tasks.add(() -> method2(inp));
//		tasks.add(() -> method3(inp));
		
		List<Callable<String>> tasks = List.of(
			    () -> method1(inp),
			    () -> method2(inp),
			    () -> method3(inp)
			);
		
		List<Future<String>> results = executorService.invokeAll(tasks);
		executorService.shutdown();
//		List<String> resultsList = new ArrayList<>();
//        for (Future<String> result : results) {
//            resultsList.add(result.get());
//        }
		
		List<String> resultsList = results.stream()
		        .map(future -> {
		            try {
		                return future.get();
		            } catch (InterruptedException | ExecutionException e) {
		                e.printStackTrace(); // Handle exceptions appropriately
		                return null; // or throw a custom exception
		            }
		        })
		        .collect(Collectors.toList());

        
        combineMethods(resultsList.get(0), resultsList.get(1), resultsList.get(2));
        
		System.out.println("After Last Method......");
	}
	
	
	public void task2(String inp) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		List<Callable<String>> tasks = List.of(
		        () -> method1(inp),
		        () -> method2(inp),
		        () -> method3(inp)
		);

		List<String> resultsList = executorService.invokeAll(tasks).stream()
		        .map(future -> CompletableFuture.completedFuture(future).thenApply(t -> {
					try {
						return t.get();
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
					return null;
				}))
		        .map(CompletableFuture::join)
		        .collect(Collectors.toList());

		combineMethods(resultsList.get(0), resultsList.get(1), resultsList.get(2));
		
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
		public String combineMethods(String result1, String result2, String result3) {
		System.out.println(result1 + " + " + result2 + " + " + result3);
		return result1 + " + " + result2 + " + " + result3;
	}

}
