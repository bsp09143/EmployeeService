package com.iss.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.iss.model.Employee;
import com.iss.service.EmployeeService;;

@RestController
public class EmployeeController {
  @Autowired
  private EmployeeService employeeService;

  @GetMapping("/employee")
  public List<Employee> retrieveAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @PostMapping("/employee")
  public ResponseEntity<Void> registerEmployeeInfo(@RequestBody Employee newEmployee) {

    boolean result = employeeService.insertEmployee(newEmployee);

    if (result == false)
      return ResponseEntity.noContent().build();

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(newEmployee.getEmpId()).toUri();

    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/employee/{employeeId}")
  public boolean getEmployeeById(@PathVariable String employeeId) {
    boolean result = employeeService.deleteEmployeeeById(employeeId);
    return result;
  }

}
