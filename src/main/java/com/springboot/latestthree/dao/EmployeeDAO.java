package com.springboot.latestthree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.latestthree.model.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Long> {

	Employee findByEmailId(String emailId);
	
	Employee findByEmailIdIgnoreCase(String emailId);
	
	Employee findByEmpId(Long empId);

}
