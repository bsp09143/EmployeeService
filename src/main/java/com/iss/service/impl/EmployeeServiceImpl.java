package com.iss.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iss.dao.EmployeeDAO;
import com.iss.exception.EmployeeException;
import com.iss.model.Employee;
import com.iss.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  EmployeeDAO employeeDao;

  @Override
  public boolean insertEmployee(Employee employee) {
    boolean result = employeeDao.insertEmployee(employee);
    return result;
  }

  @Override
  public void insertEmployees(List<Employee> employees) {
    employeeDao.insertEmployees(employees);
  }

  @Override
  public List<Employee> getAllEmployees() {
    List<Employee> employees = employeeDao.getAllEmployees();
    return employees;
  }

  @Override
  public Employee getEmployeeById(String empId) throws EmployeeException {
    Employee employee = employeeDao.getEmployeeById(empId);
    return employee;
  }

  @Override
  public boolean deleteEmployeeeById(String empId)throws EmployeeException {

    return employeeDao.deleteEmployeeById(empId);
  }


}
