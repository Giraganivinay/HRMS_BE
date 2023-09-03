package com.cg.hrms.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.cg.hrms.entities.Department;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IDepartmentRepo;
import com.cg.hrms.services.DepartmentServiceImpl;

public class DepartmentServiceTest {
	@Mock
	private IDepartmentRepo departmentRepo;

	@InjectMocks
	private DepartmentServiceImpl departmentService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

//	1------ ADD DEPT---------
	@Test
	public void testAddDepartment() {
		Department department = new Department();
		department.setDeptName("Test Department");

		Department savedDepartment = new Department();
		savedDepartment.setDeptName("Test Department");

		when(departmentRepo.save(department)).thenReturn(savedDepartment);

		Department result = departmentService.addDepartment(department);

		assertEquals(savedDepartment, result);
		verify(departmentRepo, times(1)).save(department);
	}

//	2--------GET DPT---------
	@Test
	public void testGetAllDepartments() {
		Department department1 = new Department();
		department1.setDeptName("Department 1");

		Department department2 = new Department();
		department2.setDeptName("Department 2");

		List<Department> departments = Arrays.asList(department1, department2);

		when(departmentRepo.findAll()).thenReturn(departments);

		List<Department> result = departmentService.getAllDepartments();

		assertEquals(departments, result);
		verify(departmentRepo, times(1)).findAll();
	}

//	3-----GET DPT BY No------
	@Test
	public void testGetDepartmentByDeptNo() {
		String departmentNumber = "D001";

		Department department = new Department();
		department.setDeptNo(departmentNumber);

		when(departmentRepo.findByDeptNo(departmentNumber)).thenReturn(department);

		Department result = departmentService.getDepartmentByDeptNo(departmentNumber);

		assertEquals(department, result);
		verify(departmentRepo, times(1)).findByDeptNo(departmentNumber);
	}

//	    -----NOT WORKING--------

//	    @Test
//	    public void testGetDeptByName() {
//	        // Arrange
//	        String departmentName = "Test Department";
//	        Department department = new Department();
//	        department.setDeptName(departmentName);
//
//	        when(departmentService.getDepartmentByName(departmentName)).thenReturn(department);
//
//	        // Act
//	        ResponseEntity<Department> response = departmentController.getDeptByName(departmentName);
//
//	        // Assert
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertEquals(department, response.getBody());
//	        verify(departmentService, times(1)).getDepartmentByName(departmentName);
//	    }

	
//	------UPDATE BY DPT No---------
	@Test
	public void testUpdateDepartment() {
		String departmentNo = "D001";
		String newDepartmentName = "New Department";

		Department existingDepartment = new Department();
		existingDepartment.setDeptNo(departmentNo);
		existingDepartment.setDeptName("Old Department");

		Department updatedDepartment = new Department();
		updatedDepartment.setDeptNo(departmentNo);
		updatedDepartment.setDeptName(newDepartmentName);

		when(departmentRepo.findById(departmentNo)).thenReturn(java.util.Optional.of(existingDepartment));
		when(departmentRepo.save(existingDepartment)).thenReturn(updatedDepartment);

		Department result = departmentService.updateDepartment(departmentNo, updatedDepartment);

		assertEquals(newDepartmentName, result.getDeptName());
		verify(departmentRepo, times(1)).findById(departmentNo);
		verify(departmentRepo, times(1)).save(existingDepartment);
	}

//	    ------- update by name-----
	@Test
	public void testUpdateDepartmentByName() {
		String departmentName = "Test Department";
		Department existingDepartment = new Department();
		existingDepartment.setDeptName(departmentName);

		Department updatedDepartment = new Department();
		updatedDepartment.setDeptNo("D123");
		updatedDepartment.setDeptName(departmentName);

		when(departmentRepo.findByDeptName(departmentName)).thenReturn(existingDepartment);
		when(departmentRepo.save(existingDepartment)).thenReturn(existingDepartment);

		Department result = departmentService.updateDepartmentByName(departmentName, updatedDepartment);

		assertEquals(updatedDepartment.getDeptNo(), result.getDeptNo());
		assertEquals(updatedDepartment.getDeptName(), result.getDeptName());
		verify(departmentRepo, times(1)).findByDeptName(departmentName);
		verify(departmentRepo, times(1)).save(existingDepartment);
	}

	@Test
	public void testUpdateDepartmentByName_ThrowsException() {
		String departmentName = "Non-existent Department";
		Department updatedDepartment = new Department();

		when(departmentRepo.findByDeptName(departmentName)).thenReturn(null);

		assertThrows(CustomException.class, () -> {
			departmentService.updateDepartmentByName(departmentName, updatedDepartment);
		});
	}
//	    
//	    ---NOT WORKING--
//	    @Test
//	    public void testDeleteDepartmentByDeptNo() {
//	        String departmentNo = "DEPT001";
//	        Department department = new Department();
//	        department.setDeptNo(departmentNo);
//
//	        when(departmentRepo.findByDeptNo(departmentNo)).thenReturn(department);
//
//	        String result = departmentController.deleteDepartmentByDeptNo(departmentNo);
//
//	        assertEquals("Employee deleted successfully", result);
//	        verify(departmentRepo, times(1)).deleteByDeptNo(departmentNo);
//	    }
//
//	    @Test
//	    public void testDeleteDepartmentByDeptNo_NotFound() {
//	        String departmentNo = "DEPT001";
//
//	        when(departmentRepo.findByDeptNo(departmentNo)).thenReturn(null);
//
//	        assertThrows(CustomException.class, () -> {
//	            departmentController.deleteDepartmentByDeptNo(departmentNo);
//	        });
//	        verify(departmentRepo, never()).deleteByDeptNo(departmentNo);
//	    }
//	    
//	    
//	    @Test
//	    public void testDeleteDepartmentByName() {
//	        String departmentName = "Test Department";
//	        Department department = new Department();
//	        department.setDeptName(departmentName);
//
//	        when(departmentService.findByDeptName(departmentName)).thenReturn(department);
//	        String result = departmentController.deleteDepartmentByName(departmentName);
//
//	        assertEquals("Employee deleted successfully", result);
//	        verify(departmentService, times(1)).findByDeptName(departmentName);
//	        verify(departmentService, times(1)).deleteByDeptName(departmentName);
//	    }
////
//	    @Test
//	    public void testDeleteDepartmentByName_NotFound() {
//	        String departmentName = "Nonexistent Department";
//	        when(departmentService.findByDeptName(departmentName)).thenReturn(null);
//
//	        assertThrows(CustomException.class, () -> {
//	            departmentController.deleteDepartmentByName(departmentName);
//	        });
//	        verify(departmentService, times(1)).findByDeptName(departmentName);
//	        verify(departmentService, never()).deleteByDeptName(departmentName);
//	    }

}
