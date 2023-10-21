package com.pack.ppkTest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pack.ppkTest.Model.Employee;
import com.pack.ppkTest.Repository.EmployeeRepository;
import com.pack.ppkTest.Service.EmployeeService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/Employee")
@Log4j2
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;

	/**
	 * This method is used to add new Employee
	 * 
	 * @param Employee
	 * @return ResponseEntity
	 */
	@PostMapping("/addEmployee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {// gives the hppt response
		log.info("Inside createEmployee method");
		try {
			Employee emp1 = employeeService.insertEmployee(employee);
			return new ResponseEntity<>(emp1, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method is used to find Employee by id
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/getEmployeeById/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id) {
		log.info("Inside creatEmployee method");
		Optional<Employee> empData = employeeService.findById(id);
		if (empData.isPresent()) {
			return new ResponseEntity<>(empData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is used to fetch all the employee in database
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/allEmployee")
	public ResponseEntity<List<Employee>> getAllEmployee() {
		log.info("Inside getAllEmployee method");
		try {
			List<Employee> employee = new ArrayList<Employee>();
			employeeService.fetchAllEmployee().forEach(employee::add);
			if (employee.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(employee, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method is used to update the employee information
	 * 
	 * @param id
	 * @param pet
	 * @return ResponseEntity
	 */
	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
		log.info("Inside updateEmployee method");
		Optional<Employee> employeeData = employeeService.findById(id);

		if (employeeData.isPresent()) {
			Employee emp1 = employeeData.get();
			emp1.setName(employee.getName());
			emp1.setAge(employee.getAge());
			emp1.setSalary(employee.getSalary());
			emp1.setDesignation(employee.getDesignation());
			return new ResponseEntity<>(employeeService.insertEmployee(emp1), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is used to delete employee by id
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") Integer id) {
		log.info("Inside deleteEmployee method");
		try {
			employeeService.deleteEmployeeById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method is used to find Employee by name
	 * 
	 * @param name
	 * @return ResponseEntity
	 */
	@GetMapping("/getEmployeeByName/Name")
	public ResponseEntity<List<Employee>> getEmployeeByName(@RequestParam("name") String name) {
		log.info("Inside getEmployeeByName method");
		List<Employee> employeeData = employeeService.findByName(name);

		if (employeeData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(employeeData, HttpStatus.OK);
	}

}
