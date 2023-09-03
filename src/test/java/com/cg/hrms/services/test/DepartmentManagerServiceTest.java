package com.cg.hrms.services.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.DepartmentManagerId;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IDepartmentManagerRepo;
import com.cg.hrms.services.DepartmentManagerImpl;

public class DepartmentManagerServiceTest {

	@Mock
	private IDepartmentManagerRepo repo;

	@InjectMocks
	private DepartmentManagerImpl service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// 1. Search department manager by empNo and deptNo
	@Test
	void testGetDeptManagerById_ReturnsDeptManager() {
		// Arrange
		int empNo = 123;
		String deptNo = "ABC";
		DepartmentManager expectedDeptManager = new DepartmentManager();
		when(repo.findById_EmpNoAndId_DeptNo(any(Employee.class), any(Department.class)))
				.thenReturn(expectedDeptManager);

		// Act
		DepartmentManager actualDeptManager = service.getDeptManagerById(empNo, deptNo);

		// Assert
		assertEquals(expectedDeptManager, actualDeptManager);
	}

	// 2.Fetch all department manager objects
	@Test
	void testGetAllDeptManagers() {
		// Arrange
		DepartmentManager deptManager = new DepartmentManager();
		List<DepartmentManager> expectedManagers = Arrays.asList(deptManager);
		when(repo.findAll()).thenReturn(expectedManagers);

		// Act
		List<DepartmentManager> actualManagers = service.getAllDeptManagers();

		// Assert
		assertNotNull(actualManagers);
		assertEquals(expectedManagers, actualManagers);
	}

	// 3.Fetch all department manager objects by , deptno and from date
	@Test
	void getDeptEmpByDeptNoAndFromDate_ReturnsDeptManager() {
		// Arrange
		String deptNo = "123";
		Date fromDate = new Date(0, 0, 0);

		List<DepartmentManager> expectedDeptManager = new ArrayList<>();
		when(repo.findById_DeptNoAndFromDate(Mockito.any(Department.class), Mockito.eq(fromDate)))
				.thenReturn(expectedDeptManager);

		// Act
		List<DepartmentManager> actualManagers = service.getDeptManagersByDeptNoAndFromDate(deptNo, fromDate);

		// Assert

		assertEquals(expectedDeptManager, actualManagers);
	}

	// 4.Fetch all deptmanager objects by, id and from date
	@Test
	void testGetDeptManagersByEmpNoAndFromDate_ReturnsDeptManager() {
		// Arrange
		int empNo = 123;
		Date fromDate = new Date(0, 0, 0);
		Optional<DepartmentManager> expectedDeptManager = Optional.of(new DepartmentManager());
		when(repo.findById_EmpNoAndFromDate(any(Employee.class), eq(fromDate))).thenReturn(expectedDeptManager);

		// Act
		Optional<DepartmentManager> actualDeptManager = service.getDeptManagersByEmpNoAndFromDate(empNo, fromDate);

		// Assert
		assertEquals(expectedDeptManager, actualDeptManager);
	}

	// 5.Fetch all deptmanager objects by , empNo ,deptno,and from date
	@Test
	void testGetDeptManagersByEmpNoDeptNoAndFromDate_ReturnsDeptManager() {
		// Arrange
		int empNo = 123;
		String deptNo = "ABC";
		Date fromDate = new Date(0, 0, 0);
		Optional<DepartmentManager> expectedDeptManager = Optional.of(new DepartmentManager());
		when(repo.findById_EmpNoAndId_DeptNoAndFromDate(any(Employee.class), any(Department.class), eq(fromDate)))
				.thenReturn(expectedDeptManager);

		// Act
		Optional<DepartmentManager> actualDeptManager = service.getDeptManagersByEmpNoDeptNoAndFromDate(empNo, deptNo,
				fromDate);

		// Assert
		assertEquals(expectedDeptManager, actualDeptManager);
	}

	// 6.Update deptmanager details for the given empno and deptno
	@Test
	void updateDepartmentManagerByEmpNoAndDeptNo_ExistingDepartmentManager_SuccessfulUpdate() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Department deptNo = new Department();
		deptNo.setDeptNo("ABC");
		DepartmentManager existingManager = new DepartmentManager();
		existingManager.setFromDate(new Date(0, 0, 0));
		existingManager.setToDate(new Date(0, 0, 0));
		DepartmentManager updatedManager = new DepartmentManager();
		updatedManager.setFromDate(new Date(2023, 6, 9));
		updatedManager.setToDate(new Date(2024, 6, 9));
		when(repo.findById_EmpNoAndId_DeptNo(empNo, deptNo)).thenReturn(existingManager);

		// Act
		String result = service.updateDepartmentManagerByEmpNoAndDeptNo(empNo, deptNo, updatedManager);

		// Assert
		assertEquals("DepartmentManager updated successfully", result);
		verify(repo).save(existingManager);
		assertEquals(updatedManager.getFromDate(), existingManager.getFromDate());
		assertEquals(updatedManager.getToDate(), existingManager.getToDate());
	}

	@Test
	void updateDepartmentManagerByEmpNoAndDeptNo_NonExistingDepartmentManager_ExceptionThrown() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Department deptNo = new Department();
		deptNo.setDeptNo("ABC");
		DepartmentManager existingManager = null;
		DepartmentManager updatedManager = new DepartmentManager();
		updatedManager.setFromDate(new Date(2023, 6, 9));
		updatedManager.setToDate(new Date(2024, 6, 9));
		when(repo.findById_EmpNoAndId_DeptNo(empNo, deptNo)).thenReturn(existingManager);

		// Act and Assert
		CustomException exception = assertThrows(CustomException.class, () -> {
			service.updateDepartmentManagerByEmpNoAndDeptNo(empNo, deptNo, updatedManager);
		});
		assertEquals("DepartmentManager not found", exception.getMessage());
		verify(repo, never()).save(any(DepartmentManager.class));
	}

	// 7.Update deptmanager details for the given empno and fromdate
	@Test
	void updateDepartmentManagerByEmpNoAndFromDate_ExistingDepartmentManager_ReturnsSuccessMessage() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Date fromDate = new Date(0, 0, 0);
		DepartmentManager existingManager = new DepartmentManager();
		existingManager.setFromDate(fromDate);
		existingManager.setToDate(new Date(0, 0, 0));
		DepartmentManager updatedManager = new DepartmentManager();
		updatedManager.setFromDate(fromDate);
		updatedManager.setToDate(new Date(0, 0, 0));
		when(repo.findById_EmpNoAndFromDate(empNo, fromDate)).thenReturn(Optional.of(existingManager));
		when(repo.save(existingManager)).thenReturn(existingManager);

		// Act
		String result = service.updateDepartmentManagerByEmpNoAndFromDate(empNo, fromDate, updatedManager);

		// Assert
		assertEquals("DepartmentManager updated successfully", result);
		verify(repo).save(existingManager);
		assertEquals(updatedManager.getToDate(), existingManager.getToDate());
	}

	@Test
	void updateDepartmentManagerByEmpNoAndFromDate_NonExistingDepartmentManager_ExceptionThrown() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Date fromDate = new Date(0, 0, 0);
		Optional<DepartmentManager> existingManager = Optional.empty();
		DepartmentManager updatedManager = new DepartmentManager();
		updatedManager.setFromDate(fromDate);
		updatedManager.setToDate(new Date(0, 0, 0));
		when(repo.findById_EmpNoAndFromDate(empNo, fromDate)).thenReturn(existingManager);

		// Act and Assert
		CustomException exception = assertThrows(CustomException.class, () -> {
			service.updateDepartmentManagerByEmpNoAndFromDate(empNo, fromDate, updatedManager);
		});
		assertEquals("DepartmentManager not found", exception.getMessage());
		verify(repo, never()).save(any(DepartmentManager.class));
	}

	// 8.Update deptmanager by empno, deptno, fromdate
	@Test
	void testUpdateDepartmentManagerByEmpNoDeptNoAndFromDate_ExistingDepartmentManager_SuccessfulUpdate() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Department deptNo = new Department();
		deptNo.setDeptNo("ABC");
		Date fromDate = new Date(0, 0, 0);
		DepartmentManager existingManager = new DepartmentManager();
		existingManager.setFromDate(fromDate);
		existingManager.setToDate(new Date(0, 0, 0));
		DepartmentManager updatedManager = new DepartmentManager();
		updatedManager.setFromDate(fromDate);
		updatedManager.setToDate(new Date(2024, 6, 9));
		when(repo.findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo, fromDate))
				.thenReturn(Optional.of(existingManager));

		// Act
		String result = service.updateDepartmentManagerByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate,
				updatedManager);

		// Assert
		assertEquals("DepartmentManager updated successfully", result);
		verify(repo).save(existingManager);
		assertEquals(updatedManager.getToDate(), existingManager.getToDate());
	}

	@Test
	void testUpdateDepartmentManagerByEmpNoDeptNoAndFromDate_NonExistingDepartmentManager_ExceptionThrown() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Department deptNo = new Department();
		deptNo.setDeptNo("ABC");
		Date fromDate = new Date(0, 0, 0);
		when(repo.findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo, fromDate)).thenReturn(Optional.empty());

		// Act and Assert
		CustomException exception = assertThrows(CustomException.class, () -> {
			service.updateDepartmentManagerByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate, new DepartmentManager());
		});
		assertEquals("DepartmentManager not found", exception.getMessage());
		verify(repo, never()).save(any(DepartmentManager.class));
	}

	// 9.delete by empno and fromdate
	@Test
	void deleteDepartmentManagerByEmpNoAndFromDate_ExistingDepartmentManager_SuccessfullyDeleted() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Date fromDate = new Date(0, 0, 0);
		DepartmentManager existingManager = new DepartmentManager();
		existingManager.setFromDate(fromDate);
		existingManager.setToDate(new Date(0, 0, 0));
		Optional<DepartmentManager> deletedDepartmentManager = Optional.of(existingManager);
		when(repo.findById_EmpNoAndFromDate(empNo, fromDate)).thenReturn(deletedDepartmentManager);

		// Act
		DepartmentManager deletedManager = service.deleteDepartmentManagerByEmpNoAndFromDate(empNo, fromDate);

		// Assert
		assertEquals(existingManager, deletedManager);
		verify(repo).delete(existingManager);
	}

	@Test
	void deleteDepartmentManagerByEmpNoAndFromDate_NonExistingDepartmentManager_ExceptionThrown() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Date fromDate = new Date(0, 0, 0);
		Optional<DepartmentManager> deletedDepartmentManager = Optional.empty();
		when(repo.findById_EmpNoAndFromDate(empNo, fromDate)).thenReturn(deletedDepartmentManager);

		// Act and Assert
		CustomException exception = assertThrows(CustomException.class, () -> {
			service.deleteDepartmentManagerByEmpNoAndFromDate(empNo, fromDate);
		});
		assertEquals("DepartmentManager not found", exception.getMessage());
		verify(repo, never()).delete(any(DepartmentManager.class));
	}


	// 11.Delete deptmanager by deptno and fromdate
	@Test
	void deleteDepartmentManagersByDeptNoAndFromDate_ExistingDepartmentManagers_SuccessfullyDeleted() {
		// Arrange
		Department deptNo = new Department();
		deptNo.setDeptNo("ABC");
		Date fromDate = new Date(0, 0, 0);
		List<DepartmentManager> existingManagers = new ArrayList<>();
		existingManagers.add(new DepartmentManager());
		existingManagers.add(new DepartmentManager());
		when(repo.findById_DeptNoAndFromDate(deptNo, fromDate)).thenReturn(existingManagers);

		// Act
		List<DepartmentManager> deletedManagers = service.deleteDepartmentManagersByDeptNoAndFromDate(deptNo, fromDate);

		// Assert
		assertEquals(existingManagers, deletedManagers);
		verify(repo, times(1)).deleteAll(existingManagers);
	}

	// 12.Delete deptmanager by empno, deptno and fromdate
	@Test
	void deleteDepartmentManagerByEmpNoDeptNoAndFromDate_ExistingDepartmentManager_SuccessfullyDeleted() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Department deptNo = new Department();
		deptNo.setDeptNo("ABC");
		Date fromDate = new Date(0, 0, 0);
		DepartmentManager existingManager = new DepartmentManager();
		existingManager.setFromDate(fromDate);
		existingManager.setToDate(new Date(0, 0, 0));
		Optional<DepartmentManager> departmentManagers = Optional.of(existingManager);
		when(repo.findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo, fromDate)).thenReturn(departmentManagers);

		// Act
		DepartmentManager deletedManager = service.deleteDepartmentManagerByEmpNoDeptNoAndFromDate(empNo, deptNo,
				fromDate);

		// Assert
		assertEquals(existingManager, deletedManager);
		verify(repo).delete(existingManager);
	}

	@Test
	void deleteDepartmentManagerByEmpNoDeptNoAndFromDate_NonExistingDepartmentManager_ExceptionThrown() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(123);
		Department deptNo = new Department();
		deptNo.setDeptNo("ABC");
		Date fromDate = new Date(0, 0, 0);
		Optional<DepartmentManager> departmentManagers = Optional.empty();
		when(repo.findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo, fromDate)).thenReturn(departmentManagers);

		// Act and Assert
		CustomException exception = assertThrows(CustomException.class, () -> {
			service.deleteDepartmentManagerByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);
		});
		assertEquals("DepartmentManager not found", exception.getMessage());
		verify(repo, never()).delete(any(DepartmentManager.class));
	}
}