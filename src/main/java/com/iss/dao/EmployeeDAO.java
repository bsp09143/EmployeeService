package com.iss.dao;

import java.util.List;
import com.iss.model.Employee;

public interface EmployeeDAO {

  boolean insertEmployee(Employee cus);

  void insertEmployees(List<Employee> employees);

  List<Employee> getAllEmployees();

  Employee getEmployeeById(String empId);

  boolean deleteEmployeeById(String empId);
}
