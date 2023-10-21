package com.pack.ppkTest.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pack.ppkTest.Model.Employee;
import com.pack.ppkTest.Repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee insertEmployee(Employee employee) {
		log.info("Inside insertemployee method");
		return employeeRepository.save(employee);
	}

	@Override
	public Optional<Employee> findById(Integer id) {
		log.info("Inside findById method");
		return employeeRepository.findById(id);
	}

	@Override
	public Iterable<Employee> fetchAllEmployee() {
		log.info("Inside fetchAllEmployee method");
		return employeeRepository.findAll();
	}

	@Override
	public void deleteEmployeeById(Integer id) {
		log.info("Inside deleteEmployeeById method");
		employeeRepository.deleteById(id);

	}

	@Override
	public List<Employee> findByName(String name) {
		log.info("Inside findByName method");
		return employeeRepository.findByName(name);
	}

}
