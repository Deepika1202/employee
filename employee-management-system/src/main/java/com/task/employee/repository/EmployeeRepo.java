package com.task.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.employee.entity.Employee;


@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

}
