package com.cg.hrms.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.services.IEmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public/api/v1/employees")
@Validated
public class EmployeeController {

	@Autowired
	private IEmployeeService service;

	// Fetch all employees.
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return new ResponseEntity<>(service.getAllEmployees(), HttpStatus.OK);
	}

	// Add new employee object in DB
	@PostMapping
	public ResponseEntity<String> addEmployee(@Valid @RequestBody Employee employee) {
		service.addEmployee(employee);
		return new ResponseEntity<>("New employee added successfully", HttpStatus.OK);
	}

	// Search Employee by Last Name
	@GetMapping(value = "/lastname/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> searchByLastName(@PathVariable String lastname) {
		return new ResponseEntity<>(service.searchByLastName(lastname), HttpStatus.OK);
	}

	// Search employees by empno
	@GetMapping("/{empno}")
	public ResponseEntity<Employee> getEmpById(@PathVariable Integer empno) {
		return new ResponseEntity<>(service.getEmpById(empno), HttpStatus.OK);
	}

	// Search employees by First Name
	@GetMapping("/firstname/{firstname}")
	public ResponseEntity<List<Employee>> searchByFirstName(@PathVariable String firstname) {
		return new ResponseEntity<>(service.searchByFirstName(firstname), HttpStatus.OK);
	}

	// Update Last Name of an employee given empno
	@PutMapping("/lastname/{empno}")
	public ResponseEntity<Employee> updateLastName(@Valid @PathVariable Integer empno, @RequestBody Employee employee) {
		return new ResponseEntity<>(service.updateLastName(empno, employee.getLastName()), HttpStatus.OK);
	}

	// Update First Name of an employee given empno
	@PutMapping("/firstname/{empno}")
	public ResponseEntity<Employee> updateFirstName(@Valid @PathVariable Integer empno, @RequestBody Employee employee) {
		return new ResponseEntity<>(service.updateFirstName(empno, employee.getFirstName()), HttpStatus.OK);
	}

	// Update hiredate for given empno
	@PutMapping("/hiredate/{empno}")
	public ResponseEntity<Employee> updatehireDate(@Valid @PathVariable Integer empno, @RequestBody Employee employee) {
		return new ResponseEntity<>(service.updatehireDate(empno, employee.getHireDate()), HttpStatus.OK);
	}

	// Update birthdate for given empmo
	@PutMapping("/birthdate/{empno}")
	public ResponseEntity<Employee> updateBirthDate(@Valid @PathVariable Integer empno, @RequestBody Employee employee) {
		return new ResponseEntity<>(service.updateBirthDate(empno, employee.getBirthDate()), HttpStatus.OK);
	}

	// Search employees by gender
	@GetMapping("/gender/{gender}")
	public ResponseEntity<List<Employee>> searchByGender(@PathVariable String gender) {
		return new ResponseEntity<>(service.searchByGender(gender), HttpStatus.OK);
	}

	// Search employees by hire date
	@GetMapping("hiredate/{hiredate}")
	public ResponseEntity<List<Employee>> getEmpByHireDate(@PathVariable Date hiredate) {
		return new ResponseEntity<>(service.getEmpByHireDate(hiredate), HttpStatus.OK);
	}

	// Search employees by birth date
	@GetMapping("/birthdate/{birthdate}")
	public ResponseEntity<List<Employee>> searchByBirthDate(@PathVariable Date birthdate) {
		return new ResponseEntity<>(service.searchByBirthDate(birthdate), HttpStatus.OK);
	}

}
