package com.cg.hrms.controllers.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.*;
import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrms.controllers.EmployeeConsumerController;
import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.dto.EmployeeTitleDto;
import com.cg.hrms.dto.ManagerDto;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.services.IAdminConsumerService;
import com.cg.hrms.services.IDepartmentEmployeeService;
import com.cg.hrms.services.IDepartmentManagerService;
import com.cg.hrms.services.IEmployeeConsumerService;
import com.cg.hrms.services.IEmployeeService;
import com.cg.hrms.services.ISalaryService;
import com.cg.hrms.services.ITitleService;

public class EmployeeCosumerControllerTest {

	@InjectMocks
	private EmployeeConsumerController EmployeeController;

	@Mock
	private IAdminConsumerService AdminService;

	@Mock
	private IDepartmentEmployeeService DeptEmpService;

	@Mock
	private IDepartmentManagerService DeptMngService;

	@Mock
	private IEmployeeService EmpService;

	@Mock
	private IEmployeeConsumerService EmpConService;

	@Mock
	private ISalaryService SalService;

	@Mock
	private ITitleService TitService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}



	// Update last name for the given empno employee.
	@Test
	void testUpdateLastName() {
		// Create sample data
		int empNo = 1;
		String lastName = "Kiran";

		Employee updatedEmployee = new Employee();
		updatedEmployee.setEmpNo(empNo);
		updatedEmployee.setLastName(lastName);

		// Mock the behavior of the employeeService.updateLastName() method
		when(EmpConService.updateLastName(empNo, lastName)).thenReturn(updatedEmployee);

		// Call the method to be tested
		ResponseEntity<?> responseEntity = EmployeeController.updateLastName(empNo, lastName);

		// Verify the result
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(updatedEmployee, responseEntity.getBody());

		// Verify that the employeeService.updateLastName() method was called with the
		// correct arguments
		verify(EmpConService, times(1)).updateLastName(empNo, lastName);
		verifyNoMoreInteractions(EmpConService);
	}

	// Get details of employees [id, name, dob, and department name] who are
	// manager.
	@Test
	void testGetManager() {
		// Create sample data
		List<ManagerDto> managers = new ArrayList<>();
		managers.add(new ManagerDto(1, "John Doe", Date.valueOf("1980-01-01"), "IT"));
		managers.add(new ManagerDto(2, "Jane Smith", Date.valueOf("1985-05-12"), "HR"));

		// Mock the behavior of the employeeConsumerService.getManagers() method
		when(EmpConService.getManagers()).thenReturn(managers);

		// Call the method to be tested
		ResponseEntity<?> responseEntity = EmployeeController.getManager();

		// Verify the result
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(managers, responseEntity.getBody());

		// Verify that the employeeConsumerService.getManagers() method was called
		verify(EmpConService, times(1)).getManagers();
		verifyNoMoreInteractions(EmpConService);
	}

	// Check details of those employee who are working as specific
	@Test
	void testGetEmployeesByTitle() {
		String title = "manager";
		List<EmployeeTitleDto> employees = new ArrayList<>();
		employees.add(new EmployeeTitleDto(10001, "John Doe", null, null, "Manager"));
		employees.add(new EmployeeTitleDto(10002, "Jane Smith", null, null, "Staff"));

		when(EmpConService.getEmployeesByTitle(title)).thenReturn(employees);

		ResponseEntity<?> response = EmployeeController.getEmployeesByTitle(title);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employees, response.getBody());
		verify(EmpConService, times(1)).getEmployeesByTitle(title);
	}

	// Get list of employees working in specific department in specific year.
	@Test
	public void testGetEmployeesInDepartmentWorkingInYear() {
		String deptNo = "doIT";
		Date fromDate = Date.valueOf("1995-01-11");

		List<EmployeeDto> employees = new ArrayList<>();
		employees.add(
				new EmployeeDto(1, Date.valueOf("1985-03-11"), "Jaya", "Prakash", "Male", Date.valueOf("1985-03-11")));
		employees.add(new EmployeeDto(2, Date.valueOf("1985-03-11"), "Abhishek", "kumar", "Male",
				Date.valueOf("1985-03-11")));

		when(EmpConService.getEmployeesByDepartmentAndFromDate(deptNo, fromDate)).thenReturn(employees);

		ResponseEntity<List<EmployeeDto>> responseEntity = EmployeeController
				.getEmployeesInDepartmentWorkingInYear(deptNo, fromDate);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(employees, responseEntity.getBody());

		verify(EmpConService, times(1)).getEmployeesByDepartmentAndFromDate(deptNo, fromDate);
	}

	

}
