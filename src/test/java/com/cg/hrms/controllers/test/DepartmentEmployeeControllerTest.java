package com.cg.hrms.controllers.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrms.controllers.DepartmentEmployeeController;
import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.services.IDepartmentEmployeeService;

import jakarta.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.*;
import java.sql.Date;

public class DepartmentEmployeeControllerTest {

    @Mock
    private IDepartmentEmployeeService service;

    @InjectMocks
    private DepartmentEmployeeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addDeptEmp_ValidDeptEmp_ReturnsCreatedStatus() {
        // Arrange
        DepartmentEmployee deptEmp = new DepartmentEmployee();
        when(service.addDeptEmp(deptEmp)).thenReturn(deptEmp);

        // Act
        ResponseEntity<String> response = controller.addDeptEmp(deptEmp);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New employee assigned to department successfully", response.getBody());
        verify(service, times(1)).addDeptEmp(deptEmp);
    }

    @Test
    void getAllDeptEmps_ReturnsListOfDeptEmps() {
        // Arrange
        List<DepartmentEmployee> deptEmps = new ArrayList<>();
        when(service.getAllDeptEmps()).thenReturn(deptEmps);

        // Act
        ResponseEntity<List<DepartmentEmployee>> response = controller.getAllDeptEmps();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deptEmps, response.getBody());
        verify(service, times(1)).getAllDeptEmps();
    }

    @Test
    void getDeptEmpById_ReturnsDeptEmp() {
        // Arrange
        Employee empNo = new Employee();
        Department deptNo = new Department();
        DepartmentEmployee deptEmp = new DepartmentEmployee();
        when(service.getDeptEmpByEmpNoDeptNo(empNo, deptNo)).thenReturn(deptEmp);

        // Act
        ResponseEntity<?> response = controller.getDeptEmpById(empNo, deptNo);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deptEmp, response.getBody());
        verify(service, times(1)).getDeptEmpByEmpNoDeptNo(empNo, deptNo);
    }
    
    @Test
    void getByDeptNoAndFromDate_ReturnsListOfDeptEmps() {
        // Arrange
        String deptNo = "123";
        Date fromDate = new Date(0, 0, 0);
        List<DepartmentEmployee> expectedDeptEmps = new ArrayList<>();
        when(service.getDeptEmpByDeptNoAndFromDate(deptNo, fromDate)).thenReturn(expectedDeptEmps);

        // Act
        List<DepartmentEmployee> actualDeptEmps = controller.getByDeptNoAndFromDate(deptNo, fromDate);

        // Assert
        assertEquals(expectedDeptEmps, actualDeptEmps);
        verify(service, times(1)).getDeptEmpByDeptNoAndFromDate(deptNo, fromDate);
    }
    
    
    @Test
    void getByEmpNoDeptNoAndFromDate_ReturnsDeptEmps() {
        // Arrange
        Employee empNo = new Employee();
        Department deptNo = new Department();
        Date fromDate = new Date(0, 0, 0);
        List<DepartmentEmployee> expectedDeptEmps = List.of(new DepartmentEmployee());
        when(service.getDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate)).thenReturn(expectedDeptEmps);

        // Act
        List<DepartmentEmployee> actualDeptEmps = controller.getByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);

        // Assert
        assertEquals(expectedDeptEmps, actualDeptEmps);
        verify(service, times(1)).getDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);
    }
    
    @Test
    void updateDeptEmpByEmpNoAndDeptNo_ReturnsResponse() {
        // Arrange
        Employee empNo = new Employee();
        Department deptNo = new Department();
        DepartmentEmployee deptEmp = new DepartmentEmployee();
        String expectedResponse = "Department Employee updated successfully";
        when(service.updateDeptEmpByEmpNoAndDeptNo(empNo, deptNo, deptEmp)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> responseEntity = controller.updateDeptEmpByEmpNoAndDeptNo(empNo, deptNo, deptEmp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(service, times(1)).updateDeptEmpByEmpNoAndDeptNo(empNo, deptNo, deptEmp);
    }
    
    @Test
    void updateDeptEmpByEmpNoAndFromDate_ReturnsResponse() {
        // Arrange
        Employee empNo = new Employee();
        Date fromDate = new Date(0, 0, 0);
        DepartmentEmployee deptEmp = new DepartmentEmployee();
        String expectedResponse = "Department Employee updated successfully";
        when(service.updateDeptEmpByEmpNoAndFromDate(empNo, fromDate, deptEmp)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> responseEntity = controller.updateDeptEmpByEmpNoAndFromDate(empNo, fromDate, deptEmp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(service, times(1)).updateDeptEmpByEmpNoAndFromDate(empNo, fromDate, deptEmp);
    }

    
    @Test
    void updateDepartmentEmployee_ReturnsResponse() {
        // Arrange
        Employee empno = new Employee();
        Department deptno = new Department();
        Date fromdate = new Date(0, 0, 0);
        DepartmentEmployee deptEmp = new DepartmentEmployee();
        String expectedResponse = "Department Employee updated successfully";
        when(service.updateDeptEmpByEmpNoDeptNoAndFromDate(empno, deptno, fromdate, deptEmp))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> responseEntity = controller.updateDepartmentEmployee(empno, deptno, fromdate, deptEmp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(service, times(1)).updateDeptEmpByEmpNoDeptNoAndFromDate(empno, deptno, fromdate, deptEmp);
    }
    
    @Test
    void deleteDeptEmpByEmpNoAndDeptNo_ReturnsResponse() {
        // Arrange
        int empNo = 123;
        String deptNo = "ABC";
        String expectedResponse = "Department Employee deleted successfully";
        when(service.deleteDeptEmpByEmpNoAndDeptNo(empNo, deptNo))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> responseEntity = controller.deleteDeptEmpByEmpNoAndDeptNo(empNo, deptNo);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(service, times(1)).deleteDeptEmpByEmpNoAndDeptNo(empNo, deptNo);
    }
    
    @Test
    void deleteDeptEmpByEmpNoAndFromDate_ReturnsResponseEntity() {
        // Arrange
        int empNo = 123;
        Date fromDate = new Date(empNo, empNo, empNo);
        DepartmentEmployee expectedDeletedDeptEmp = new DepartmentEmployee();
        when(service.deleteDeptEmpByEmpNoAndFromDate(empNo, fromDate))
                .thenReturn(expectedDeletedDeptEmp);

        // Act
        ResponseEntity<DepartmentEmployee> responseEntity = controller.deleteDeptEmpByEmpNoAndFromDate(empNo, fromDate);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDeletedDeptEmp, responseEntity.getBody());
        verify(service, times(1)).deleteDeptEmpByEmpNoAndFromDate(empNo, fromDate);
    }
    
    @Test
    void deleteDeptEmpByDeptNoAndFromDate_ReturnsResponseEntity() {
        // Arrange
        String deptNo = "123";
        Date fromDate = new Date(0, 0, 0);
        List<DepartmentEmployee> expectedDeptEmpList = new ArrayList<>();
        when(service.deleteDeptEmpByDeptNoAndFromDate(deptNo, fromDate))
                .thenReturn(expectedDeptEmpList);

        // Act
        ResponseEntity<List<DepartmentEmployee>> responseEntity = controller.getDeptEmpByDeptNoAndFromDate(deptNo, fromDate);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDeptEmpList, responseEntity.getBody());
        verify(service, times(1)).deleteDeptEmpByDeptNoAndFromDate(deptNo, fromDate);
    }
    
    @Test
    void deleteDeptEmpByEmpNoDeptNoAndFromDate_ReturnsResponseEntity() {
        // Arrange
        int empNo = 123;
        String deptNo = "456";
        Date fromDate = new Date(empNo, empNo, empNo);
        List<DepartmentEmployee> expectedDeletedDeptEmp = new ArrayList<>();
        when(service.deleteDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate))
                .thenReturn(expectedDeletedDeptEmp);

        // Act
        ResponseEntity<List<DepartmentEmployee>> responseEntity =
                controller.deleteDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDeletedDeptEmp, responseEntity.getBody());
        verify(service, times(1)).deleteDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);
    }
}
