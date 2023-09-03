package com.cg.hrms.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.services.EmployeeServiceImpl;

@SpringBootTest
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeServiceImpl service;

	@Mock
	private IEmployeeRepo repo;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void addEmployeeSaveEmployee() {
		// Arrange
		Employee employee = new Employee(1, Date.valueOf("2000-01-01"), "John", "Doe", "M", Date.valueOf("2021-01-01"));

		// Act
		service.addEmployee(employee);

		// Assert
		verify(repo).save(employee);
	}

	@Test
	public void getAllEmployees_shouldReturnListOfEmployees() {
		// Arrange
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, Date.valueOf("2000-01-01"), "John", "Doe", "M", Date.valueOf("2021-01-01")));
		employees.add(new Employee(2, Date.valueOf("1995-06-15"), "Jane", "Smith", "F", Date.valueOf("2019-03-10")));
		when(repo.findAll()).thenReturn(employees);

		// Act
		List<Employee> result = service.getAllEmployees();

		// Assert
		assertEquals(2, result.size());
		assertEquals("John", result.get(0).getFirstName());
		assertEquals("Jane", result.get(1).getFirstName());
	}

	@Test
	public void searchByBirthDate_shouldReturnMatchingEmployees() {
		// Arrange
		Date searchDate = Date.valueOf("2000-01-01");
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, Date.valueOf("2000-01-01"), "John", "Doe", "M", Date.valueOf("2021-01-01")));
		employees.add(new Employee(2, Date.valueOf("2000-01-01"), "Jane", "Smith", "F", Date.valueOf("2019-03-10")));
		when(repo.findByBirthDate(searchDate)).thenReturn(employees);

		// Act
		List<Employee> result = service.searchByBirthDate(searchDate);

		// Assert
		assertEquals(2, result.size());
		assertEquals("John", result.get(0).getFirstName());
		assertEquals("Jane", result.get(1).getFirstName());
	}

	@Test
	public void updateLastName_shouldUpdateAndReturnEmployee() {
		// Arrange
		int empNo = 1;
		String newLastName = "Doe";
		Employee existingEmployee = new Employee(empNo, null, null, "Smith", null, null);
		when(repo.findById(empNo)).thenReturn(Optional.of(existingEmployee));
		Employee updatedEmployee = new Employee(empNo, null, null, newLastName, null, null);
		when(repo.save(existingEmployee)).thenReturn(updatedEmployee);

		// Act
		Employee result = service.updateLastName(empNo, newLastName);

		// Assert
		assertEquals(newLastName, result.getLastName());
		verify(repo).findById(empNo);
		verify(repo).save(existingEmployee);
	}

	@Test
	public void updateHireDate_shouldUpdateAndReturnEmployee() {
		// Arrange
		int empNo = 1;
		Date newHireDate = Date.valueOf("2023-07-01");
		Employee existingEmployee = new Employee(empNo, null, null, null, null, null);
		when(repo.findById(empNo)).thenReturn(Optional.of(existingEmployee));
		Employee updatedEmployee = new Employee(empNo, null, null, null, null, newHireDate);
		when(repo.save(existingEmployee)).thenReturn(updatedEmployee);

		// Act
		Employee result = service.updatehireDate(empNo, newHireDate);

		// Assert
		assertEquals(newHireDate, result.getHireDate());
		verify(repo).findById(empNo);
		verify(repo).save(existingEmployee);
	}

	@Test
	public void searchByGender_shouldReturnMatchingEmployees() {
		// Arrange
		String searchGender = "M";
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, null, null, null, "M", null));
		employees.add(new Employee(2, null, null, null, "M", null));
		when(repo.findTop800ByGender(searchGender)).thenReturn(employees);

		// Act
		List<Employee> result = service.searchByGender(searchGender);

		// Assert
		assertEquals(2, result.size());
		assertEquals("M", result.get(0).getGender());
		assertEquals("M", result.get(1).getGender());
	}

	@Test
	public void getEmpByHireDate_shouldReturnMatchingEmployees() {
		// Arrange
		Date searchHireDate = Date.valueOf("2023-01-01");
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, null, null, null, null, searchHireDate));
		employees.add(new Employee(2, null, null, null, null, searchHireDate));
		when(repo.findByHireDate(searchHireDate)).thenReturn(employees);

		// Act
		List<Employee> result = service.getEmpByHireDate(searchHireDate);

		// Assert
		assertEquals(2, result.size());
		assertEquals(searchHireDate, result.get(0).getHireDate());
		assertEquals(searchHireDate, result.get(1).getHireDate());
	}
}
