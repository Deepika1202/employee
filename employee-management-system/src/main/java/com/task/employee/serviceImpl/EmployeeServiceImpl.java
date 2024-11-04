package com.task.employee.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.task.employee.entity.Employee;
import com.task.employee.exceptions.ResourceNotFoundException;
import com.task.employee.mapper.Mapper;
import com.task.employee.model.EmployeeDto;
import com.task.employee.repository.EmployeeRepo;
import com.task.employee.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	/// TO ADD THE EMPLOYEE
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		// TODO Auto-generated method stub
		Employee employee = Mapper.dtoToEmployee(employeeDto);
		Employee savedEmployee = this.employeeRepo.save(employee);
		return Mapper.employeeToDto(savedEmployee);

	}

	/// TO GET THE EMPLOYEE BY ID
	@Override
	public EmployeeDto getEmployee(Long employeeId) {
		// TODO Auto-generated method stub
		Optional<Employee> employee = employeeRepo.findById(employeeId);
		return employee.map(Mapper::employeeToDto).orElseThrow(
				() -> new ResourceNotFoundException("Employee does not exists with this id : " + employeeId));
	}

	/// TO GET ALL THE EMPLOYEES
	@Override
	public List<EmployeeDto> getAllEmployees() {
		// TODO Auto-generated method stub
		List<Employee> employee = employeeRepo.findAll();
		return employee.stream().map(Mapper::employeeToDto).collect(Collectors.toList());

	}

	/// TO DELETE EMPLOYEE BY ID
	@Override
	public void deleteEmployeeById(Long employeeId) {
		// TODO Auto-generated method stub
		Optional<Employee> employee = employeeRepo.findById(employeeId);
		if (employee.isPresent()) {
			employeeRepo.deleteById(employeeId);
		} else {
			throw new ResourceNotFoundException("Employee does not exists with this id : " + employeeId);
		}

	}

	/// TO EDIT THE EMPLOYEE DATA BY ID
	@Override
	public EmployeeDto toeditemployee(Long employeeId, Employee employee) {
		// TODO Auto-generated method stub
		Optional<Employee> foundEmployee = employeeRepo.findById(employeeId);
		if (foundEmployee.isPresent()) {
			Employee gotEmployee = foundEmployee.get();
			gotEmployee.setFirstname(employee.getFirstname());
			gotEmployee.setLastname(employee.getLastname());
			gotEmployee.setEmail(employee.getEmail());
			employeeRepo.save(gotEmployee);
			return Mapper.employeeToDto(gotEmployee);
		}

		else {
			throw new ResourceNotFoundException("Employee does not exists with this id : " + employeeId);
		}
	}

	
//
//	@Override
//	public void deleteAll() {
//		// TODO Auto-generated method stub
//		employeeRepo.deleteAll();
//
//	}

}
