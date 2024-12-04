package com.task.employee.mapper;

import com.task.employee.entity.Employee;
import com.task.employee.model.EmployeeDto;

public class Mapper {
	public static Employee dtoToEmployee(EmployeeDto employeeDto)
	{
		Employee employee = new Employee();
		employee.setId(employeeDto.getId());
		employee.setUsername(employeeDto.getUsername());

		employee.setEmail(employeeDto.getEmail());
		employee.setPassword(employeeDto.getPassword());
		employee.setRole(employeeDto.getRole());
		return employee;
	}
	
	
	public static EmployeeDto employeeToDto(Employee employee)
		{
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setId(employee.getId());
			employeeDto.setUsername(employee.getUsername());

			employeeDto.setEmail(employee.getEmail());
			return employeeDto;
		}
	

}
