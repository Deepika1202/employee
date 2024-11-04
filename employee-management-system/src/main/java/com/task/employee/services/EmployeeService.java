package com.task.employee.services;

import java.util.List;

import com.task.employee.entity.Employee;
import com.task.employee.model.EmployeeDto;

public interface EmployeeService {
	
	///METHOD TO ADD THE EMPLOYEES 
	EmployeeDto createEmployee(EmployeeDto employeeDto);
	
	///METHOD TO GET EMPLOYEE BY ID
	EmployeeDto getEmployee(Long employeeId);
	
	///METHOD TO GET ALL THE EMPLOYEES
	List<EmployeeDto> getAllEmployees();
	
	///TO DELETE THE EMPLOYEE BY ID 
	void deleteEmployeeById(Long employeeId);
	
	///TO EDIT EMPLOYEE DATA BY ID 
	EmployeeDto toeditemployee(Long employeeId , Employee employee);
	
     
	
	//void deleteAll(); 
}
