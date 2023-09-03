package com.cg.hrms.services.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.DepartmentEmployeeId;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IDepartmentEmployeeRepo;
import com.cg.hrms.services.DepartmentEmployeeServiceImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;
import java.sql.Date;
import java.time.LocalDate;

public class DepartmentEmployeeServicesTest {
    
    @Mock
    private IDepartmentEmployeeRepo repo;
    
    @InjectMocks
    private DepartmentEmployeeServiceImpl service;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    
    @Test
    void getAllDeptEmps_ReturnsListOfDeptEmps() {
        // Arrange
        List<DepartmentEmployee> expectedDeptEmps = new ArrayList<>();
        expectedDeptEmps.add(new DepartmentEmployee());
        expectedDeptEmps.add(new DepartmentEmployee());
        when(repo.findAll()).thenReturn(expectedDeptEmps);

        // Act
        List<DepartmentEmployee> actualDeptEmps = service.getAllDeptEmps();

        // Assert
        assertEquals(expectedDeptEmps, actualDeptEmps);
        assertEquals(expectedDeptEmps.size(), actualDeptEmps.size());
    }
    
    @Test
    void getDeptEmpByEmpNoDeptNo_ReturnsDeptEmp() {
        // Arrange
        Employee empNo = new Employee();
        Department deptNo = new Department();
        DepartmentEmployee expectedDeptEmp = new DepartmentEmployee();
        when(repo.findById_EmpNoAndId_DeptNo(empNo, deptNo)).thenReturn(expectedDeptEmp);

        // Act
        DepartmentEmployee actualDeptEmp = service.getDeptEmpByEmpNoDeptNo(empNo, deptNo);

        // Assert
        assertEquals(expectedDeptEmp, actualDeptEmp);
    }
    
    @Test
    void getDeptEmpByDeptNoAndFromDate_ReturnsDeptEmps() {
        // Arrange
        String deptNo = "123";
        Date fromDate = new Date(0, 0, 0);
        List<DepartmentEmployee> expectedDeptEmps = new ArrayList<>();
        when(repo.findById_DeptNoAndFromDate(Mockito.any(Department.class), Mockito.eq(fromDate))).thenReturn(expectedDeptEmps);

        // Act
        List<DepartmentEmployee> actualDeptEmps = service.getDeptEmpByDeptNoAndFromDate(deptNo, fromDate);

        // Assert
        assertEquals(expectedDeptEmps, actualDeptEmps);
    }
    
    @Test
    void getDeptEmpByEmpNoDeptNoAndFromDate_ReturnsDeptEmps() {
        // Arrange
        Employee empNo = new Employee();
        empNo.setEmpNo(123);
        Department deptNo = new Department();
        deptNo.setDeptNo("ABC");
        Date fromDate = new Date(0, 0, 0);
        List<DepartmentEmployee> expectedDeptEmps = new ArrayList<>();
        when(repo.findById_EmpNoAndId_DeptNoAndFromDate(Mockito.eq(empNo), Mockito.eq(deptNo), Mockito.eq(fromDate))).thenReturn(expectedDeptEmps);

        // Act
        List<DepartmentEmployee> actualDeptEmps = service.getDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);

        // Assert
        assertEquals(expectedDeptEmps, actualDeptEmps);
    }
    

    @Test
    void updateDeptEmpByEmpNoAndDeptNo_NonExistingDepartmentEmployee_ExceptionThrown() {
        // Arrange
        Employee empNo = new Employee();
        empNo.setEmpNo(123);
        Department deptNo = new Department();
        deptNo.setDeptNo("ABC");
        DepartmentEmployee updatedDeptEmp = new DepartmentEmployee();
        updatedDeptEmp.setFromDate(new Date(0, 0, 0));
        updatedDeptEmp.setToDate(new Date(0, 0, 0));
        when(repo.findById_EmpNoAndId_DeptNo(empNo, deptNo)).thenReturn(null);

        // Act and Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            service.updateDeptEmpByEmpNoAndDeptNo(empNo, deptNo, updatedDeptEmp);
        });
        assertEquals("For given empNo and deptNo no DepartmentEmployee record found", exception.getMessage());
    }
    

    @Test
    void getDeptEmpByEmpNoDeptNoAndFromDate_ReturnsDepartmentEmployees() {
        // Arrange
        Employee empNo = new Employee();
        Department deptNo = new Department();
        Date fromDate = Date.valueOf("2023-01-01");
        DepartmentEmployee deptEmp1 = new DepartmentEmployee();
        DepartmentEmployee deptEmp2 = new DepartmentEmployee();
        when(repo.findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo, fromDate))
                .thenReturn(Arrays.asList(deptEmp1, deptEmp2));

        // Act
        List<DepartmentEmployee> result = service.getDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);

        // Assert
        assertEquals(2, result.size());
        assertEquals(deptEmp1, result.get(0));
        assertEquals(deptEmp2, result.get(1));
        verify(repo, times(1)).findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo, fromDate);
    }
    
    @Test
    void updateDeptEmpByEmpNoAndDeptNo_ExistingDepartmentEmployee_ReturnsSuccessMessage() {
        // Arrange
        Employee empNo = new Employee();
        empNo.setEmpNo(123);
        Department deptNo = new Department();
        deptNo.setDeptNo("456");
        DepartmentEmployee existingDeptEmp = new DepartmentEmployee();
        existingDeptEmp.setFromDate(new Date(0, 0, 0));
        existingDeptEmp.setToDate(new Date(0, 0, 0));
        DepartmentEmployee updatedDeptEmp = new DepartmentEmployee();
        updatedDeptEmp.setFromDate(new Date(0, 0, 0));
        updatedDeptEmp.setToDate(new Date(0, 0, 0));
        when(repo.findById_EmpNoAndId_DeptNo(empNo, deptNo)).thenReturn(existingDeptEmp);
        when(repo.save(existingDeptEmp)).thenReturn(existingDeptEmp);

        // Act
        String result = service.updateDeptEmpByEmpNoAndDeptNo(empNo, deptNo, updatedDeptEmp);

        // Assert
        assertEquals("Department Employee updated successfully.", result);
    }
    
    @Test
    void updateDeptEmpByEmpNoAndDeptNo_NonExistingDepartmentEmployee_ThrowsException() {
        // Arrange
        Employee empNo = new Employee();
        empNo.setEmpNo(123);
        Department deptNo = new Department();
        deptNo.setDeptNo("456");
        DepartmentEmployee existingDeptEmp = null;
        DepartmentEmployee updatedDeptEmp = new DepartmentEmployee();
        updatedDeptEmp.setFromDate(new Date(0, 0, 0));
        updatedDeptEmp.setToDate(new Date(0, 0, 0));
        when(repo.findById_EmpNoAndId_DeptNo(empNo, deptNo)).thenReturn(existingDeptEmp);

        // Act and Assert
        assertThrows(CustomException.class, () -> service.updateDeptEmpByEmpNoAndDeptNo(empNo, deptNo, updatedDeptEmp));
    }
    
    @Test
    void updateDeptEmpByEmpNoAndFromDate_ExistingDepartmentEmployee_ReturnsSuccessMessage() {
        // Arrange
        Employee empNo = new Employee();
        empNo.setEmpNo(123);
        Date fromDate = new Date(0, 0, 0);
        DepartmentEmployee existingDeptEmp = new DepartmentEmployee();
        existingDeptEmp.setFromDate(fromDate);
        existingDeptEmp.setToDate(new Date(0, 0, 0));
        DepartmentEmployee updatedDeptEmp = new DepartmentEmployee();
        updatedDeptEmp.setFromDate(fromDate);
        updatedDeptEmp.setToDate(new Date(0, 0, 0));
        when(repo.findById_EmpNoAndFromDate(empNo, fromDate)).thenReturn(existingDeptEmp);
        when(repo.save(existingDeptEmp)).thenReturn(existingDeptEmp);

        // Act
        String result = service.updateDeptEmpByEmpNoAndFromDate(empNo, fromDate, updatedDeptEmp);

        // Assert
        assertEquals("Department Employee updated successfully", result);
    }

    @Test
    void updateDeptEmpByEmpNoDeptNoAndFromDate_NonExistingDepartmentEmployee_ThrowsException() {
        // Arrange
        Employee empNo = new Employee();
        empNo.setEmpNo(123);
        Department deptNo = new Department();
        deptNo.setDeptNo("ABC");
        Date fromDate = new Date(0, 0, 0);
        DepartmentEmployee updatedDeptEmp = new DepartmentEmployee();
        updatedDeptEmp.setFromDate(fromDate);
        updatedDeptEmp.setToDate(new Date(0, 0, 0));
        when(repo.findUpdateById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo, fromDate)).thenReturn(null);

        // Act and Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            service.updateDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate, updatedDeptEmp);
        });
        assertEquals("For given empNo, deptNo and fromDate no DepartmentEmployee record found", exception.getMessage());
    }

    @Test
    void deleteDeptEmpByEmpNoAndDeptNo_NonExistingDepartmentEmployee_ReturnsFailureMessage() {
        // Arrange
        int empNo = 123;
        String deptNo = "ABC";
        Employee emp = new Employee();
        emp.setEmpNo(empNo);
        Department dept = new Department();
        dept.setDeptNo(deptNo);
        when(repo.findById_EmpNoAndId_DeptNo(emp, dept)).thenReturn(null);

        // Act
        String result = service.deleteDeptEmpByEmpNoAndDeptNo(empNo, deptNo);

        // Assert
        assertEquals("Validation Failed, Deleting is unsuccessful", result);
    }

    @Test
    void deleteDeptEmpByEmpNoAndDeptNo_NonExistingDepartmentEmployee_ReturnsValidationFailedMessage() {
        // Arrange
        int empNo = 123;
        String deptNo = "ABC";
        Employee emp = new Employee();
        emp.setEmpNo(empNo);
        Department dept = new Department();
        dept.setDeptNo(deptNo);
        when(repo.findById_EmpNoAndId_DeptNo(emp, dept)).thenReturn(null);

        // Act
        String result = service.deleteDeptEmpByEmpNoAndDeptNo(empNo, deptNo);

        // Assert
        assertEquals("Validation Failed, Deleting is unsuccessful", result);
    }
    

    @Test
    void deleteDeptEmpByDeptNoAndFromDate_NonExistingDepartmentEmployee_ThrowsException() {
        // Arrange
        String deptNo = "123";
        Date fromDate = new Date(0, 0, 0);
        Department dept = new Department();
        dept.setDeptNo(deptNo);
        List<DepartmentEmployee> existingDeptEmps = Collections.emptyList();
        when(repo.deleteById_DeptNoAndFromDate(dept, fromDate)).thenReturn(existingDeptEmps);

        // Act and Assert
        assertThrows(CustomException.class, () -> service.deleteDeptEmpByDeptNoAndFromDate(deptNo, fromDate));
    }

    
    @Test
    void deleteDeptEmpByEmpNoDeptNoAndFromDate_DeletesDeptEmpsAndReturnsDeletedList() {
        // Arrange
        int empNo = 123;
        String deptNo = "ABC";
        Date fromDate = new Date(0, 0, 0);
        Employee emp = new Employee();
        emp.setEmpNo(empNo);
        Department dept = new Department();
        dept.setDeptNo(deptNo);
        List<DepartmentEmployee> expectedDeletedDeptEmps = new ArrayList<>();
        when(repo.deleteById_EmpNoAndId_DeptNoAndFromDate(Mockito.eq(emp), Mockito.eq(dept), Mockito.eq(fromDate))).thenReturn(expectedDeletedDeptEmps);

        // Act
        List<DepartmentEmployee> actualDeletedDeptEmps = service.deleteDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);

        // Assert
        assertEquals(expectedDeletedDeptEmps, actualDeletedDeptEmps);
    }	

}
