package com.cg.hrms.controllers.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrms.controllers.DepartmentController;
import com.cg.hrms.entities.Department;
import com.cg.hrms.services.IDepartmentService;

class DepartmentControllerTest {

	@Mock
	private IDepartmentService departmentService;

	@InjectMocks
	private DepartmentController departmentController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

//	1 ------- ADD----
	@Test
	public void testAddNewDepartment() {
		Department department = new Department();
		department.setDeptName("Product Analyst");

		Department savedDepartment = new Department();
		savedDepartment.setDeptName("Product Analyst");

		when(departmentService.addDepartment(department)).thenReturn(savedDepartment);

		ResponseEntity<?> response = departmentController.addNewDepartment(department);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(savedDepartment, response.getBody());
		verify(departmentService, times(1)).addDepartment(department);
	}

//	2------FETCH ALL-----------
	@Test
	public void testGetAllDepartments() {
		Department department1 = new Department();
		department1.setDeptName("Department 1");

		Department department2 = new Department();
		department2.setDeptName("Department 2");

		List<Department> departments = Arrays.asList(department1, department2);

		when(departmentService.getAllDepartments()).thenReturn(departments);

		ResponseEntity<List<Department>> response = departmentController.getAllDepartments();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(departments, response.getBody());
		verify(departmentService, times(1)).getAllDepartments();
	}

//	3------------FETCH BY DEPT No---------
	@Test
	public void testGetDepartmentByDeptNo() {
		String deptNo = "A001";

		Department department = new Department();
		department.setDeptNo(deptNo);
		department.setDeptName("Test Department");

		when(departmentService.getDepartmentByDeptNo(deptNo)).thenReturn(department);

		ResponseEntity<Department> response = departmentController.getDepartmentByDeptNo(deptNo);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(department, response.getBody());
		verify(departmentService, times(1)).getDepartmentByDeptNo(deptNo);
	}

//	4-------FETCH BY DEPT NAME-------
	@Test
	public void testGetDeptByName() {
		String departmentName = "Product Sales";

		Department department = new Department();
		department.setDeptName(departmentName);

		when(departmentService.getDepartmentByName(departmentName)).thenReturn(department);

		ResponseEntity<Department> response = departmentController.getDeptByName(departmentName);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(department, response.getBody());
		verify(departmentService, times(1)).getDepartmentByName(departmentName);
	}

//	5--------UPDATE BY DEPT No-------------
	@Test
	public void testUpdateDepartment() {
		String departmentNo = "A001";

		Department department = new Department();
		department.setDeptName("Updated Department");

		Department updatedDepartment = new Department();
		updatedDepartment.setDeptNo(departmentNo);
		updatedDepartment.setDeptName("Updated Department");

		when(departmentService.updateDepartment(departmentNo, department)).thenReturn(updatedDepartment);

		ResponseEntity<Department> response = departmentController.updateDepartment(departmentNo, department);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedDepartment, response.getBody());
		verify(departmentService, times(1)).updateDepartment(departmentNo, department);
	}

//	6--------- UPDATE BY DPT NAME---------
	@Test
	public void testUpdateDepartmentByName() {

		String departmentName = "Product Analyst";

		Department updatedDepartment = new Department();
		updatedDepartment.setDeptName(departmentName);

		Department savedDepartment = new Department();
		savedDepartment.setDeptName(departmentName);

		when(departmentService.updateDepartmentByName(departmentName, updatedDepartment)).thenReturn(savedDepartment);

		ResponseEntity<Department> response = departmentController.updateDepartmentByName(departmentName,
				updatedDepartment);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(savedDepartment, response.getBody());
		verify(departmentService, times(1)).updateDepartmentByName(departmentName, updatedDepartment);
	}

////	7---------DELETE BY DPT No----------
//	@Test
//	public void testDeleteDepartmentByDeptNo() {
//
//		String departmentNumber = "A001";
//
//		String deleteMessage = "Department with deptno " + departmentNumber + " has been deleted";
//
//		when(departmentService.deleteDepartmentByDeptNo(departmentNumber)).thenReturn(deleteMessage);
//		ResponseEntity<String> response = departmentController.deleteDepartmentByDeptNo(departmentNumber);
//
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(deleteMessage, response.getBody());
//		verify(departmentService, times(1)).deleteDepartmentByDeptNo(departmentNumber);
//	}
//
////	8 ----- DELETE BY DPT NAME----------
//	@Test
//	public void testDeleteDepartmentByName() {
//
//		String departmentName = "Product Analyst";
//		String expectedResponse = "Department " + departmentName + " deleted successfully";
//
//
//		when(departmentService.deleteDepartmentByName(departmentName)).thenReturn(expectedResponse);
//
//		ResponseEntity<String> response = departmentController.deleteDepartmentByName(departmentName);
//
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(expectedResponse, response.getBody());
//		verify(departmentService, times(1)).deleteDepartmentByName(departmentName);
//	}

}
