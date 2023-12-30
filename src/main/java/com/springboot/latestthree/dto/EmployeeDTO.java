package com.springboot.latestthree.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.latestthree.model.EmployeeRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties
public class EmployeeDTO {

	private Long empId;

	private String emailId;

	private String firstName;

	private String lastName;

	private EmployeeRole employeeRole;

	private Long createdById;

	private Date createdDate;

	private Long modifiedById;

	private Date modifiedDate;

}
