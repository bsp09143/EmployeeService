package com.iss.controller;

import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.iss.exception.EmployeeException;
import com.iss.model.Employee;
import com.iss.model.Response;
import com.iss.service.EmployeeService;;

@RestController
public class EmployeeController {


  @Autowired
  private EmployeeService employeeService;

  @GetMapping("/employee")
  public List<Employee> retrieveAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @GetMapping("/employee/{employeeId}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId)
      throws EmployeeException {
    Employee emp = employeeService.getEmployeeById(employeeId);
    if (emp == null) {
      throw new EmployeeException("Employee doesn´t exist");
    }
    return new ResponseEntity<Employee>(emp, HttpStatus.OK);
  }

  // @PostMapping("/employee")
  // public ResponseEntity<Employee> registerEmployeeInfo(@RequestBody Employee newEmployee) {
  //
  // boolean result = employeeService.insertEmployee(newEmployee);
  //
  // if (result == false)
  // return ResponseEntity.noContent().build();
  //
  // return new ResponseEntity<Employee>(newEmployee, HttpStatus.CREATED);
  // }

  @PostMapping("/employee")
  public ResponseEntity<Employee> registerUsingForm(@ModelAttribute Employee employee) {
    boolean result = employeeService.insertEmployee(employee);
    if (result == false)
      return ResponseEntity.noContent().build();

    return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);

  }

  @DeleteMapping("/employee/{employeeId}")
  public ResponseEntity<Response> deleteEmployeeById(@PathVariable String employeeId)
      throws EmployeeException {
    boolean result = employeeService.deleteEmployeeeById(employeeId);

    if (result == false) {
      throw new EmployeeException("Employee to delete doesn´t exist");
    }

    return new ResponseEntity<Response>(
        new Response(HttpStatus.OK.value(), "Employee has been deleted"), HttpStatus.OK);
  }

}
