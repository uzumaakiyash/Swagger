package com.pack.ppkTest;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pack.ppkTest.Controller.EmployeeController;
import com.pack.ppkTest.Model.Employee;
import com.pack.ppkTest.Repository.EmployeeRepository;
import com.pack.ppkTest.Service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc; // test the controller

	@MockBean
	private EmployeeService employeeService;

	private static ObjectMapper mapper = new ObjectMapper();

	@MockBean
	EmployeeRepository employeeRepository;

	@Before
	public void setup() {
		mapper.registerModule(new JavaTimeModule());
	}

	@Test
	public void testCreatePet() throws Exception {
		// Create a Pet object with the required fields
		Employee emp = new Employee();
		emp.setName("Tom");
		emp.setAge(2);
		emp.setSalary(20000.00);
		emp.setDesignation("Home");

		// Convert the Pet object to JSON string
		String empJson = mapper.writeValueAsString(emp);

		// Perform the POST request
		mockMvc.perform(MockMvcRequestBuilders.post("/Employee/addEmployee").contentType(MediaType.APPLICATION_JSON)
				.content(empJson)).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void testGetAllEmployee() throws Exception {
		List<Employee> l = new ArrayList<>();
		l.add(new Employee(106, "naruto", 3, 20000.00, "Navalpur"));
		l.add(new Employee(107, "sasuke", 1, 30000.00, "Navalpur"));
		Mockito.when(employeeService.fetchAllEmployee()).thenReturn(l);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Employee/allEmployee")).andReturn();
		String res = result.getResponse().getContentAsString();
		List<Employee> emplist = new ArrayList<>();
		emplist = Arrays.asList(mapper.readValue(res, Employee[].class));
		assertEquals(l.size(), emplist.size());
	}

	@Test
	public void testGetPetById() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		Employee e = new Employee(102, "naruto", 3, 10000.00, "Navalpur");
		Optional<Employee> optionalEmp = Optional.of(e); // Wrap the pet object in an Optional
		Mockito.when(employeeService.findById(102)).thenReturn(optionalEmp); // Return the Optional<Pet>

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Employee/getEmployeeById/102")).andReturn();
		String res = result.getResponse().getContentAsString();

		Employee emp = objectMapper.readValue(res, Employee.class);

		assertEquals(e.getId(), emp.getId());
		assertEquals(e.getName(), emp.getName());
		assertEquals(e.getAge(), emp.getAge());
		assertEquals(e.getSalary(), emp.getSalary());
		assertEquals(e.getDesignation(), emp.getDesignation());
	}

	@Test
	public void testUpdateEmployee() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		int id = 102;
		Employee existingEmp = new Employee(102, "naruto", 3, 10000.00, "Navalpur");
		Employee updatedEmp = new Employee(102, "updatedNaruto", 4, 10000.00, "UpdatedPlace");

		Optional<Employee> optionalEmp = Optional.of(existingEmp);
		Mockito.when(employeeService.findById(id)).thenReturn(optionalEmp);
		Mockito.when(employeeService.insertEmployee(Mockito.any(Employee.class))).thenReturn(updatedEmp);

		String requestBody = objectMapper.writeValueAsString(updatedEmp);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/Employee/updateEmployee/{id}", id)
				.contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn();

		int statusCode = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), statusCode);

		String responseString = result.getResponse().getContentAsString();
		Employee responsePet = objectMapper.readValue(responseString, Employee.class);

		assertEquals(updatedEmp.getName(), responsePet.getName());
		assertEquals(updatedEmp.getAge(), responsePet.getAge());
		assertEquals(updatedEmp.getSalary(), responsePet.getSalary());
		assertEquals(updatedEmp.getDesignation(), responsePet.getDesignation());
	}

	@Test
	public void testDeleteEmployee() throws Exception {
		int id = 102;

		Mockito.doNothing().when(employeeService).deleteEmployeeById(id);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/Employee/deleteEmployee/{id}", id))
				.andReturn();

		int statusCode = result.getResponse().getStatus();
		assertEquals(HttpStatus.NO_CONTENT.value(), statusCode);
	}

}
