package com.cg.hrms.services.test;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cg.hrms.controllers.AuthenticationController;
import com.cg.hrms.dto.AuthenticationResponse;
import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.dto.RegisterRequest;
import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.DepartmentEmployeeId;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Role;
import com.cg.hrms.entities.User;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IDepartmentEmployeeRepo;
import com.cg.hrms.repos.IDepartmentManagerRepo;
import com.cg.hrms.repos.IDepartmentRepo;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.repos.ISalaryRepo;
import com.cg.hrms.repos.ITitleRepo;
import com.cg.hrms.repos.IUserRepo;
import com.cg.hrms.security.JwtService;
import com.cg.hrms.services.AdminConsumerServiceImpl;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminConsumerServiceTest {
    
    @InjectMocks
    private AdminConsumerServiceImpl service;
    
    @Mock
    private IUserRepo userRepository;
    
    @Mock
    private IDepartmentEmployeeRepo deptEmpRepo;
    
    @Mock
    private IEmployeeRepo empRepo;
    
    @Mock
    private IDepartmentRepo deptRepo;
    
    @Mock
    private IDepartmentManagerRepo deptManagerRepo;
    
    @Mock
    private ITitleRepo titleRepo;
    
    @Mock
    private ISalaryRepo salRepo;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtService jwtService;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    
//    @Test
//    void assignDeptToEmp_ValidEmployeeAndDepartment_AssignsDepartmentToEmployeeSuccessfully() {
//        // Arrange
//        DepartmentEmployee deptEmp = new DepartmentEmployee();
//        Employee emp = new Employee();
//        Department dept = new Department();
//        
//        deptEmp.setId(new DepartmentEmployeeId(emp, dept));
//        
//        when(empRepo.findByEmpNo(emp.getEmpNo())).thenReturn(emp);
//        when(deptRepo.findByDeptNo(dept.getDeptNo())).thenReturn(dept);
//        
//        // Act
//        String result = service.assignDeptToEmp(deptEmp);
//        
//        // Assert
//        assertEquals("Employee is assigned to the department successfully", result);
//        verify(deptEmpRepo, times(1)).save(deptEmp);
//        verify(empRepo, times(1)).findByEmpNo(emp.getEmpNo());
//        verify(deptRepo, times(1)).findByDeptNo(dept.getDeptNo());
//    }

//    @Test
//    void assignDeptToEmp_InvalidEmployeeOrDepartment_ReturnsErrorMessage() {
//        // Arrange
//        DepartmentEmployee deptEmp = new DepartmentEmployee();
//        Employee emp = new Employee();
//        Department dept = new Department();
//        
//        deptEmp.setId(new DepartmentEmployeeId(emp, dept));
//        
//        when(empRepo.findByEmpNo(emp.getEmpNo())).thenReturn(null);
//        when(deptRepo.findByDeptNo(dept.getDeptNo())).thenReturn(null);
//        
//        // Act
//        String result = service.assignDeptToEmp(deptEmp);
//        
//        // Assert
//        assertEquals("Employee Or Department not exist..", result);
//        verify(deptEmpRepo, times(0)).save(any(DepartmentEmployee.class));
//        verify(empRepo, times(1)).findByEmpNo(emp.getEmpNo());
//        verify(deptRepo, times(1)).findByDeptNo(dept.getDeptNo());
//    }

    @Test
    void deleteEmployeeByEmpNo_EmployeeExists_EmployeeDeletedSuccessfully() {
        // Arrange
        int empNo = 123;
        
        // Act
        service.deleteEmployeeByEmpNo(empNo);
        
        // Assert
        verify(empRepo, times(1)).deleteById(empNo);
    }

    @Test
    void getCountOfWomenEmployess_ReturnsCountOfWomenEmployees() {
        // Arrange
        String gender = "female";
        int count = 10;
        when(empRepo.countByGender(gender)).thenReturn(count);
        
        // Act
        int result = service.getCountOfWomenEmployess(gender);
        
        // Assert
        assertEquals(count, result);
        verify(empRepo, times(1)).countByGender(gender);
    }

    @Test
    void getWomenEmployeesByGender_ReturnsListOfWomenEmployees() {
        // Arrange
        String gender = "female";
        List<Employee> employees = new ArrayList<>();
        when(empRepo.findTop800ByGender(gender)).thenReturn(employees);
        
        // Act
        List<Employee> result = service.getWomenEmployeesByGender(gender);
        
        // Assert
        assertEquals(employees, result);
        verify(empRepo, times(1)).findTop800ByGender(gender);
    }

    @Test
    void getCountOfEmployeesJoinedAfterYear_ReturnsCountOfEmployeesJoinedAfterYear() {
        // Arrange
        int year = 2022;
        Date startDate = Date.valueOf(year + "-01-01");
        int expectedCount = 5;
        when(empRepo.countByHireDateAfter(startDate)).thenReturn(expectedCount);

        // Act
        int result = service.getCountOfEmployeesJoinedAfterYear(year);

        // Assert
        assertEquals(expectedCount, result);
        verify(empRepo, times(1)).countByHireDateAfter(startDate);
    }
    

    @Test
    void deleteEmployeeByEmpNo_EmployeeDoesNotExist_NoActionTaken() {
        // Arrange
        int empNo = 456;
        doNothing().when(empRepo).deleteById(empNo);

        // Act
        service.deleteEmployeeByEmpNo(empNo);

        // Assert
        verify(empRepo, times(1)).deleteById(empNo);
    }

    @Test
    void getCountOfWomenEmployess_GenderDoesNotExist_ReturnsZero() {
        // Arrange
        String gender = "nonexistent";
        int count = 0;
        when(empRepo.countByGender(gender)).thenReturn(count);

        // Act
        int result = service.getCountOfWomenEmployess(gender);

        // Assert
        assertEquals(count, result);
        verify(empRepo, times(1)).countByGender(gender);
    }
    

    @Test
    void getCountOfWomenEmployess_NullGender_ReturnsZero() {
        // Arrange
        String gender = null;
        int count = 0;
        when(empRepo.countByGender(gender)).thenReturn(count);

        // Act
        int result = service.getCountOfWomenEmployess(gender);

        // Assert
        assertEquals(count, result);
        verify(empRepo, times(1)).countByGender(gender);
    }

    
    
    
}

    

    
    


