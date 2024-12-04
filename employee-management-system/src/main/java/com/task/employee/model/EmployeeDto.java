package com.task.employee.model;




import jakarta.persistence.Column;
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
	@Column(unique = true)
	private String username;
	

	
	@Email(message = "Enter a valid Email address!!")
	@Column(unique = true)
	private String email;
	private String password;
	private String role;
   
	

}
