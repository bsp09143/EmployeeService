package com.iss.test;

import static org.junit.Assert.assertEquals;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.iss.EmployeeServiceApplication;
import com.iss.controller.EmployeeController;
import com.iss.model.Employee;
import com.iss.service.EmployeeService;
import com.iss.service.impl.EmployeeServiceImpl;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EmployeeServiceApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeService_Tests
{

	Employee emp = new Employee("emp3", "emp3");
	List<Employee> empList = new ArrayList<Employee>();

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void GetEmployeeById_Should_Return_Single_Employee() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/employee/emp1").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.empId").exists()).andExpect(jsonPath("$.empName").exists())
				.andExpect(jsonPath("$.empId").value("emp1")).andExpect(jsonPath("$.empName").value("emp1"))
				.andDo(print());
	}

	@Test
	public void GetAllEmployees_Should_Return_All_Employees() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/employee").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(19))).andDo(print());
	}

	@Test
	public void InsertEmployee_Should_Insert_New_Employees() throws Exception
	{
		System.out.println("Mock_InsertEmployee_Should_Insert_New_Employees");
		String empJson = "{\"empId\":\"emp20\",\"empName\":\"emp20\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/employee").accept(MediaType.APPLICATION_JSON).content(empJson)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.empId").exists())
				.andExpect(jsonPath("$.empName").exists()).andExpect(jsonPath("$.empId").value("emp20"))
				.andExpect(jsonPath("$.empName").value("emp20")).andDo(print());
	}

	@Test
	public void InsertEmployee_Using_Form_Should_Insert_New_Employees() throws Exception
	{
		System.out.println("Mock_InsertEmployee_Should_Insert_New_Employees");

		// mockMvc.perform(
		// MockMvcRequestBuilders.post("/employee").param("empId",
		// "emp21").param("empName", "emp21"));

		mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(EntityUtils
						.toString(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("empId", "emp25"),
								new BasicNameValuePair("empName", "emp25"))))));

		// String empJson = "{\"empId\":\"emp20\",\"empName\":\"emp20\"}";
		// mockMvc
		// .perform(MockMvcRequestBuilders.post("/employee").accept(MediaType.APPLICATION_JSON)
		// .content(empJson).contentType(MediaType.APPLICATION_JSON))
		// .andExpect(jsonPath("$.empId").exists()).andExpect(jsonPath("$.empName").exists())
		// .andExpect(jsonPath("$.empId").value("emp20"))
		// .andExpect(jsonPath("$.empName").value("emp20")).andDo(print());

		// mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		// .content(EntityUtils.toString(
		// new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("empId",
		// "emp21"),
		// new BasicNameValuePair("empName", "emp21"))))));
	}

	String buildUrlEncodedFormEntity(String... params)
	{
		if ((params.length % 2) > 0)
		{
			throw new IllegalArgumentException("Need to give an even number of parameters");
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < params.length; i += 2)
		{
			if (i > 0)
				result.append('&');
			result.append(URLEncoder.encode(params[i])).append('=').append(URLEncoder.encode(params[i + 1]));
		}
		return result.toString();
	}

	@Test
	public void DeleteEmployee_Should_Delete_Employee() throws Exception
	{
		System.out.println("Mock_DeleteEmployee_Should_Delete_Employee");

		mockMvc.perform(MockMvcRequestBuilders.delete("/employee/emp20").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.message").value("Employee has been deleted")).andDo(print());
	}

	@Test
	public void verifyInvalidEmployeeId() throws Exception
	{
		System.out.println("-------------------------------------------------");
		System.out.println("verifyInvalidToDoArgument");
		mockMvc.perform(MockMvcRequestBuilders.get("/employee/emp35").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(404))
				.andExpect(jsonPath("$.message").value("Employee doesn´t exist")).andDo(print());
	}

	@Test
	public void verifyInvalidEmployeeIdToDelete() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/employee/45").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(404))
				.andExpect(jsonPath("$.message").value("Employee to delete doesn´t exist")).andDo(print());
	}
}
