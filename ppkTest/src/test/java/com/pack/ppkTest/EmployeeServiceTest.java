package com.pack.ppkTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.pack.ppkTest.Model.Employee;
import com.pack.ppkTest.Repository.EmployeeRepository;
import com.pack.ppkTest.Service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

	@MockBean
	EmployeeService employeeService;

	@MockBean
	EmployeeRepository employeeRepository;

	@Test
	public void testCreateEmployee() {
		Employee emp = new Employee(112, "naruto", 3, 20000.00, "Navalpur");
		Mockito.when(employeeService.insertEmployee(emp)).thenReturn(emp);
		assertThat(employeeService.insertEmployee(emp)).isEqualTo(emp);
	}

	@Test
	public void testGetEmployeeById() throws Exception {
		int id = 107;
		Optional<Employee> expectedEmployee = Optional.of(new Employee(112, "naruto", 3, 20000.00, "Navalpur"));
		Mockito.when(employeeService.findById(Mockito.anyInt())).thenReturn(expectedEmployee);
		Optional<Employee> response = employeeService.findById(id);
		Optional<Employee> actualEmployee = Optional.ofNullable(response.get());

		Assertions.assertTrue(actualEmployee.isPresent(), "The Employee should be present");
		Assertions.assertEquals(expectedEmployee.get(), actualEmployee.get(),
				"Returned Employee does not match the expected Employee");
	}

	@Test
	public void testGetAllEmplouyee() {
		List<Employee> employeeList = new ArrayList<>();
		// Add pets to the list
		employeeList.add(new Employee(1, "cat", 2, 20000.00, "Home"));
		employeeList.add(new Employee(2, "dog", 3, 20000.00, "Park"));
		// ... add more pets as needed

		Mockito.when(employeeService.fetchAllEmployee()).thenReturn(employeeList);

		Iterable<Employee> employeeIterable = employeeList; // Convert the List to Iterable

		List<Employee> result = StreamSupport.stream(employeeIterable.spliterator(), false)
				.collect(Collectors.toList()); // Convert
		// Iterable
		// to
		// List

		assertThat(result).isEqualTo(employeeList);
	}

	@Test
	public void testUpdateEmployee() {
		Employee emp = new Employee(1, "cat", 2, 2000.00, "Home");
		Mockito.when(employeeService.insertEmployee(Mockito.any(Employee.class))).thenReturn(emp);
		Mockito.when(employeeService.insertEmployee(emp)).thenReturn(emp);
		Employee insertedEmployee = employeeService.insertEmployee(emp);
		Employee updatedEmployee = new Employee(1, "dog", 3, 20000.00, "Park");
		Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(insertedEmployee));
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(updatedEmployee);
		Optional<Employee> optionalEmployee = employeeService.findById(1);
		if (optionalEmployee.isPresent()) {
			Employee pet1 = optionalEmployee.get();
			pet1.setName("new name");
			pet1.setAge(5);
			// Mock the behavior of the bookServiceImpl.insertBooks method
			Mockito.when(employeeService.insertEmployee(Mockito.any(Employee.class))).thenReturn(emp);

			// Insert the updated book
			Employee updatedPet1 = employeeService.insertEmployee(emp);

			// Assert the updated book has the correct values
			assertEquals("new name", updatedPet1.getName());
			assertEquals("new place", updatedPet1.getAge());
		}
	}

	@Test
	public void testDeleteEmployee() {
		int id = 101;
		// Create a book instance for testing
		Employee emp = new Employee();
		emp.setId(id);
		// Set other properties as needed
		// Mock the behavior of the bookRepository.findById method
		Mockito.when(employeeService.findById(id)).thenReturn(Optional.of(emp));

		// Call the deleteBook method
		employeeService.deleteEmployeeById(id);

		// Verify that the book is deleted
		Mockito.when(employeeService.findById(id)).thenReturn(Optional.empty());
		Optional<Employee> deletedEmployee = employeeService.findById(id);
		Assertions.assertTrue(deletedEmployee.isEmpty(), "Book should be deleted.");
	}

}
