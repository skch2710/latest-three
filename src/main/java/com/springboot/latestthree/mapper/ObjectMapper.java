package com.springboot.latestthree.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.springboot.latestthree.dto.EmployeeDTO;
import com.springboot.latestthree.model.Employee;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ObjectMapper {

	ObjectMapper INSTANCE = Mappers.getMapper(ObjectMapper.class);
	
//	default String mapName(Employee employee) {
//		return employee.getFirstName() != null ? "$" + employee.getFirstName() : null;
//	}
	
//	default String mapName(String name) {
//		return name != null ? "$" + name : null;
//	}
	
//	@Mapping()
	EmployeeDTO fromEmployeeModel(Employee employee);
	List<EmployeeDTO> fromEmployeeModel(List<Employee> employee);
	
}
