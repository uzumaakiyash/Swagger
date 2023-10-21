package com.pack.ppkTest.Service;

import java.util.List;
import java.util.Optional;

import com.pack.ppkTest.Model.Employee;

public interface EmployeeService {

	Employee insertEmployee(Employee employee);

	Optional<Employee> findById(Integer id);

	Iterable<Employee> fetchAllEmployee();

	void deleteEmployeeById(Integer id);

	List<Employee> findByName(String name);

}
