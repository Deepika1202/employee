package com.task.employee.controllertests;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.task.employee.controllers.EmployeeController;
import com.task.employee.entity.Employee;
import com.task.employee.model.EmployeeDto;
import com.task.employee.repository.EmployeeRepo;
import com.task.employee.services.EmployeeService;

@WebMvcTest(EmployeeController.class)

public class ControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EmployeeService employeeService;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp()
	{
	        
	}
	
	public static EmployeeDto employeeToDto(Employee employee)
	{
		 if (employee == null) {
	            throw new IllegalArgumentException("Employee cannot be null");
	        }
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setFirstname(employee.getFirstname());
		employeeDto.setLastname(employee.getLastname());
		employeeDto.setEmail(employee.getEmail());
		return employeeDto;
	}

	
	/////CONTROLLER TEST TO SAVE THE EMPLOYEE//////
	@Test
	public void createEmployeeTest() throws Exception
	{
	EmployeeDto savedEmployeeDto = new EmployeeDto(1L, "deepu","gudisa","abc@gmail.com");
                  
		  given(employeeService.createEmployee(any(EmployeeDto.class))).willReturn(savedEmployeeDto);
	ResultActions result= mockMvc.perform(post("/api/employee")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(savedEmployeeDto)));
		  
		  result.andDo(print()).andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("deepu"));	
	}

	//////CONTROLLER TEST TO GET EMPLOYEE BY ID////
	@Test
	public void getEmployeeTest() throws Exception 
	{
		EmployeeDto employeeDto = new EmployeeDto(1L, "deepu","gudisa","abc@gmail.com");
		given(employeeService.getEmployee(employeeDto.getId())).willReturn(employeeDto);
	  ResultActions result = mockMvc.perform(get("/api/employee/{id}", 1L));
	  result.andDo(print()).andExpect(status().isOk());	
	}
	
	
///////CONTROLLER TEST TO GET ALL EMPLOYEEES/////
	@Test
	public void getAllEmployeesTest() throws Exception
	{
		EmployeeDto employeeDto1 = new EmployeeDto(1L, "deepu","gudisa","abc@gmail.com");
		EmployeeDto employeeDto2 = new EmployeeDto(2L, "john","cena","john@gmail.com");
		List<EmployeeDto> employees = new ArrayList<>();
		employees.add(employeeDto1);
		employees.add(employeeDto2);
		
		given(employeeService.getAllEmployees()).willReturn(employees);
		mockMvc.perform(get("/api/employee/all")).andDo(print()).andExpect(status().isOk());
	}
	 
	
	/////CONTROLLER TEST TO EDIT EMPLOYEE BY ID////
	@Test
	public void toeditemployeeTest() throws Exception
	{
		Employee employee = new Employee(1L, "deepu","gudisa","abc@gmail.com");
		employee.setFirstname("priya");
		EmployeeDto employeeDto = employeeToDto(employee);
		
		given(employeeService.toeditemployee(anyLong(), any(Employee.class))).willReturn(employeeDto);
		ResultActions result =  mockMvc.perform(put("/api/employee/editbyid/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee)));
		result.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.firstname").value("priya"));
	}
	
	
	//////CONTROLLER TEST TO DELETE EMPLOYEE BY ID///////
	@Test
	public void deleteEmployeeByIdTest() throws Exception
	{
		Employee employee = new Employee(1L, "deepu","gudisa","abc@gmail.com");
		employeeService.deleteEmployeeById(anyLong());
		ResultActions result = mockMvc.perform(delete("/api/employee/deletebyid/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON));
		result.andDo(print()).andExpect(status().isOk());
		
	}
}
