package com.iss.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.iss.controller.EmployeeController;
import com.iss.model.Employee;
import com.iss.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class, secure = false)
public class EmployeeService_Mock_Tests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;


  Employee emp = new Employee("emp0", "emp0");
  List<Employee> empList = new ArrayList<Employee>();
  
  
  @Test
  public void Mock_GetEmployeeById_Should_Return_Single_Employee() throws Exception {

    Mockito.when(employeeService.getEmployeeById(Mockito.anyString())).thenReturn(emp);

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/employee/emp0").accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    System.out.println(result.getResponse().getContentAsString());
    String expected = "{\"empId\":\"emp0\",\"empName\":\"emp0\"}";
    JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
  }
  
  @Test
  public void Mock_GetAllEmployees_Should_Return_All_Employees() throws Exception {

	  for(int i = 0;i<2;i++)
	  {
		  Employee emp = new Employee("emp"+i,"emp"+1);
		  empList.add(emp);
	  }
    Mockito.when(employeeService.getAllEmployees()).thenReturn(empList);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/employee").accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    System.out.println(result.getResponse().getContentAsString());
    String expected = "[{\"empId\":\"emp0\",\"empName\":\"emp1\"},{\"empId\":\"emp1\",\"empName\":\"emp1\"}]";
    JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
  }
  
  @Test
  public void Mock_InsertEmployee_Should_Insert_New_Employees() throws Exception {
	  
    Mockito.when(employeeService.insertEmployee(Mockito.any(Employee.class))).thenReturn(true);
    String empJson = "{\"empId\":\"emp0\",\"empName\":\"emp0\"}";
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/employee").accept(MediaType.APPLICATION_JSON).content(empJson)
        .contentType(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();    
    assertEquals(HttpStatus.CREATED.value(), response.getStatus());    
    
  }
  
  @Test
  public void Mock_DeleteEmployee_Should_Delete_Employee() throws Exception {
	  
    Mockito.when(employeeService.deleteEmployeeeById(Mockito.anyString())).thenReturn(true);
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/employee/emp0").accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();    
    assertEquals(HttpStatus.OK.value(), response.getStatus());   
    
  }
  
  
}
