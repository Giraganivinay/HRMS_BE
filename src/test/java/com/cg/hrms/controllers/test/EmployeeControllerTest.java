package com.cg.hrms.controllers.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrms.controllers.EmployeeController;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.services.IEmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class EmployeeControllerTest {

	@Mock
	private IEmployeeService service;

	@InjectMocks
	private EmployeeController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Fetch all employees.
	@Test
	@DisplayName("Test Get All Employees")
	void testGetAllEmployees() {
		// Arrange
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());
		when(service.getAllEmployees()).thenReturn(employees);

		// Act
		ResponseEntity<List<Employee>> response = controller.getAllEmployees();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employees, response.getBody());
		verify(service).getAllEmployees();
	}

	// Add new employee object in DB
	@Test
	void testAddEmployee() {
		// Arrange
		Employee employee = new Employee(); // Create a mock employee object

		// Act
		ResponseEntity<String> response = controller.addEmployee(employee);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("New employee added successfully", response.getBody());
		verify(service).addEmployee(employee);
	}

	// Search Employee by Last Name
	@Test
	@DisplayName("Test Search Employees by Last Name")
	void testSearchByLastName() {
		// Arrange
		String lastName = "Doe";
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());
		when(service.searchByLastName(lastName)).thenReturn(employees);

		// Act
		ResponseEntity<List<Employee>> response = controller.searchByLastName(lastName);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employees, response.getBody());
		verify(service).searchByLastName(lastName);
	}

	// Search employees by empno
	@Test
	@DisplayName("Test Get Employee by ID")
	void testGetEmpById() {
		// Arrange
		Integer empId = 1;
		Employee employee = new Employee();
		when(service.getEmpById(empId)).thenReturn(employee);

		// Act
		ResponseEntity<Employee> response = controller.getEmpById(empId);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employee, response.getBody());
		verify(service).getEmpById(empId);
	}

	// Search employees by First Name
	@Test
	@DisplayName("Test Search Employees by First Name")
	void testSearchByFirstName() {
		// Arrange
		String firstName = "John";
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());
		when(service.searchByFirstName(firstName)).thenReturn(employees);

		// Act
		ResponseEntity<List<Employee>> response = controller.searchByFirstName(firstName);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employees, response.getBody());
		verify(service).searchByFirstName(firstName);
	}

	// Update Last Name of an employee given empno
	@Test
	@DisplayName("Test Update Last Name")
	void testUpdateLastName() {
		// Arrange
		Integer empNo = 1;
		String newLastName = "Smith";
		Employee employee = new Employee();
		employee.setLastName(newLastName);

		// Mock the service method
		when(service.updateLastName(empNo, employee.getLastName())).thenReturn(employee);

		// Act
		ResponseEntity<Employee> response = controller.updateLastName(empNo, employee);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employee, response.getBody());
		verify(service).updateLastName(empNo, employee.getLastName());
	}

	// Update First Name of an employee given empno
	@Test
	@DisplayName("Test Update First Name")
	void testUpdateFirstName() {
		// Arrange
		Integer empNo = 1;
		String newFirstName = "John";
		Employee employee = new Employee();
		employee.setFirstName(newFirstName);

		// Mock the service method
		when(service.updateFirstName(empNo, employee.getFirstName())).thenReturn(employee);

		// Act
		ResponseEntity<Employee> response = controller.updateFirstName(empNo, employee);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employee, response.getBody());
		verify(service).updateFirstName(empNo, employee.getFirstName());
	}

	// Update hiredate for given empno
	@Test
	@DisplayName("Test Update Hire Date")
	void testUpdateHireDate() {
		// Arrange
		Integer empNo = 1;
		Date newHireDate = Date.valueOf(LocalDate.of(2023, 7, 1));
		Employee expectedEmployee = new Employee();
		expectedEmployee.setEmpNo(empNo);
		expectedEmployee.setHireDate(newHireDate);
		// Set other properties of the expectedEmployee object as per your requirement

		// Mock the service method
		when(service.updatehireDate(empNo, newHireDate)).thenReturn(expectedEmployee);

		// Act
		ResponseEntity<Employee> response = controller.updatehireDate(empNo, expectedEmployee);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedEmployee, response.getBody());
		verify(service).updatehireDate(empNo, newHireDate);
	}

	// Update birthdate for given empmo
	@Test
	@DisplayName("Test Update Birth Date")
	void testUpdateBirthDate() {
		// Arrange
		Integer empNo = 1;
		LocalDate newBirthDate = LocalDate.of(1990, 5, 10);
		Employee employee = new Employee();
		employee.setBirthDate(Date.valueOf(newBirthDate)); // Convert LocalDate to java.sql.Date

		// Mock the service method
		when(service.updateBirthDate(empNo, employee.getBirthDate())).thenReturn(employee);

		// Act
		ResponseEntity<Employee> response = controller.updateBirthDate(empNo, employee);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employee, response.getBody());
		verify(service).updateBirthDate(empNo, employee.getBirthDate());
	}

	// Search employees by gender
	@Test
	@DisplayName("Test Search Employees by Gender")
	void testSearchByGender() {
		// Arrange
		String gender = "Male";
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());
		when(service.searchByGender(gender)).thenReturn(employees);

		// Act
		ResponseEntity<List<Employee>> response = controller.searchByGender(gender);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employees, response.getBody());
		verify(service).searchByGender(gender);
	}

	// Search employees by hire date
	@Test
	@DisplayName("Test Get Employees by Hire Date")
	void testGetEmpByHireDate() {
		// Arrange
		Date hireDate = Date.valueOf(LocalDate.of(2023, 7, 1));
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());
		when(service.getEmpByHireDate(hireDate)).thenReturn(employees);

		// Act
		ResponseEntity<List<Employee>> response = controller.getEmpByHireDate(hireDate);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employees, response.getBody());
		verify(service).getEmpByHireDate(hireDate);
	}

	// Search employees by birth date
	@Test
	@DisplayName("Test Search Employees by Birth Date")
	void testSearchByBirthDate() {
		// Arrange
		Date birthDate = Date.valueOf(LocalDate.of(1990, 5, 10));
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());
		when(service.searchByBirthDate(birthDate)).thenReturn(employees);

		// Act
		ResponseEntity<List<Employee>> response = controller.searchByBirthDate(birthDate);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employees, response.getBody());
		verify(service).searchByBirthDate(birthDate);
	}

}
