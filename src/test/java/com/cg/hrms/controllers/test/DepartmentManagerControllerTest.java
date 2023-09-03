package com.cg.hrms.controllers.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrms.controllers.DepartmentManagerController;
import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.services.IDepartmentManagerService;

@ExtendWith(MockitoExtension.class)
public class DepartmentManagerControllerTest {

	@Mock
	private IDepartmentManagerService service;

	@InjectMocks
	private DepartmentManagerController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// 1. Add new department manager object in DB
	@Test
	void testAddDeptManager() {
		// Arrange
		DepartmentManager deptManager = new DepartmentManager();

		// Mock the service method
		when(service.addDeptManager(deptManager)).thenReturn(deptManager);

		// Act
		ResponseEntity<String> response = controller.addDeptManager(deptManager);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("New employee assigned as manager successfully", response.getBody());
		verify(service, times(1)).addDeptManager(deptManager);
	}

	// 2.search department manager by empNo and deptNo
	@Test
	void fetchDeptManagerById_ReturnsDepartmentManager_WhenFound() {
		// Arrange
		int empno = 123;
		String deptno = "D001";
		DepartmentManager departmentManager = new DepartmentManager();
		when(service.getDeptManagerById(empno, deptno)).thenReturn(departmentManager);

		// Act
		ResponseEntity<?> response = controller.fetchDeptManagerById(empno, deptno);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(departmentManager, response.getBody());
	}

	// 3.Fetch all department manager objects
	@Test
	void testGetAllDeptManagers() {
		// Prepare test data
		List<DepartmentManager> departmentManagers = new ArrayList<>();
		departmentManagers.add(new DepartmentManager());
		departmentManagers.add(new DepartmentManager());

		// Configure mock service
		when(service.getAllDeptManagers()).thenReturn(departmentManagers);

		// Perform the GET request
		ResponseEntity<List<DepartmentManager>> response = controller.getAllDeptManagers();

		// Verify the result
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(departmentManagers, response.getBody());

		// Verify that the service method was called
		verify(service, times(1)).getAllDeptManagers();
		verifyNoMoreInteractions(service);
	}

	// 4.Fetch all department manager objects by , deptno and from date
	@Test
	void getDeptManagersByDeptNoAndFromDate_ReturnsDepartmentManagers_Successfully() {
		// Arrange
		String deptNo = "123";
		Date fromDate = new Date(0, 0, 0);
		List<DepartmentManager> expectedDeptManagers = new ArrayList<>();
		// Add some department manager objects to the expectedDeptManagers list

		when(service.getDeptManagersByDeptNoAndFromDate(deptNo, fromDate)).thenReturn(expectedDeptManagers);

		// Act
		ResponseEntity<List<DepartmentManager>> response = controller.getDeptManagersByDeptNoAndFromDate(deptNo,
				fromDate);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedDeptManagers, response.getBody());
		verify(service, times(1)).getDeptManagersByDeptNoAndFromDate(deptNo, fromDate);
	}

	// 5.Fetch all deptmanager objects by , id and from date
	@Test
	void getDeptManagersByEmpNoAndFromDate_ReturnsDepartmentManager_Successfully() {
		// Arrange
		int empNo = 123;
		Date fromDate = new Date(0, 0, 0);
		Optional<DepartmentManager> expectedDeptManager = Optional.of(new DepartmentManager());

		when(service.getDeptManagersByEmpNoAndFromDate(empNo, fromDate)).thenReturn(expectedDeptManager);

		// Act
		ResponseEntity<Optional<DepartmentManager>> response = controller.getDeptManagersByEmpNoAndFromDate(empNo,
				fromDate);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedDeptManager, response.getBody());
		verify(service, times(1)).getDeptManagersByEmpNoAndFromDate(empNo, fromDate);
	}

	// 6.Fetch all deptmanager objects by , id ,deptno,and from date
	@Test
	void testGetDeptManagersByEmpNoDeptNoAndFromDate() {
		// Mocking the service method
		int empno = 1;
		String deptno = "HR";
		Date fromDate = Date.valueOf("2023-01-01");
		Optional<DepartmentManager> mockDeptManagers = Optional.of(new DepartmentManager());
		when(service.getDeptManagersByEmpNoDeptNoAndFromDate(empno, deptno, fromDate)).thenReturn(mockDeptManagers);

		// Calling the controller method
		ResponseEntity<?> response = controller.getDeptManagersByEmpNoDeptNoAndFromDate(empno, deptno, fromDate);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockDeptManagers, response.getBody());
	}

	// 7.Update deptmanager details for the given empno and deptno
	@Test
	void testUpdateDepartmentManagerByEmpNoAndDeptNo() {
		// Mocking the service method
		Employee empno = new Employee();
		Department deptno = new Department();
		DepartmentManager departmentManager = new DepartmentManager();

		// Calling the controller method
		ResponseEntity<String> response = controller.updateDepartmentManagerByEmpNoAndDeptNo(empno, deptno,
				departmentManager);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Department manager for empNo: " + empno + " and deptNo: " + deptno + " updated successfully.",
				response.getBody());

		// Verifying the service method was called with the correct arguments
		verify(service).updateDepartmentManagerByEmpNoAndDeptNo(empno, deptno, departmentManager);
	}

	// 8.Update deptmanager details for the given empno and fromdate
	@Test
	void testUpdateDepartmentManagerByEmpNoAndFromDate() {
		// Mocking the service method
		Employee empno = new Employee();
		Date fromdate = Date.valueOf("2023-01-01");
		DepartmentManager departmentManager = new DepartmentManager();

		// Calling the controller method
		ResponseEntity<String> response = controller.updateDepartmentManagerByEmpNoAndFromDate(empno, fromdate,
				departmentManager);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Department manager for empNo: " + empno + " and fromDate: " + fromdate + " updated successfully.",
				response.getBody());

		// Verifying the service method was called with the correct arguments
		verify(service).updateDepartmentManagerByEmpNoAndFromDate(empno, fromdate, departmentManager);
	}

	// 9.Update deptmanager details for the given deptno and fromdate
	@Test
	void testUpdateDeptManagerDetailsByDeptNoAndFromDate() {
		// Arrange
		Department deptno = new Department();
		Date fromdate = Date.valueOf("2023-07-09");
		DepartmentManager departmentManager = new DepartmentManager();

		// Act
		ResponseEntity<String> response = controller.updateDeptManagerDetailsByDeptNoAndFromDate(deptno, fromdate,
				departmentManager);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Department manager details updated successfully.", response.getBody());
	}

	// 10.Update deptmanager by empno, deptno, fromdate
	@Test
	void testUpdateDepartmentManager() {
		// Arrange
		Employee empno = new Employee();
		Department deptno = new Department();
		Date fromdate = Date.valueOf("2023-07-09");
		DepartmentManager departmentManager = new DepartmentManager();

		String expectedResponse = "Department manager details updated successfully.";
		when(service.updateDepartmentManagerByEmpNoDeptNoAndFromDate(empno, deptno, fromdate, departmentManager))
				.thenReturn(expectedResponse);

		// Act
		String response = controller.updateDepartmentManager(empno, deptno, fromdate, departmentManager);

		// Assert
		assertEquals(expectedResponse, response);
		verify(service, times(1)).updateDepartmentManagerByEmpNoDeptNoAndFromDate(empno, deptno, fromdate,
				departmentManager);
	}

	// 11.delete by empno and deptno
	@Test
	void testDeleteDepartmentManager() {
		// Arrange
		Employee empno = new Employee();
		Department deptno = new Department();

		// Act
		ResponseEntity<String> response = controller.deleteDepartmentManager(empno, deptno);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Department manager deleted successfully", response.getBody());
		verify(service, times(1)).deleteDepartmentManagerByEmpNoAndDeptNo(empno, deptno);
	}

	// 12. delete by empno and fromdate
	@Test
	void testDeleteDeptEmpByEmpNoAndFromDate() {
		// Arrange
		Employee empno = new Employee();
		Date fromdate = Date.valueOf("2023-07-09");
		DepartmentManager deletedDepartmentManager = new DepartmentManager();

		when(service.deleteDepartmentManagerByEmpNoAndFromDate(empno, fromdate)).thenReturn(deletedDepartmentManager);

		// Act
		ResponseEntity<DepartmentManager> response = controller.deleteDeptEmpByEmpNoAndFromDate(empno, fromdate);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deletedDepartmentManager, response.getBody());
		verify(service, times(1)).deleteDepartmentManagerByEmpNoAndFromDate(empno, fromdate);
	}

	// 13.Delete deptmanager by deptno and fromdate
	@Test
	void testDeleteDepartmentManagers() {
		// Arrange
		Department deptno = new Department();
		Date fromdate = Date.valueOf("2023-07-09");
		List<DepartmentManager> deletedDepartmentManagers = new ArrayList<>();

		when(service.deleteDepartmentManagersByDeptNoAndFromDate(deptno, fromdate))
				.thenReturn(deletedDepartmentManagers);

		// Act
		ResponseEntity<List<DepartmentManager>> response = controller.deleteDepartmentManagers(deptno, fromdate);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deletedDepartmentManagers, response.getBody());
		verify(service, times(1)).deleteDepartmentManagersByDeptNoAndFromDate(deptno, fromdate);
	}

	// 14.Delete deptmanager by empno, deptno and fromdate
	@Test
	void testDeleteDepartmentManagerByEmpId_NoAndFromDate() {
		// Arrange
		Employee empno = new Employee();
		Department deptno = new Department();
		Date fromdate = Date.valueOf("2023-07-09");
		DepartmentManager deletedDepartmentManager = new DepartmentManager();

		when(service.deleteDepartmentManagerByEmpNoDeptNoAndFromDate(empno, deptno, fromdate))
				.thenReturn(deletedDepartmentManager);

		// Act
		ResponseEntity<DepartmentManager> response = controller.deleteDepartmentManagerByEmpId_NoAndFromDate(empno,
				deptno, fromdate);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deletedDepartmentManager, response.getBody());
		verify(service, times(1)).deleteDepartmentManagerByEmpNoDeptNoAndFromDate(empno, deptno, fromdate);
	}
}
