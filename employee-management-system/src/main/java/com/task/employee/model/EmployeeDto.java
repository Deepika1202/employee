package com.task.employee.model;




import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeDto {

	
	
	private long id;
	
	@NotEmpty(message = "Please Enter First Name!!")
	private String firstname;
	
	@NotEmpty(message = "Please Enter Last Name!!")
	private String lastname;
	
	@Email(message = "Enter a valid Email address!!")
	private String email;
	
   
	

}
