# Employee Management System Backend

## Overview
This is the backend application for the Employee Management System, built using Spring Boot. It provides RESTful APIs for managing employee records.

## Features
- **Employee Records Management**: APIs to add, update, and delete employee information.

## Technologies Used
- **Spring Boot**: For building the backend application.
- **Spring Data JPA**: For database interactions.
- **MySQL**: As the database.
- **Swagger**: For API documentation.

### Prerequisites
- Java 11 or higher
- Maven
- MySQL

### Steps
1. **Clone the repository**:
   git clone https://github.com/Deepika1202/employee.git
2. **Navigate to the project**:
   cd employee-management-system
3. **Set up the database**:
   -> Create a database named **employee_management_app**.
     -> Update the application.properties file with this database credentials:
   **spring.datasource.url=jdbc:mysql://localhost:3306/employee_management_app
      spring.datasource.username=root
      spring.datasource.password=root@123**
4. **Build the project**:
   mvn clean install
5. **Run the application**:
   mvn spring-boot:run

### API Documentation
The API documentation is available at 
**http://localhost:8080/swagger-ui/index.html**.

### Testing 
**Unit Tests**
Unit tests are written using Junit and Mockito. To run the unit tests, use the following command : 
**mvn test**
**Controller Tests**
Controller Tests are written using MockMvc. to run the tests, use the following command:
**mvn test**

### Contact
**deepikagudisa@gmail.com**
   

  
