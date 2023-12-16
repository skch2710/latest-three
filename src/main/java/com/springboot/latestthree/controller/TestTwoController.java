package com.springboot.latestthree.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.latestthree.dto.JwtDTO;
import com.springboot.latestthree.dto.LoginRequest;
import com.springboot.latestthree.dto.Result;
import com.springboot.latestthree.dto.StudentDTO;
import com.springboot.latestthree.service.TestService;
import com.springboot.latestthree.util.EmailSender;
import com.springboot.latestthree.util.RestClientHelper;

@RestController
@RequestMapping("/test2")
public class TestTwoController {
	
	@Autowired
	private TestService  testService;
	
	@Autowired
	private EmailSender emailSender;
	
	@GetMapping("/test-async")
	public ResponseEntity<?> testAsync(){
		Result result = testService.testMehod();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/test-file")
	public ResponseEntity<?> testFile(){
		// Specify the local path where you want to create the text file
        String path = "D:\\Files\\example.csv";

        // Content to be written to the file
        String content = "Sample,Test\n1,2";

        try {
            // Create a FileWriter with the specified path
            FileWriter fileWriter = new FileWriter(path);

            // Wrap the FileWriter in a BufferedWriter for efficient writing
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the content to the file
            bufferedWriter.write(content);

            // Close the resources to ensure the content is flushed and the file is properly closed
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Text file created successfully at: " + path);
        } catch (IOException e) {
            System.err.println("An error occurred while creating the text file.");
            e.printStackTrace();
        }
		return ResponseEntity.ok("File Created");
	}
	
	@GetMapping("/test-async-get-data/{uuid}")
	public ResponseEntity<?> testAsyncgetData(@PathVariable("uuid") String uuid){
		Result result = testService.getAsyncData(uuid);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get-test-without-jwt")
	public ResponseEntity<?> getHi(){
		return ResponseEntity.ok("get-test-without-jwt");
	}

	@PostMapping("/post-test-without-jwt")
	public ResponseEntity<?> getS() {
		return ResponseEntity.ok("post-test-without-jwt");
	}
	
	@Value("${app.url}")
	private String url;
	
	@Value("${app.auth-cred}")
	private String clientCred;
	
	@PostMapping("/get-jwt-token")
	public ResponseEntity<?> getToken(@RequestBody LoginRequest request) {
		Map<String,String> values = new HashMap<>();
		values.put("url", url);
		values.put("clientCred", clientCred);
		values.put("userName", request.getEmailId());
		values.put("pwd", request.getPassword());
		values.put("grantType", "custom_pwd");
		
		JwtDTO dto = RestClientHelper.getTokens(values,JwtDTO.class);
		
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/get-jwt-refresh-token")
	public ResponseEntity<?> getRefreshToken(@RequestBody JwtDTO dto) {
		Map<String,String> values = new HashMap<>();
		values.put("url", url);
		values.put("clientCred", clientCred);
		values.put("grantType", "refresh_token");
		values.put("refresh_token", dto.getRefresh_token());
		
		dto = RestClientHelper.getRefreshToken(values,JwtDTO.class);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/send-mail")
	public ResponseEntity<?> sendMail() {
		Map<String,Object> values = new HashMap<>();
		values.put("toMail", "skch2710@gmail.com");
		values.put("htmlFile", "table-template.ftlh");
		values.put("subject", "Table Data with Header Color...");
		
		List<StudentDTO> studentList = new ArrayList<>();
		StudentDTO std1 = new StudentDTO("AAAA","Mail@gmail.com");
		StudentDTO std2 = new StudentDTO("BBB","Mail@gmail.com");
		StudentDTO std3 = new StudentDTO("CCCC","Mail@gmail.com");
		studentList.add(std1);
		studentList.add(std2);
		studentList.add(std3);
		
		values.put("studentList", studentList);
		
		emailSender.sendEmailWelcome(values);
		
		return ResponseEntity.ok("Email Sended....");
	}
}
