package com.iss.service;

import java.util.List;

import com.iss.exception.EmployeeException;
import com.iss.model.Employee;

public interface EmployeeService {
  boolean insertEmployee(Employee emp);

  void insertEmployees(List<Employee> employees);

  List<Employee> getAllEmployees();

  Employee getEmployeeById(String empid) throws EmployeeException;

  boolean deleteEmployeeeById(String empId) throws EmployeeException;

}
