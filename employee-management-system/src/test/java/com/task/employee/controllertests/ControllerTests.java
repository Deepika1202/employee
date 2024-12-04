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

import com.task.employee.services.CustomUserDetailService;
import com.task.employee.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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
@AutoConfigureMockMvc

public class ControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EmployeeService employeeService;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private JwtService jwtService;

	@MockBean
	CustomUserDetailService customUserDetailService;




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
		employeeDto.setUsername(employee.getUsername());
		employeeDto.setEmail(employee.getEmail());
		employeeDto.setRole(employee.getRole());
		return employeeDto;
	}

	
	/////CONTROLLER TEST TO SAVE THE EMPLOYEE//////
	@Test
	//@WithMockUser(username = "John", roles="ADMIN")
	public void createEmployeeTest() throws Exception
	{
	EmployeeDto savedEmployeeDto = new EmployeeDto(1L, "Sri","abc@gmail.com","abc","USER");
                  
		 given(employeeService.createEmployee(any(EmployeeDto.class))).willReturn(savedEmployeeDto);
	ResultActions result= mockMvc.perform(post("/api/employee")
				.with(SecurityMockMvcRequestPostProcessors.user("deepu").roles("ADMIN"))
					.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(savedEmployeeDto)));
		  result.andDo(print()).andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Sri"));
	}
	//////CONTROLLER TEST TO GET EMPLOYEE BY ID////
	@Test
	@WithMockUser(username = "John", roles="USER")
	public void getEmployeeTest() throws Exception 
	{
		EmployeeDto employeeDto = new EmployeeDto(1L, "deepu","gudisa","abc@gmail.com","USER");
		given(employeeService.getEmployee(employeeDto.getId())).willReturn(employeeDto);
	  ResultActions result = mockMvc.perform(get("/api/employee/{id}", 1L));
	  result.andDo(print()).andExpect(status().isOk());	
	}
	
	
///////CONTROLLER TEST TO GET ALL EMPLOYEEES/////
	@Test
	@WithMockUser(username = "John", roles="ADMIN")
	public void getAllEmployeesTest() throws Exception
	{
		EmployeeDto employeeDto1 = new EmployeeDto(1L, "deepu","gudisa","abc@gmail.com","USER");
		EmployeeDto employeeDto2 = new EmployeeDto(2L, "john","cena","john@gmail.com","USER");
		List<EmployeeDto> employees = new ArrayList<>();
		employees.add(employeeDto1);
		employees.add(employeeDto2);
		
		given(employeeService.getAllEmployees()).willReturn(employees);
		mockMvc.perform(get("/api/employee/all")).andDo(print()).andExpect(status().isOk());
	}
	 
	
	/////CONTROLLER TEST TO EDIT EMPLOYEE BY ID////
	@Test
	@WithMockUser(username = "John", roles="ADMIN")
	public void toeditemployeeTest() throws Exception
	{
		Employee employee = new Employee(1L, "deepu","gudisa","abc@gmail.com","USER");
		employee.setUsername("priya");
		EmployeeDto employeeDto = employeeToDto(employee);
		
		given(employeeService.toeditemployee(anyLong(), any(Employee.class))).willReturn(employeeDto);
		ResultActions result =  mockMvc.perform(put("/api/employee/editbyid/{id}", 1L)
			//	.with(SecurityMockMvcRequestPostProcessors.user("deepu").roles("USER"))
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee)));

		result.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.username").value("priya"));
	}
	
	
	//////CONTROLLER TEST TO DELETE EMPLOYEE BY ID///////
	@Test
	//@WithMockUser(username = "John", roles="ADMIN")
	public void deleteEmployeeByIdTest() throws Exception
	{
		Employee employee = new Employee(1L, "deepu","gudisa","abc@gmail.com","USER");
		employeeService.deleteEmployeeById(anyLong());
		ResultActions result = mockMvc.perform(delete("/api/employee/deletebyid/{id}", 1L)
				.with(SecurityMockMvcRequestPostProcessors.user("deepu").roles("ADMIN"))
					.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON));
		result.andDo(print()).andExpect(status().isOk());
		
	}
}
