package com.cg.hrms.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.*;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Salary;
import com.cg.hrms.entities.SalaryId;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.repos.ISalaryRepo;
import com.cg.hrms.services.SalaryServiceImpl;

@SpringBootTest
public class SalaryServiceTest {

	@InjectMocks
	private SalaryServiceImpl SalService;

	@Mock
	private ISalaryRepo SalRepo;
	@Mock
	private IEmployeeRepo EmpRepo;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// 1------- Add new Sal
	@Test
	public void testAddSalary() {
		Employee employee = new Employee();
		employee.setEmpNo(1);

		SalaryId salaryId = new SalaryId();
		salaryId.setEmpNo(employee);
		salaryId.setFromDate(Date.valueOf("2023-01-01"));

		Salary salary = new Salary();
		salary.setId(salaryId);
		salary.setSalary(5000);

		when(SalRepo.findById(salaryId)).thenReturn(Optional.of(salary));
		when(SalRepo.save(salary)).thenReturn(salary);

		String result = SalService.addSalary(salary);

		assertEquals("New Salary details  added Successfully", result);
		verify(SalRepo, times(1)).findById(salaryId);
		verify(SalRepo, times(1)).save(salary);
	}

	// 2-------- fetch all
	@Test
	public void testGetAllSalaries() {
		SalaryId salaryId1 = new SalaryId();

		Salary salary1 = new Salary();
		salary1.setId(salaryId1);

		SalaryId salaryId2 = new SalaryId();

		Salary salary2 = new Salary();
		salary2.setId(salaryId2);

		List<Salary> salaries = Arrays.asList(salary1, salary2);

		when(SalRepo.findAll()).thenReturn(salaries);

		List<Salary> result = SalService.getAllSalaries();

		assertEquals(salaries.size(), result.size());
		assertEquals(salaries, result);
		verify(SalRepo, times(1)).findAll();
	}

	// 3---------- Search salaries by from date, empno

//	@Test
//	public void testGetSalaryById_ExistingEmployee() {
//		int empNo = 1;
//		Date fromDate = Date.valueOf("2023-01-01");
//
//		Employee employee = new Employee();
//		employee.setEmpNo(empNo);
//
//		Salary salary = new Salary();
//
//		when(EmpRepo.findById(empNo)).thenReturn(Optional.of(employee));
//		when(SalRepo.findById_EmpNoAndId_FromDate(employee, fromDate)).thenReturn(salary);
//
//		Salary result = SalService.getSalaryById(empNo, fromDate);
//
//		assertEquals(salary, result);
//		verify(EmpRepo, times(1)).findById(empNo);
//		verify(SalRepo, times(1)).findById_EmpNoAndId_FromDate(employee, fromDate);
//	}

	// 4------ Fetch all salary objects by from date

	@Test
	public void testGetAllSalariesFromDate() {
		Date fromDate = Date.valueOf("2023-01-01");

		SalaryId salaryId1 = new SalaryId();

		Salary salary1 = new Salary();
		salary1.setId(salaryId1);
		salary1.setSalary(5000);

		SalaryId salaryId2 = new SalaryId();

		Salary salary2 = new Salary();
		salary2.setId(salaryId2);
		salary2.setSalary(6000);

		List<Salary> salaries = Arrays.asList(salary1, salary2);

		when(SalRepo.findById_FromDateAfter(fromDate)).thenReturn(salaries);

		List<Salary> result = SalService.getAllSalariesFromDate(fromDate);

		assertEquals(salaries.size(), result.size());
		assertEquals(salaries, result);
		verify(SalRepo, times(1)).findById_FromDateAfter(fromDate);
	}

	// 5----------- Fetch all salary objects by empno
	 

//	@Test
//    public void testGetAllSalariesByEmpNo() {
//        int empNo = 1;
//
//        Employee employee = new Employee();
//        employee.setEmpNo(empNo);
//
//        Salary salary1 = new Salary();
//
//        Salary salary2 = new Salary();
//
//        List<Salary> salaries = Arrays.asList(salary1, salary2);
//
//        when(SalRepo.findById_EmpNo(employee)).thenReturn(salaries);
//
//        List<Salary> result = SalService.getAllSalariesByEmpNo(empNo);
//
//        assertEquals(salaries.size(), result.size());
//        assertEquals(salaries, result);
//        verify(SalRepo, times(1)).findById_EmpNo(employee);
//    }

	// 6----------- Fetch all salary objects by salary

	@Test
	public void testGetAllSalariesBetween() {
		// Arrange
		int minSalary = 3000;
		int maxSalary = 6000;

		Salary salary1 = new Salary();
		salary1.setSalary(4000);

		Salary salary2 = new Salary();
		salary2.setSalary(5000);

		List<Salary> salaries = Arrays.asList(salary1, salary2);

		when(SalRepo.findBySalaryBetween(minSalary, maxSalary)).thenReturn(salaries);

		List<Salary> result = SalService.getAllSalariesBetween(minSalary, maxSalary);

		assertEquals(salaries.size(), result.size());
		assertEquals(salaries, result);
		verify(SalRepo, times(1)).findBySalaryBetween(minSalary, maxSalary);
	}

	// 7-------------- Update salary details for the given fromdate

	@Test
	public void testUpdateSalaryByFromDate() {
		Date fromDate = Date.valueOf("2023-01-01");

		Salary salary = new Salary();
		salary.setSalary(5000);
		salary.setToDate(Date.valueOf("2023-02-01"));

		Salary salary1 = new Salary();
		salary1.setSalary(7000);
		salary1.setToDate(Date.valueOf("2023-01-31"));

		Salary salary2 = new Salary();
		salary2.setSalary(9000);
		salary2.setToDate(Date.valueOf("2023-02-28"));

		List<Salary> salaries = Arrays.asList(salary1, salary2);

		when(SalRepo.findById_FromDate(fromDate)).thenReturn(salaries);
		when(SalRepo.saveAll(salaries)).thenReturn(salaries);

		List<Salary> result = SalService.updateSalaryByFromDate(fromDate, salary1);

		assertEquals(salaries.size(), result.size());
		assertEquals(salary1.getSalary(), result.get(0).getSalary());
		assertEquals(salary1.getToDate(), result.get(0).getToDate());
		assertEquals(salary1.getSalary(), result.get(1).getSalary());
		assertEquals(salary1.getToDate(), result.get(1).getToDate());
		verify(SalRepo, times(1)).findById_FromDate(fromDate);
		verify(SalRepo, times(1)).saveAll(salaries);
	}

	@Test
	public void testUpdateSalaryByFromDate_NoRecords() {
		Date fromDate = Date.valueOf("2023-01-01");

		when(SalRepo.findById_FromDate(fromDate)).thenReturn(Arrays.asList());

		assertThrows(CustomException.class, () -> SalService.updateSalaryByFromDate(fromDate, new Salary()));
		verify(SalRepo, times(1)).findById_FromDate(fromDate);
		verify(SalRepo, never()).saveAll(any());
	}

	// 8 ---------- Update salary details for the given empid

//     @Test
	public void testUpdateSalaryByEmpNo() {
		int empNo = 1;
		Salary salary = new Salary();
		salary.setSalary(5000);
		salary.setToDate(Date.valueOf("2023-07-01"));

		Employee emp = new Employee();
		emp.setEmpNo(empNo);

		List<Salary> salaries = new ArrayList<>();

		when(SalRepo.findById_EmpNo(emp)).thenReturn(salaries);
		when(SalRepo.saveAll(salaries)).thenReturn(salaries);

		List<Salary> result = SalService.updateSalaryByEmpNo(empNo, salary);

		assertNotNull(result);
		assertEquals(salaries.size(), result.size());
		verify(SalRepo, times(1)).findById_EmpNo(emp);
		verify(SalRepo, times(1)).saveAll(salaries);
	}

	// 9----------- Update salary by empno, fromdate

//    @Test
//    public void testUpdateSalaryByEmpNoAndFromDate() {
//        // Arrange
//        int empNo = 1;
//        Date fromDate = Date.valueOf("2023-01-01");
//
//        SalaryId salaryId = new SalaryId();
//        Employee employee = new Employee();
//        employee.setEmpNo(empNo);
//        salaryId.setEmpNo(employee);
//        salaryId.setFromDate(fromDate);
//
//        Salary salary = new Salary();
//        salary.setSalary(5000);
//        salary.setToDate(Date.valueOf("2023-06-30"));
//
//        Salary existingSalary = new Salary();
//        existingSalary.setId(salaryId);
//        existingSalary.setSalary(4000);
//        existingSalary.setToDate(Date.valueOf("2023-05-31"));
//
//        Optional<Salary> optionalSalary = Optional.of(existingSalary);
//
//        when(SalRepo.findById(salaryId)).thenReturn(optionalSalary);
//        when(SalRepo.save(existingSalary)).thenReturn(existingSalary);
//
//        String result = SalService.updateSalaryByEmpNoAndFromDate(empNo, fromDate, salary);
//
//        assertEquals("Salary updated Successfully", result);
//        assertEquals(salary.getSalary(), existingSalary.getSalary());
//        assertEquals(salary.getToDate(), existingSalary.getToDate());
//        verify(SalRepo, times(1)).findById(salaryId);
//        verify(SalRepo, times(1)).save(existingSalary);
//    }

	// 10--------------- Delete salary by empno and from date

//    @Test
//    public void testDeleteSalaryByEmpNoAndFromDate() {
//        int empNo = 1;
//        Date fromDate = Date.valueOf("2023-01-01");
//
//        Employee employee = new Employee();
//        employee.setEmpNo(empNo);
//
//        Salary salary = new Salary();
//
//        when(SalRepo.findById_EmpNoAndId_FromDate(employee, fromDate)).thenReturn(salary);
//
//        String result = SalService.deleteSalryByEmpNoAndFromDate(empNo, fromDate);
//
//        assertEquals("Salary deleted Successfully", result);
//        verify(SalRepo, times(1)).findById_EmpNoAndId_FromDate(employee, fromDate);
//        verify(SalRepo, times(1)).deleteById_EmpNoAndId_FromDate(employee, fromDate);
//    }
//    @Test
//    public void testDeleteSalaryByEmpNoAndFromDate_NoRecord() {
//        int empNo = 1;
//        Date fromDate = Date.valueOf("2023-01-01");
//
//        Employee employee = new Employee();
//        employee.setEmpNo(empNo);
//
//        when(SalRepo.findById_EmpNoAndId_FromDate(employee, fromDate)).thenReturn(null);
//
//        CustomException exception = 
//            org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, 
//                () -> SalService.deleteSalryByEmpNoAndFromDate(empNo, fromDate));
//
//        assertEquals("No records for given emp no...", exception.getMessage());
//        verify(SalRepo, times(1)).findById_EmpNoAndId_FromDate(employee, fromDate);
//        verify(SalRepo, never()).deleteById_EmpNoAndId_FromDate(employee, fromDate);
//    }

	// 11------ Delete salary by empno

	@Test
	public void testDeleteSalaryByEmpNo() {
		Employee employee = new Employee();
		employee.setEmpNo(1);

		List<Salary> salaries = new ArrayList<>();

		when(SalRepo.deleteById_EmpNo(employee)).thenReturn(salaries);

		List<Salary> result = SalService.deleteSalaryByEmpNo(employee);

		assertEquals(salaries, result);
		verify(SalRepo, times(1)).deleteById_EmpNo(employee);
	}
	

	// 12 ----------- Delete salary by fromdate

	@Test
	public void testDeleteSalaryByFromDate() {
		Date fromDate = Date.valueOf("2023-01-01");

		Salary salary1 = new Salary();

		Salary salary2 = new Salary();

		List<Salary> deletedSalaries = Arrays.asList(salary1, salary2);

		when(SalRepo.deleteById_FromDate(fromDate)).thenReturn(deletedSalaries);

		List<Salary> result = SalService.deleteSalaryByFromDate(fromDate);

		assertEquals(deletedSalaries.size(), result.size());
		assertEquals(deletedSalaries, result);
		verify(SalRepo, times(1)).deleteById_FromDate(fromDate);
	}
}
