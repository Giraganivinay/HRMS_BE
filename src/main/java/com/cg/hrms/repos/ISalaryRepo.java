package com.cg.hrms.repos;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Salary;
import com.cg.hrms.entities.SalaryId;

public interface ISalaryRepo extends JpaRepository<Salary, SalaryId> {

	// Search salaries by from date, empno
	Salary findById_EmpNoAndId_FromDate(Employee empNo, Date fromDate);
	
	// Fetch all salary objects by from date
	List<Salary> findById_FromDateAfter(Date fromDate);
	
	// Fetch all salary objects by empno
	List<Salary> findById_EmpNo(Employee empNo);
	
	// Fetch all salary objects by salary
	List<Salary> findBySalaryBetween(int minSalary, int maxSalary);
	
	// Fetch all salary objects by fromdate
	List<Salary> findById_FromDate(Date fromDate);
	
	// Delete salary by empno and  from date
	void deleteById_EmpNoAndId_FromDate(Employee empNo, Date fromDate);
	
	// Delete salary by empno
	List<Salary> deleteById_EmpNo(Employee empNo);
	
	// Delete salary by fromdate
	List<Salary> deleteById_FromDate(Date fromDate);
	
	
}
