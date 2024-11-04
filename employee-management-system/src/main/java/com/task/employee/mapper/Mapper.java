package com.task.employee.mapper;

import com.task.employee.entity.Employee;
import com.task.employee.model.EmployeeDto;

public class Mapper {
	public static Employee dtoToEmployee(EmployeeDto employeeDto)
	{
		Employee employee = new Employee();
		employee.setId(employeeDto.getId());
		employee.setFirstname(employeeDto.getFirstname());
		employee.setLastname(employeeDto.getLastname());
		employee.setEmail(employeeDto.getEmail());
		return employee;
	}
	
	
	public static EmployeeDto employeeToDto(Employee employee)
		{
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setId(employee.getId());
			employeeDto.setFirstname(employee.getFirstname());
			employeeDto.setLastname(employee.getLastname());
			employeeDto.setEmail(employee.getEmail());
			return employeeDto;
		}
	

}
