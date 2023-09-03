package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Salary;

public interface ISalaryService {
	
	// get salary by empNo and fromDate
	Salary getSalaryById(int empNo, Date fromDate);

	// Add new salary object in DB
	String addSalary(Salary s);
	
	// Fetch all salary objects
	List<Salary> getAllSalaries();
	
	// Fetch all salary objects by from date
	List<Salary> getAllSalariesFromDate(Date fromDate);
	
	// Fetch all salary objects by empno
	List<Salary> getAllSalariesByEmpNo(int empNo);
	
	// Fetch all salary objects by salary
	List<Salary> getAllSalariesBetween(int minSalary, int maxSalary);
	
	// Update salary details for the given fromdate
	List<Salary> updateSalaryByFromDate(Date fromDate, Salary salary);
	
	// Update salary details for the given empid
	List<Salary> updateSalaryByEmpNo(int empNo, Salary salary);
	
	// Update salary by empno, fromdate
	String updateSalaryByEmpNoAndFromDate(int empNo, Date fromDate, Salary salary);
	
	// Delete salary by empno and  from date
	String deleteSalryByEmpNoAndFromDate(int empNo, Date fromDate);
	
	// Delete salary by empno
	List<Salary> deleteSalaryByEmpNo(Employee empNo);
	
	// Delete salary by fromdate
	List<Salary> deleteSalaryByFromDate(Date fromDate);
	
}
