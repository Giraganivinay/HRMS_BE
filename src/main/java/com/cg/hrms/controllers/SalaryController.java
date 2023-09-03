package com.cg.hrms.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Salary;
import com.cg.hrms.services.ISalaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public/api/v1/salaries")
@Validated
public class SalaryController {

	@Autowired
	ISalaryService service;

	// Add new salary object in DB
	@PostMapping
	public ResponseEntity<String> addNewSalary(@Valid @RequestBody Salary s) {
		return new ResponseEntity<>(service.addSalary(s), HttpStatus.CREATED);
	}

	// Search salaries by from date, empno
	@GetMapping("/empno/{empNo}/fromdate/{fromDate}")
	public ResponseEntity<Salary> fetchSalaryById(@PathVariable int empNo, @PathVariable Date fromDate) {
		return new ResponseEntity<>(service.getSalaryById(empNo, fromDate), HttpStatus.OK);
	}

	// Fetch all salary objects
	@GetMapping
	public ResponseEntity<List<Salary>> fetchAllSalaries() {
		return new ResponseEntity<>(service.getAllSalaries(), HttpStatus.OK);
	}

	// Fetch all salary objects by from date
	@GetMapping("/fromdate/{fromDate}")
	public ResponseEntity<List<Salary>> fetchAllSalaryAfterDate(@PathVariable Date fromDate) {
		return new ResponseEntity<>(service.getAllSalariesFromDate(fromDate), HttpStatus.OK);
	}

	// Fetch all salary objects by empno
	@GetMapping("/empno/{empNo}")
	public ResponseEntity<List<Salary>> fetchAllSalaryByEmpNo(@PathVariable int empNo) {
		return new ResponseEntity<>(service.getAllSalariesByEmpNo(empNo), HttpStatus.OK);
	}

	// Fetch all salary objects by salary
	@GetMapping("/minsalary/{minSal}/maxsalary/{maxSal}")
	public ResponseEntity<List<Salary>> fetchAllSalaryBySalary(@PathVariable int minSal, @PathVariable int maxSal) {
		return new ResponseEntity<>(service.getAllSalariesBetween(minSal, maxSal), HttpStatus.OK);
	}

	// Update salary details for the given fromdate
	@PutMapping("/fromdate/{fromDate}")
	public ResponseEntity<List<Salary>> updateSalaryByFromDate(@Valid @PathVariable Date fromDate, @RequestBody Salary salary) {
		return new ResponseEntity<>(service.updateSalaryByFromDate(fromDate, salary), HttpStatus.OK);
	}

	// Update salary details for the given empid
	@PutMapping("/empno/{empNo}")
	public ResponseEntity<List<Salary>> updateSalaryByEmpId(@Valid @PathVariable int empNo, @RequestBody Salary salary){
		return new ResponseEntity<>(service.updateSalaryByEmpNo(empNo, salary), HttpStatus.OK);
	}
	
	// Update salary by empno, fromdate
	@PutMapping("/empno/{empNo}/fromdate/{fromDate}")
	public ResponseEntity<String> updateSalaryByEmpNoFromDate(@Valid  @PathVariable int empNo, @PathVariable Date fromDate,
			@RequestBody Salary salary) {
		return new ResponseEntity<>(service.updateSalaryByEmpNoAndFromDate(empNo, fromDate, salary), HttpStatus.OK);
	}

	// Delete salary by empno and from date
	@DeleteMapping("/empno/{empNo}/fromdate/{fromDate}")
	public ResponseEntity<String> deleteSalaryByEmpnoAndFromdate(@Valid @PathVariable int empNo, @PathVariable Date fromDate) {
		return new ResponseEntity<>(service.deleteSalryByEmpNoAndFromDate(empNo, fromDate), HttpStatus.OK);
	}

	// Delete salary by empno
	@DeleteMapping("/empno/{empNo}")
	public ResponseEntity<List<Salary>> deleteSalaryByEmpNo(@PathVariable Employee empNo) {
		return new ResponseEntity<>(service.deleteSalaryByEmpNo(empNo), HttpStatus.OK);
	}

	// Delete salary by fromdate
	@DeleteMapping("/fromdate/{fromDate}")
	public ResponseEntity<List<Salary>> deleteSalaryByFromDate(@PathVariable Date fromDate) {
		return new ResponseEntity<>(service.deleteSalaryByFromDate(fromDate), HttpStatus.OK);
	}
}
