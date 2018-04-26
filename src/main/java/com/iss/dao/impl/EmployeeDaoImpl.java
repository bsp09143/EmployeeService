package com.iss.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.iss.dao.EmployeeDAO;
import com.iss.exception.EmployeeException;
import com.iss.model.Employee;

@Repository
public class EmployeeDaoImpl extends JdbcDaoSupport implements EmployeeDAO {
  @Autowired
  DataSource dataSource;
  Logger log = LoggerFactory.getLogger(this.getClass());

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  @Override
  public boolean insertEmployee(Employee emp) {
    boolean result;
    try {
      String sql = "INSERT INTO employee " + "(empId, empName) VALUES (?, ?)";
      getJdbcTemplate().update(sql, new Object[] {emp.getEmpId(), emp.getEmpName()});
      result = true;
    } catch (DataAccessException e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void insertEmployees(List<Employee> employees) {
    String sql = "INSERT INTO employee " + "(empId, empName) VALUES (?, ?)";
    getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Employee employee = employees.get(i);
        ps.setString(1, employee.getEmpId());
        ps.setString(2, employee.getEmpName());
      }

      @Override
      public int getBatchSize() {
        return employees.size();
      }
    });

  }

  @Override
  public List<Employee> getAllEmployees() {
    String sql = "SELECT * FROM employee";
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

    List<Employee> result = new ArrayList<Employee>();
    for (Map<String, Object> row : rows) {
      Employee emp = new Employee();
      emp.setEmpId((String) row.get("empId"));
      emp.setEmpName((String) row.get("empName"));
      result.add(emp);
    }

    return result;
  }

  @Override
  public Employee getEmployeeById(String empId) throws EmployeeException {
    String sql = "SELECT * FROM employee WHERE empId = ?";
    Employee emp = new Employee();
    try {
      emp = getJdbcTemplate().queryForObject(sql, new Object[] {empId}, new RowMapper<Employee>() {
        @Override
        public Employee mapRow(ResultSet rs, int rwNumber) throws SQLException {
          Employee emp = new Employee();
          emp.setEmpId(rs.getString("empId"));
          emp.setEmpName(rs.getString("empName"));
          return emp;
        }
      });
    } catch (DataAccessException e) {
      log.error("Data Access Exception" + e);
      return null;
    }
    return emp;
  }

  @Override
  public boolean deleteEmployeeById(String empId) throws EmployeeException {
    boolean result;
    try {
      String sql = "DELETE FROM employee WHERE empId = ?";
      int id = getJdbcTemplate().update(sql, new Object[] {empId});
      if (id == 0) {
        result = false;
      } else {
        result = true;
      }
    } catch (DataAccessException e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  }
}
