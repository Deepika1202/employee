package com.task.employee.controllers;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.employee.entity.Employee;
import com.task.employee.model.EmployeeDto;
import com.task.employee.services.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired 
	private EmployeeService employeeService;

	private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeController.class);

	//TO ADD EMPLOYEE
	@PostMapping
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(description = "Create Employee", summary = "This is a endpoint for saving the employee",
	                               responses = { @ApiResponse(description = "Success", responseCode = "200")})
	public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto)
	{
		logger.info("Creating new Employee : {}",employeeDto);
		EmployeeDto savedUser = employeeService.createEmployee(employeeDto);
		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
	}
	
	//TO GET THE EMPLOYEE BY ID 
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@Operation(description = "Get Employee by Id", summary = "This is a endpoint for getting the employee with id",
	   responses = { @ApiResponse(description = "Success", responseCode = "200")})
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") Long id)
	{
		logger.info("Fetching employee with Id : {}",id);
	   EmployeeDto employee = employeeService.getEmployee(id);
	   
	   return new ResponseEntity<>(employee , HttpStatus.OK);
	}
	
	//TO GET ALL THE EMPLOYEES
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(description = "Get All Employees", summary = "This is a endpoint for getting all the employees")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees()
	{
		logger.info("Fetching All Employees");
		List<EmployeeDto> employeeDto= employeeService.getAllEmployees();
		 return new ResponseEntity<List<EmployeeDto>>(employeeDto, HttpStatus.OK);
	}
	
	//TO DELETE THE EMPLOYEE BY ID
	@DeleteMapping("/deletebyid/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(description = "Delete Employee by Id", summary = "This is a endpoint for deleting employee with id")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id")  Long id)
	{
		logger.info("Deleting employee with Id : {}",id);
		employeeService.deleteEmployeeById(id);
	 return ResponseEntity.ok("EMPLOYEE SUCCESSFULLY DELETED");
	}
	
	//TO EDIT THE EMPLOYEE BY ID 
	@PutMapping("/editbyid/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(description = "Edit Employee By Id", summary = "This is a endpoint for updating the employee with id",
			   responses = { @ApiResponse(description = "Success", responseCode = "200")})
	public ResponseEntity<EmployeeDto> toeditemployee(@Valid @PathVariable("id")Long employeeId ,@RequestBody Employee employee)
	{
		logger.info("Updating employee data with Id : {}",employeeId);
	 EmployeeDto editedEmployee = employeeService.toeditemployee(employeeId , employee);
		
		return ResponseEntity.ok(editedEmployee);
	}
	
//	@DeleteMapping("/deleteall")
//	public ResponseEntity<Void> deleteAll()
//	{
//		employeeService.deleteAll();
//		return ResponseEntity.noContent().build();
//	}

}
