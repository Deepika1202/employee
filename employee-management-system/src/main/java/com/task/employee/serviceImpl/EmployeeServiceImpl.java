package com.task.employee.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/// TO ADD THE EMPLOYEE
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		// TODO Auto-generated method stub
		logger.info("Saving new Employee : {}",employeeDto);
		Employee employee = Mapper.dtoToEmployee(employeeDto);
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		logger.info("Password Is Encoded");
		Optional<Employee> existEmployee = employeeRepo.findByEmail(employee.getEmail());
		if(existEmployee.isPresent())
		{
			logger.error("Error Saving Employee : {}",employeeDto.getEmail());
			throw new EntityExistsException("Employee Credentials already exists!!!!");
		}
		else {
			Employee savedEmployee = this.employeeRepo.save(employee);
			logger.info("Employee saved successfully");
			return Mapper.employeeToDto(savedEmployee);
		}
	}

	/// TO GET THE EMPLOYEE BY ID
	@Override
	public EmployeeDto getEmployee(Long employeeId) {
		// TODO Auto-generated method stub
		logger.info("Fetching Employee with Id : {}",employeeId);
		Optional<Employee> employee = employeeRepo.findById(employeeId);
		return employee.map(Mapper::employeeToDto).orElseThrow(
				 () ->{
					 String errorMessage = "Employee does not exists with this id : ";
					 logger.error("Error While Fetching Employee : {}",errorMessage);
						 return new ResourceNotFoundException(errorMessage + employeeId);
				 });
    }

	/// TO GET ALL THE EMPLOYEES
	@Override
	public List<EmployeeDto> getAllEmployees() {
		// TODO Auto-generated method stub
		logger.info("Fetching All Employees");
		List<Employee> employee = employeeRepo.findAll();
		return employee.stream().map(Mapper::employeeToDto).collect(Collectors.toList());

	}

	/// TO DELETE EMPLOYEE BY ID
	@Override
	public void deleteEmployeeById(Long employeeId) {
		// TODO Auto-generated method stub
		logger.info("Deleting Employee with Id : {}",employeeId);
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
			gotEmployee.setUsername(employee.getUsername());
			gotEmployee.setEmail(employee.getEmail());
			gotEmployee.setPassword(employee.getPassword());
			gotEmployee.setRole(employee.getRole());
			gotEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
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
