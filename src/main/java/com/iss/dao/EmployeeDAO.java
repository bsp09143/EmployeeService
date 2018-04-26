package com.iss.dao;

import java.util.List;

import com.iss.exception.EmployeeException;
import com.iss.model.Employee;

public interface EmployeeDAO {

  boolean insertEmployee(Employee emp);

  void insertEmployees(List<Employee> employees);

  List<Employee> getAllEmployees();

  Employee getEmployeeById(String empId)  throws EmployeeException;

  boolean deleteEmployeeById(String empId) throws EmployeeException;
}
