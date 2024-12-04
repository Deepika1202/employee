package com.task.employee;

import com.task.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository <Employee,Long>{
}
