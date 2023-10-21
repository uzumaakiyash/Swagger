package com.pack.ppkTest.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@Id
	private Integer id;
	private String name;
	private Integer age;
	private Double salary;
	private String designation;

}
