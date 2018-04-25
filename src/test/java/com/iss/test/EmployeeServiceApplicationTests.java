package com.iss.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class EmployeeServiceApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;


  Employee emp = new Employee("emp0", "emp0");

  @Test
  public void Mock_GetEmployeeById_Should_Return_EmployeeDetail() throws Exception {

    Mockito.when(employeeService.getEmployeeById(Mockito.anyString())).thenReturn(emp);

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/employee/emp0").accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    System.out.println(result.getResponse().getContentAsString());
    String expected = "{\n" + "  \"empId\":\"emp20\",\n" + "  \"empName\":\"emp20\"\n" + "}";

    JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
  }
}
