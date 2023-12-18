package com.springboot.latestthree.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CompletableFutureSaveAll {

	public void testAsync(String inp, List<CompletableFuture<Void>> features) {
		System.out.println("Started method...." + inp);
		CompletableFuture<Void> feature = CompletableFuture.runAsync(() -> {
			try {
				testSave(inp);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		features.add(feature);
	}

	// If you call this method it will run asynchronously
	public CompletableFuture<Void> testAsync(String inp) {
		System.out.println("Started method...." + inp);
		return CompletableFuture.runAsync(() -> {
			try {
				testSave(inp);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void testSave(String inp) throws InterruptedException {
		Thread.sleep(8000);
		System.out.println(">>>>Test..." + inp);
	}

	/* =================== */

	public List<String> testSaveRet(List<String> data) throws InterruptedException {
		Thread.sleep(9000);
		System.out.println(">>>>Test..." + data);
		return data;
	}

	public CompletableFuture<List<String>> testAsync2(List<String> data) {
		System.out.println("Started method...." + data);
		CompletableFuture<List<String>> feature = CompletableFuture.supplyAsync(() -> {
			try {
				return testSaveRet(data);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		return feature;
	}

	public List<String> batchInsertRecords(List<String> records) {
		List<String> itemsList = new ArrayList<>();
		try {
			List<CompletableFuture<List<String>>> features = new ArrayList<>();
			int batchSize = 20; // Set the desired batch size
			for (int i = 0; i < records.size(); i += batchSize) {
				int endIndex = Math.min(i + batchSize, records.size());
				List<String> batch = records.subList(i, endIndex);
				features.add(testAsync2(batch));
			}
			CompletableFuture<Void> allFeatures = CompletableFuture.allOf(features.toArray(new CompletableFuture[0]));
			allFeatures.join();
			System.out.println(">>>>>>>" + features.size());
			itemsList = features.stream().map(CompletableFuture::join)
					.flatMap(List::stream)
					.collect(Collectors.toList());
//			for (CompletableFuture<List<String>> feature : features) {
//				itemsList.addAll(feature.get());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemsList;
	}
}
