package com.iss;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iss.model.Employee;
import com.iss.service.EmployeeService;

@SpringBootApplication
public class EmployeeServiceApplication {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(EmployeeServiceApplication.class, args);
    
//    int i = 0;
//	while (i < 10) {
//		String password = "vasudev";
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String hashedPassword = passwordEncoder.encode(password);
//
//		System.out.println(hashedPassword);
//		i++;
//	}

//     EmployeeService employeeService = context.getBean(EmployeeService.class);
//     List<Employee> employees = new ArrayList<>();
//     System.out.println("Inserting 20 Employees Data in batch!");
//     for (int i = 0; i < 20; i++) {
//     Employee empNew = new Employee();
//     empNew.setEmpId("emp" + i);
//     empNew.setEmpName("emp" + i);
//     employees.add(empNew);
//     }
//     employeeService.insertEmployees(employees);
//     System.out.println("Insertion Completed!!");
  }
}
