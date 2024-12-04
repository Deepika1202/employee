package com.task.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.employee.entity.Employee;
import com.task.employee.model.AuthRequest;
import com.task.employee.model.EmployeeDto;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.task.employee.services.JwtService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
  public class EmployeeManagementSystemApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeManagementSystemApplicationTests.class);
	@Autowired
	private TestH2Repository h2Repository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JwtService jwtService;
	@LocalServerPort
	private int port;


	@Test
	void contextLoads() {
	}

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void testAddUser()
	{
		Employee testAuthUser = new Employee();
		testAuthUser.setUsername("Priya");
		testAuthUser.setPassword(passwordEncoder.encode("priya123"));
		h2Repository.save(testAuthUser);
			List<Employee> employees= h2Repository.findAll();
			logger.info("Employees : {}",employees);
	}

	@BeforeEach
	public void setUp()
	{

	}

@Test
	public  void testCreateEmployees() throws Exception {
		 String baseUrl = "http://localhost:" + port + "/api/employee";
	logger.info("running Port  is {}", port);
	EmployeeDto employeeDto = new EmployeeDto();
	employeeDto.setUsername("Deepu");
	employeeDto.setPassword("deepu123");
	employeeDto.setEmail("deepu@gmail.com");
	employeeDto.setRole("USER");
	this.mockMvc.perform(post("/api/employee"));

	//HttpEntity<EmployeeDto> request = new HttpEntity<>(employeeDto);
	//ResponseEntity<EmployeeDto> response = restTemplate.postForEntity(baseUrl, request, EmployeeDto.class)


}

@Test
	public void getAllEmployeesTest()
{
	String baseUrl = "http://localhost:" + port + "/api/employee/all";
mockMvc.perform(get(baseUrl));


}


}
