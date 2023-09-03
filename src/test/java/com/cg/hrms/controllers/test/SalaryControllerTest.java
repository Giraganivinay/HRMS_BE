package com.cg.hrms.controllers.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.cg.hrms.controllers.SalaryController;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Salary;
import com.cg.hrms.services.ISalaryService;

import java.sql.Date; // Import java.sql.Date instead of java.util.Date
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class SalaryControllerTest {

	@Mock
	private ISalaryService service;

	@InjectMocks
	private SalaryController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Add new salary object in DB
	@Test
	void testAddNewSalary() {
		// Mocking the request data
		Salary salary = new Salary(); // Assuming a Salary model class

		// Mocking the service response
		String successMessage = "Salary added successfully";

		when(service.addSalary(salary)).thenReturn(successMessage);

		// Calling the controller method
		ResponseEntity<?> response = controller.addNewSalary(salary);

		// Verifying the service method was called with the correct arguments
		verify(service, times(1)).addSalary(salary);

		// Asserting the response status and body
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(successMessage, response.getBody());
	}

	// Search salaries by from date, empno
	@Test
	void testFetchSalaryById() {
		// Mocking the service response
		int empNo = 123;
		Date fromDate = new Date(0, 0, 0); // Use java.sql.Date instead of java.util.Date
		Salary salary = new Salary(); // Assuming a Salary model class

		when(service.getSalaryById(empNo, fromDate)).thenReturn(salary);

		// Calling the controller method
		ResponseEntity<?> response = controller.fetchSalaryById(empNo, fromDate);

		// Verifying the service method was called with the correct arguments
		verify(service, times(1)).getSalaryById(empNo, fromDate);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(salary, response.getBody());
	}

	// Fetch all salary objects

	@Test
	void testFetchAllSalaries() {
		// Mocking the service response
		List<Salary> salaries = new ArrayList<>(); // Assuming a list of Salary objects
		salaries.add(new Salary());
		salaries.add(new Salary());

		when(service.getAllSalaries()).thenReturn(salaries);

		// Calling the controller method
		ResponseEntity<?> response = controller.fetchAllSalaries();

		// Verifying the service method was called
		verify(service, times(1)).getAllSalaries();

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(salaries, response.getBody());
	}

	// Fetch all salary objects by from date
	@Test
	void testFetchAllSalaryAfterDate() {
		// Mocking the service response
		Date fromDate = new Date(2023, 6, 1); // Assuming a specific from date
		List<Salary> salaries = new ArrayList<>(); // Assuming a list of Salary objects
		salaries.add(new Salary());
		salaries.add(new Salary());

		when(service.getAllSalariesFromDate(fromDate)).thenReturn(salaries);

		// Calling the controller method
		ResponseEntity<?> response = controller.fetchAllSalaryAfterDate(fromDate);

		// Verifying the service method was called
		verify(service, times(1)).getAllSalariesFromDate(fromDate);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(salaries, response.getBody());
	}

	// Fetch all salary objects by empno
	@Test
	void testFetchAllSalaryByEmpNo() {
		// Mocking the service response
		int empNo = 123; // Assuming a specific employee number
		List<Salary> salaries = new ArrayList<>(); // Assuming a list of Salary objects
		salaries.add(new Salary());
		salaries.add(new Salary());

		when(service.getAllSalariesByEmpNo(empNo)).thenReturn(salaries);

		// Calling the controller method
		ResponseEntity<?> response = controller.fetchAllSalaryByEmpNo(empNo);

		// Verifying the service method was called
		verify(service, times(1)).getAllSalariesByEmpNo(empNo);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(salaries, response.getBody());
	}

	// Fetch all salary objects by salary
	@Test
	void testFetchAllSalaryBySalary() {
		// Mocking the service response
		int minSal = 1000; // Assuming a minimum salary
		int maxSal = 5000; // Assuming a maximum salary
		List<Salary> salaries = new ArrayList<>(); // Assuming a list of Salary objects
		salaries.add(new Salary());
		salaries.add(new Salary());

		when(service.getAllSalariesBetween(minSal, maxSal)).thenReturn(salaries);

		// Calling the controller method
		ResponseEntity<?> response = controller.fetchAllSalaryBySalary(minSal, maxSal);

		// Verifying the service method was called
		verify(service, times(1)).getAllSalariesBetween(minSal, maxSal);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(salaries, response.getBody());
	}

	// Update salary details for the given fromdate
	@Test
	void testUpdateSalaryByFromDate() {
		// Mocking the request data
		Date fromDate = new Date(2023, 6, 1); // Assuming a specific from date
		Salary salary = new Salary(); // Assuming a Salary model class

		// Mocking the service response
		Salary updatedSalary = new Salary(); // Assuming an updated Salary object
		List<Salary> updatedSalaries = Collections.singletonList(updatedSalary);

		when(service.updateSalaryByFromDate(fromDate, salary)).thenReturn(updatedSalaries);

		// Calling the controller method
		ResponseEntity<?> response = controller.updateSalaryByFromDate(fromDate, salary);

		// Verifying the service method was called with the correct arguments
		verify(service, times(1)).updateSalaryByFromDate(fromDate, salary);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedSalaries, response.getBody());
	}

	// Update salary details for the given empid
	@Test
	void testUpdateSalaryByEmpId() {
		// Mocking the request data
		int empNo = 123; // Assuming a specific employee number
		Salary salary = new Salary(); // Assuming a Salary model class

		// Mocking the service response
		Salary updatedSalary = new Salary(); // Assuming an updated Salary object
		List<Salary> updatedSalaries = Collections.singletonList(updatedSalary);

		when(service.updateSalaryByEmpNo(empNo, salary)).thenReturn(updatedSalaries);

		// Calling the controller method
		ResponseEntity<?> response = controller.updateSalaryByEmpId(empNo, salary);

		// Verifying the service method was called with the correct arguments
		verify(service, times(1)).updateSalaryByEmpNo(empNo, salary);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedSalaries, response.getBody());
	}

	// Update salary by empno, fromdate
	@Test
	void testUpdateSalaryByEmpNoFromDate() {
		// Mocking the request data
		int empNo = 123; // Assuming a specific employee number
		Date fromDate = new Date(2023, 6, 1); // Assuming a specific from date
		Salary salary = new Salary(); // Assuming a Salary model class

		// Mocking the service response
		String successMessage = "Salary updated successfully";

		when(service.updateSalaryByEmpNoAndFromDate(empNo, fromDate, salary)).thenReturn(successMessage);

		// Calling the controller method
		ResponseEntity<?> response = controller.updateSalaryByEmpNoFromDate(empNo, fromDate, salary);

		// Verifying the service method was called with the correct arguments
		verify(service, times(1)).updateSalaryByEmpNoAndFromDate(empNo, fromDate, salary);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(successMessage, response.getBody());
	}

	// Delete salary by empno and from date
	@Test
	void testDeleteSalaryByEmpnoAndFromdate() {
		// Mocking the request data
		int empNo = 123; // Assuming a specific employee number
		Date fromDate = new Date(2023, 6, 1); // Assuming a specific from date

		// Mocking the service response
		String successMessage = "Salary deleted successfully";

		when(service.deleteSalryByEmpNoAndFromDate(empNo, fromDate)).thenReturn(successMessage);

		// Calling the controller method
		ResponseEntity<?> response = controller.deleteSalaryByEmpnoAndFromdate(empNo, fromDate);

		// Verifying the service method was called with the correct arguments
		verify(service, times(1)).deleteSalryByEmpNoAndFromDate(empNo, fromDate);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(successMessage, response.getBody());
	}

	// Delete salary by empno
	@Test
	void testDeleteSalaryByEmpNo() {
		// Mocking the request data
		Employee employee = new Employee();
		employee.setEmpNo(123); // Assuming a specific employee number

		// Mocking the service response
		List<Salary> deletedSalaries = new ArrayList<>(); // Assuming a list of deleted Salary objects

		when(service.deleteSalaryByEmpNo(employee)).thenReturn(deletedSalaries);

		// Calling the controller method
		ResponseEntity<?> response = controller.deleteSalaryByEmpNo(employee);

		// Verifying the service method was called with the correct arguments
		verify(service, times(1)).deleteSalaryByEmpNo(employee);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deletedSalaries, response.getBody());
	}

	// Delete salary by fromdate
	@Test
	void testDeleteSalaryByFromDate() {
		// Mocking the request data
		Date fromDate = new Date(2023, 6, 1); // Assuming a specific from date

		// Mocking the service response
		List<Salary> deletedSalaries = new ArrayList<>(); // Assuming a list of deleted Salary objects

		when(service.deleteSalaryByFromDate(fromDate)).thenReturn(deletedSalaries);

		// Calling the controller method
		ResponseEntity<?> response = controller.deleteSalaryByFromDate(fromDate);

		// Verifying the service method was called with the correct arguments
		verify(service, times(1)).deleteSalaryByFromDate(fromDate);

		// Asserting the response status and body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deletedSalaries, response.getBody());
	}

}
