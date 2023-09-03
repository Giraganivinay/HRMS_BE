package com.cg.hrms.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.hrms.dto.EmployeeTitleDto;
import com.cg.hrms.dto.ManagerDto;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.DepartmentManagerId;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.entities.TitleId;
import com.cg.hrms.projection.DepartmentReportDto;
import com.cg.hrms.projection.DesignationReportDto;
import com.cg.hrms.repos.IDepartmentEmployeeRepo;
import com.cg.hrms.repos.IDepartmentManagerRepo;
import com.cg.hrms.repos.IDepartmentRepo;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.repos.ISalaryRepo;
import com.cg.hrms.repos.ITitleRepo;
import com.cg.hrms.services.EmployeeConsumerServiceImpl;

@SpringBootTest
public class EmployeeConsumerServiceImplTest {

	@InjectMocks
	private EmployeeConsumerServiceImpl employeeService;

	@Mock
	private IDepartmentEmployeeRepo deptEmprepo;
	@Mock
	private IDepartmentManagerRepo deptMagrepo;
	@Mock
	private IDepartmentRepo deptrepo;
	@Mock
	private IEmployeeRepo empRepo;
	@Mock
	private ISalaryRepo salrepo;
	@Mock
	private ITitleRepo titrepo;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Update last name for the given empno employee.
	@Test
	void testUpdateLastName() {
		// Arrange
		int empNo = 1;
		String lastName = "Prakash";
		Employee employee = new Employee();
		employee.setEmpNo(empNo);
		when(empRepo.findByEmpNo(empNo)).thenReturn(employee);
		when(empRepo.save(any(Employee.class))).thenReturn(employee);

		// Act
		Employee result = employeeService.updateLastName(empNo, lastName);

		// Assert
		verify(empRepo, times(1)).findByEmpNo(empNo);
		verify(empRepo, times(1)).save(employee);
		assertEquals(lastName, result.getLastName());
	}

	// get all the Designations
	@Test
	void testGetAllDesignations() {
		// Arrange
		List<String> expectedDesignations = Arrays.asList("Manager", "Engineer");
		when(titrepo.findAllDistinctTitles()).thenReturn(expectedDesignations);

		// Act
		List<String> result = employeeService.getAllDesignations();

		// Assert
		verify(titrepo, times(1)).findAllDistinctTitles();
		assertEquals(expectedDesignations, result);
	}

	// findByBirthDateBetween
	@Test
	void testGetMidAgeEmployees() {
		// Arrange
		LocalDate currentDate = LocalDate.now();
		LocalDate minBirthDate = currentDate.minusYears(59);
		LocalDate maxBirthDate = currentDate.minusYears(50);

		Date minDate = Date.valueOf(minBirthDate);
		Date maxDate = Date.valueOf(maxBirthDate.plusDays(1));

		Employee employee1 = new Employee();
		employee1.setBirthDate(minDate);

		Employee employee2 = new Employee();
		employee2.setBirthDate(maxDate);

		List<Employee> expectedEmployees = new ArrayList<>();
		expectedEmployees.add(employee1);
		expectedEmployees.add(employee2);

		when(empRepo.findByBirthDateBetween(minDate, maxDate)).thenReturn(expectedEmployees);

		// Act
		List<Employee> result = employeeService.getMidAgeEmployees();

		// Assert
		verify(empRepo, times(1)).findByBirthDateBetween(minDate, maxDate);
		assertEquals(expectedEmployees, result);
	}

	// Check details of those employee who are working as specific title
	@Test
	void testGetEmployeesByTitle() {
		// Arrange
		String title = "Manager";

		Employee employee = new Employee();
		employee.setEmpNo(1);
		employee.setFirstName("John");

		Title title1 = new Title();
		title1.setId(new TitleId(employee, title, Date.valueOf("2022-01-01")));
		title1.setToDate(Date.valueOf("2023-01-01"));

		Title title2 = new Title();
		title2.setId(new TitleId(employee, title, Date.valueOf("2021-01-01")));
		title2.setToDate(null);

		List<Title> titles = new ArrayList<>();
		titles.add(title1);
		titles.add(title2);

		when(titrepo.findById_Title(title)).thenReturn(titles);
		when(empRepo.findById(1)).thenReturn(Optional.of(employee));

		// Act
		List<EmployeeTitleDto> result = employeeService.getEmployeesByTitle(title);

		// Assert
		verify(titrepo, times(1)).findById_Title(title);
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getEmpno());
		assertEquals("John", result.get(0).getName());
		assertEquals(Date.valueOf("2022-01-01"), result.get(0).getFromDate());
		assertEquals(Date.valueOf("2023-01-01"), result.get(0).getToDate());
		assertEquals(title, result.get(0).getDesignation());
	}
	

	// getListByDeptAndYear
    @Test
    public void testGetListByDeptAndYear() {
        DepartmentReportDto departmentReportDto1 = createMockedDepartmentReportDto();
        DepartmentReportDto departmentReportDto2 = createMockedDepartmentReportDto();
        List<DepartmentReportDto> expectedDepartmentReportDTOs = new ArrayList<>();
        expectedDepartmentReportDTOs.add(departmentReportDto1);
        expectedDepartmentReportDTOs.add(departmentReportDto2);

        when(deptrepo.getDetails()).thenReturn(expectedDepartmentReportDTOs);
        List<DepartmentReportDto> actualDepartmentReportDTOs = employeeService.getListByDeptAndYear();
        assertEquals(expectedDepartmentReportDTOs, actualDepartmentReportDTOs);
    }

 

    // create a mocked DepartmentReportDto
    private DepartmentReportDto createMockedDepartmentReportDto() {
        return null;
    }

    // getDesignationList
    @Test
    public void testGetDesignationList() {
        DesignationReportDto designationReportDto1 = createMockedDesignationReportDto();
        DesignationReportDto designationReportDto2 = createMockedDesignationReportDto();
        List<DesignationReportDto> expectedDesignationReportDTOs = new ArrayList<>();
        expectedDesignationReportDTOs.add(designationReportDto1);
        expectedDesignationReportDTOs.add(designationReportDto2);
        when(titrepo.getDesignationDetails()).thenReturn(expectedDesignationReportDTOs);
        List<DesignationReportDto> actualDesignationReportDTOs = employeeService.getDesignationList();
        assertEquals(expectedDesignationReportDTOs, actualDesignationReportDTOs);
    }
    
    // create a mocked DesignationReportDto
    private DesignationReportDto createMockedDesignationReportDto() {
        return null;
    }

 

}

