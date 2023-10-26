package com.springboot.latestthree.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentMail {
	
	private String sName;
	private List<StudentDTO> students;

}
