package com.pack.ppkTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.pack.ppkTest.Model.Employee;
import com.pack.ppkTest.Repository.EmployeeRepository;

@RunWith(SpringRunner.class/* to enable springboot enable features */) // junit 4
@SpringBootTest // load full application context
public class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void TestInsertEmployee() {
		Employee E = new Employee(106, "naruto", 3, 10000.00, "Navalpur");
		Employee E1 = employeeRepository.save(E);
		Optional<Employee> opt = employeeRepository.findById(E1.getId());
		Employee E2 = (Employee) opt.get();// to get value from optional we use get method
		assertEquals(E1.getId(), E2.getId());
	}

	@Test
	public void testGetAllEmployee() {
		List<Employee> list = employeeRepository.findAll();
		List<Employee> employeelist = new ArrayList<>();
		for (Employee E : list)
			employeelist.add(E);
		assertThat(employeelist.size()).isEqualTo(list.size());
	}

	@Test
	public void testGetEmployeeById() {
		Employee E = employeeRepository.findById(106).get();
		assertEquals(E.getName(), "naruto");
	}

	@Test
	public void testUpdateEmployee() {
		Employee E = employeeRepository.findById(101).get();
		E.setName("One Piece");
		E.setAge(4);
		Employee UpdatedEmployee = employeeRepository.save(E);
		assertThat(UpdatedEmployee.getName()).isEqualTo("One Piece");
	}

	@Test
	public void testDeleteEmployee() {
		Employee E = new Employee(111, "boruto", 3, 10000.00, "Navalpur");
		employeeRepository.save(E);
		employeeRepository.deleteById(111);
		Optional<Employee> deletedEmployee = employeeRepository.findById(111);
		assertFalse(deletedEmployee.isPresent());
	}

}
