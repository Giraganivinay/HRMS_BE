package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Salary;
import com.cg.hrms.entities.SalaryId;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.repos.ISalaryRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalaryServiceImpl implements ISalaryService {

	@Autowired
	ISalaryRepo salRepo;

	@Autowired
	IEmployeeRepo empRepo;

	// Add new salary object in DB
	@Override
	public String addSalary(Salary s) {
		Employee emp = empRepo.findByEmpNo(s.getId().getEmpNo().getEmpNo());
		if(emp == null)
			throw new CustomException("Incorrect employee id");
		
		Optional<Salary> salary = salRepo.findById(s.getId());
		if (!salary.isPresent()) {
			salRepo.save(s);
			return "Salary details  added Successfully";
		} else {
			s.getId().setFromDate(salary.get().getToDate());
			salRepo.save(s);
			return "Salary details  added Successfully";
		}
	}

	// Fetch all salary objects
	@Override
	public List<Salary> getAllSalaries() {
		return salRepo.findAll();
	}

	// Search salaries by from date, empno
	@Override
	public Salary getSalaryById(int empNo, Date fromDate) {
		Optional<Employee> emp = empRepo.findById(empNo);
		if (emp.isPresent()) {
			Employee employee =  new Employee();
			employee.setEmpNo(empNo);
			return salRepo.findById_EmpNoAndId_FromDate(employee, fromDate);
		} else {
			throw new CustomException("Invalid employee Id...");
		}
	}

	// Fetch all salary objects by from date
	@Override
	public List<Salary> getAllSalariesFromDate(Date fromDate) {
		return salRepo.findById_FromDateAfter(fromDate);
	}

	// Fetch all salary objects by empno
	@Override
	public List<Salary> getAllSalariesByEmpNo(int empNo) {
		Optional<Employee> employee = empRepo.findById(empNo);
		if(!employee.isPresent())
			throw new CustomException("Invalid employee Id");
		
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		return salRepo.findById_EmpNo(emp);
	}

	// Fetch all salary objects by salary
	@Override
	public List<Salary> getAllSalariesBetween(int minSalary, int maxSalary) {
		return salRepo.findBySalaryBetween(minSalary, maxSalary);
	}

	// Update salary details for the given fromdate
	public List<Salary> updateSalaryByFromDate(Date fromDate, Salary salary) {
		List<Salary> sal = salRepo.findById_FromDate(fromDate);
		if (!sal.isEmpty()) {
			int salary2 = salary.getSalary();
			Date toDate = salary.getToDate();
			sal.forEach(s -> {
				if (salary2 >= 0 )
					s.setSalary(salary2);
				if (toDate != null)
					s.setToDate(toDate);
			});
			return salRepo.saveAll(sal);
		} else {
			throw new CustomException("No records found...");
		}
	}

	// Update salary details for the given empid
	@Override
	@Transactional
	public List<Salary> updateSalaryByEmpNo(int empNo, Salary salary) {
		if(!empRepo.findById(empNo).isPresent())
			throw new CustomException("Invalid employee Id");
		
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		List<Salary> sal = salRepo.findById_EmpNo(emp);
		if (!sal.isEmpty()) {
			sal.forEach(s -> {
				if (salary.getSalary() >= 0)
					s.setSalary(salary.getSalary());
				if (salary.getToDate() != null)
					s.setToDate(salary.getToDate());
			});
			return salRepo.saveAll(sal);
		} else {
			throw new CustomException("No records found...");
		}
	}

	// Update salary by empno, fromdate
	@Override
	public String updateSalaryByEmpNoAndFromDate(int empNo, Date fromDate, Salary salary) {
		if(!empRepo.findById(empNo).isPresent())
			throw new CustomException("Invalid employee Id");
		
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		Optional<Salary> sal = salRepo.findById(new SalaryId(emp, fromDate));

		if (sal.isPresent()) {
			Salary s = sal.get();
			if (salary.getSalary() >= 0)
				s.setSalary(salary.getSalary());
			if (salary.getToDate() != null)
				s.setToDate(salary.getToDate());
			salRepo.save(s);
			return "Salary updated Successfully";
		} else {
			throw new CustomException("No records for give emp no...");
		}
	}

	// Delete salary by empno and from date
	@Override
	@Transactional
	public String deleteSalryByEmpNoAndFromDate(int empNo, Date fromDate) {
		if(!empRepo.findById(empNo).isPresent())
			throw new CustomException("Invalid employee Id");
		
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		Salary sal = salRepo.findById_EmpNoAndId_FromDate(emp, fromDate);
		if (sal != null) {
			salRepo.deleteById_EmpNoAndId_FromDate(emp, fromDate);
			return "Salary deleted Successfully";
		} else {
			throw new CustomException("No records for give emp no...");
		}

	}

	// Delete salary by empno
	@Override
	@Transactional
	public List<Salary> deleteSalaryByEmpNo(Employee empNo) {
		if(salRepo.findById_EmpNo(empNo).isEmpty())
			throw new CustomException("No records found for given emp no");
		return salRepo.deleteById_EmpNo(empNo);
	}

	// Delete salary by fromdate
	@Override
	@Transactional
	public List<Salary> deleteSalaryByFromDate(Date fromDate) {
		if(salRepo.findById_FromDate(fromDate).isEmpty())
			throw new CustomException("No records found for given fromDate");
		return salRepo.deleteById_FromDate(fromDate);
	}

}
