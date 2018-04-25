package com.iss.model;

import java.util.List;

public class Employee {

  private String empId;
  private String empName;

  public Employee() {

  }

  public Employee(String empid, String empName) {
    super();
    this.empId = empid;
    this.empName = empName;
  }

  public String getEmpId() {
    return empId;
  }

  public void setEmpId(String empId) {
    this.empId = empId;
  }

  public String getEmpName() {
    return empName;
  }

  public void setEmpName(String empName) {
    this.empName = empName;
  }

  @Override
  public String toString() {
    return "Employee [empId=" + empId + ", empName=" + empName + "]";
  }

}
