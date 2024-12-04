package com.task.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.employee.entity.Employee;

import java.util.Optional;


@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

   Optional<Employee>  findByusername(String username);

    Optional<Employee> findByEmail(String email);
}
