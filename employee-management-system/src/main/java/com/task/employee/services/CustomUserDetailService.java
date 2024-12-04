package com.task.employee.services;

import com.task.employee.configurations.EmployeeUserDetailsConv;
import com.task.employee.entity.Employee;
import com.task.employee.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Employee> employee =  employeeRepo.findByusername(username);
       return employee.map(EmployeeUserDetailsConv::new)
               .orElseThrow(()-> new UsernameNotFoundException("user not found "+username));


    }
}
