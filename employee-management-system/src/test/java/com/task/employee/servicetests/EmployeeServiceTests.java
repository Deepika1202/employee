package com.task.employee.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito.Then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.task.employee.entity.Employee;
import com.task.employee.model.EmployeeDto;
import com.task.employee.repository.EmployeeRepo;
import com.task.employee.serviceImpl.EmployeeServiceImpl;
import com.task.employee.services.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    
	@Mock
	private EmployeeRepo employeeRepo;
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;
	private Employee employee;
	
	
	////METHOD TO CONVERT EMPLOYEE TO EMPLOYEEDTO
	public static EmployeeDto employeeToDto(Employee employee)
	{
		 if (employee == null) {
	            throw new IllegalArgumentException("Employee cannot be null");
	        }
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
	//	employeeDto.setirstname(employee.getFirstname());
//		employeeDto.setLastname(employee.getLastname());
		employeeDto.setUsername(employee.getUsername());
		employeeDto.setPassword(employee.getPassword());
		employeeDto.setEmail(employee.getEmail());
		employeeDto.setRole(employee.getRole());
		return employeeDto;
	}
	
	
  ////METHOD TO CONVERTEMPLOYEEDTO TO EMPLOYEE
	public static Employee dtoToEmployee(EmployeeDto employeeDto)
	{
		Employee employee = new  Employee();
		
		employee.setId(employeeDto.getId());
		employee.setUsername(employeeDto.getUsername());
		//employee.setLastname(employeeDto.getLastname());
		employee.setEmail(employeeDto.getEmail());
		employee.setPassword(employeeDto.getPassword());
		employee.setRole(employeeDto.getRole());
		return employee;
	}
	
//	
	@BeforeEach
	public void setUp()
	{
		 MockitoAnnotations.openMocks(this);	
		 employee = Employee.builder()
	    			.id(1L)
	    			.username("John")
	    			.email("abc@gmail.com")
				 .password("john123")
				 .role("USER")
				 .build();
	}
	
	
	///TEST METHOD FOR CREATING EMPLOYEE
    @Test
	public void CreateEmployeeTest()
	{
    	EmployeeDto employeeDto = employeeToDto(employee);
		when(employeeRepo.save(any(Employee.class))).thenReturn(employee);
		EmployeeDto savedEmployee = employeeServiceImpl.createEmployee(employeeDto);
		assertNotNull(savedEmployee);
		assertEquals("John",savedEmployee.getUsername());
	}
    
    
   ////TEST METHOD TO GET ALL THE EMPLOYEES
    @Test
    public void getAllEmployeesTest()
    {
    	Employee employee1 = new Employee(1L,"John","john123","abc@gmail.com","ADMIN");
    			
    	Employee employee2 = new Employee(2L,"fed","fed123","fed@gmail.com","USER");

//        List<Employee> employees = new ArrayList();
//        employees.add(employee1);
//        employees.add(employee2);	
       when(employeeRepo.findAll()).thenReturn(List.of(employee1, employee2));
   List<EmployeeDto> savedEmployees  = employeeServiceImpl.getAllEmployees();
       assertEquals(2,savedEmployees.size());
       assertEquals("fed",savedEmployees.get(1).getUsername());
    }
    
    
    ///TEST METHOD TO GET EMPLOYEE BY ID
    @Test
    public void getEmployeeTest()
    {

    	when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
    	EmployeeDto employeeDto = employeeServiceImpl.getEmployee(employee.getId());
    	assertNotNull(employeeDto);
    	assertEquals("abc@gmail.com",employeeDto.getEmail());
    }
    
    
    ///TEST TO DELETE EMPLOYEE BY ID
    @Test
    public void deleteEmployeeByIdTest()
    {
      when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));
      doNothing().when(employeeRepo).deleteById(1L);
       employeeServiceImpl.deleteEmployeeById(1L);
       verify(employeeRepo,times(1)).deleteById(1L);
       System.out.println();
    }
    
    
     ///TEST TO UPDATE THE EMPLOYEE BY ID
    @Test
    public void toeditemployeeTest()
    {
    	when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
    	employee.setUsername("jack");
    	EmployeeDto employeeDto = employeeServiceImpl.toeditemployee(employee.getId(), employee);
  
    	assertNotNull(employee);
    	assertEquals("jack",employee.getUsername());
    	System.out.print(employee.getUsername());
    }
}
