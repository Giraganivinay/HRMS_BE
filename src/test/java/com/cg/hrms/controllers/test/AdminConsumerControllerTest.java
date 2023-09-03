package com.cg.hrms.controllers.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrms.controllers.AdminConsumerController;
import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.services.IAdminConsumerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminConsumerControllerTest {

    @Mock
    private IAdminConsumerService service;

    @InjectMocks
    private AdminConsumerController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEmployee_NewEmployeeAdded_Successfully() {
        // Arrange
        Employee employee = new Employee();
        when(service.addNewEmployee(employee)).thenReturn(employee);

        // Act
        ResponseEntity<Employee> response = controller.addEmployee(employee);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(service, times(1)).addNewEmployee(employee);
    }

   

    @Test
    void getCountOfEmployeesJoinedInLastYears_ReturnsCountOfEmployees() {
        // Arrange
        int noOfYears = 5;
        int count = 10;
        when(service.getCountOfEmployeesJoinedInLastYears(noOfYears)).thenReturn(count);

        // Act
        ResponseEntity<Integer> num = controller.getCountOfEmployeesJoinedInLastYears(noOfYears);

        // Assert
        assertEquals(count, num.getBody());
        verify(service, times(1)).getCountOfEmployeesJoinedInLastYears(noOfYears);
    }

    @Test
    void getEmployeesJoinedAfterYear_ReturnsEmployeesList() {
        // Arrange
        int year = 2022;
        List<EmployeeDto> employees = new ArrayList<>();
        when(service.getEmployeesJoinedAfterYear(year)).thenReturn(employees);

        // Act
        ResponseEntity<List<EmployeeDto>> response = controller.getEmployeesJoinedAfterYear(year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(service, times(1)).getEmployeesJoinedAfterYear(year);
    }
    
    @Test
    void getEmployeesJoinedInLastYears_ReturnsEmployeesList() {
        // Arrange
        int noOfYears = 5;
        List<EmployeeDto> employees = new ArrayList<>();
        when(service.getEmployeesJoinedInLastYears(noOfYears)).thenReturn(employees);

        // Act
        ResponseEntity<List<EmployeeDto>> response = controller.getEmployeesJoinedInLastYears(noOfYears);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(service, times(1)).getEmployeesJoinedInLastYears(noOfYears);
    }
   
    
    @Test
    void deleteEmployeeByEmpNo_EmployeeDeleted_Successfully() {
        // Arrange
        int empNo = 123;
        // Assuming the employee is deleted successfully, no need to return any specific response

        // Act
        ResponseEntity<String> response = controller.deleteEmployeeByEmpNo(empNo);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).deleteEmployeeByEmpNo(empNo);
    }
    
    @Test
    void getWomenEmployeeByGender_ReturnsListOfEmployees() {
        // Arrange
        String gender = "female";
        List<Employee> womenEmployees = new ArrayList<>();
        womenEmployees.add(new Employee());
        womenEmployees.add(new Employee());
        when(service.getWomenEmployeesByGender(gender)).thenReturn(womenEmployees);

        // Act
        ResponseEntity<List<Employee>> response = controller.getWomenEmployeeByGender(gender);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(womenEmployees, response.getBody());
        verify(service, times(1)).getWomenEmployeesByGender(gender);
    }
    
    @Test
    void getCountOfWomenEmployess_ReturnsCountOfEmployees() {
        // Arrange
        String gender = "female";
        int count = 10;
        when(service.getCountOfWomenEmployess(gender)).thenReturn(count);

        // Act
        ResponseEntity<Integer> response = controller.getCountOfWomenEmployess(gender);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(count, response.getBody());
        verify(service, times(1)).getCountOfWomenEmployess(gender);
    }
    
    @Test
    void getEmployeesByExperience_ReturnsListOfEmployees() {
        // Arrange
        int noOfYears = 5;
        List<Employee> employees = new ArrayList<>();
        when(service.getEmployeesByExperience(noOfYears)).thenReturn(employees);

        // Act
        ResponseEntity<?> response = controller.getEmployeesByExperience(noOfYears);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(service, times(1)).getEmployeesByExperience(noOfYears);
    }
    
    @Test
    void getCountOfWomenEmployess_ReturnsCountOfWomenEmployees() {
        // Arrange
        String gender = "female";
        int expectedCount = 10;
        when(service.getCountOfWomenEmployess(gender)).thenReturn(expectedCount);

        // Act
        ResponseEntity<Integer> response = controller.getCountOfWomenEmployess(gender);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCount, response.getBody());
        verify(service, times(1)).getCountOfWomenEmployess(gender);
    }
    
    @Test
    void getWomenEmployeeByGender_ReturnsWomenEmployeesByGender() {
        // Arrange
        String gender = "female";
        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new Employee());
        expectedEmployees.add(new Employee());
        when(service.getWomenEmployeesByGender(gender)).thenReturn(expectedEmployees);

        // Act
        ResponseEntity<List<Employee>> response = controller.getWomenEmployeeByGender(gender);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEmployees, response.getBody());
        verify(service, times(1)).getWomenEmployeesByGender(gender);
    }
    
 

    
    
}    