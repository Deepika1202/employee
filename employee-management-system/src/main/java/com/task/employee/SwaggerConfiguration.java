package com.task.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
@OpenAPIDefinition(servers = {
	    @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080", description = "Local server")
})
public class SwaggerConfiguration {
	
	@Bean
	public OpenAPI myCustomApiCongig()
	{
		return new OpenAPI()
				.info(new Info()
						.title("EMPLOYEE MANAGEMENT API APPLICATION")
						.version("1.0")
						.description("This is a Employee Management API Documentation using springdoc-openapi and OpenAPI 3"));
		
		
	}

}
