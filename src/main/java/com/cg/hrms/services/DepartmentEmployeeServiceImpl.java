package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IDepartmentEmployeeRepo;

import jakarta.transaction.Transactional;

@Service
public class DepartmentEmployeeServiceImpl implements IDepartmentEmployeeService {

	@Autowired
	IDepartmentEmployeeRepo repo;

	// 1.Add new DepartmentEmployee object in Data Base
	@Override
	public DepartmentEmployee addDeptEmp(DepartmentEmployee deptEmp) {
		return repo.save(deptEmp);
	}

	// 2.Fetch all DepartmentEmployee objects
	@Override
	public List<DepartmentEmployee> getAllDeptEmps() {
		return repo.findAll();
	}

	// 3.Fetch DepartmentEmployee by empNo and deptNo
	@Override
	public DepartmentEmployee getDeptEmpByEmpNoDeptNo(Employee empNo, Department deptNo) {
		return repo.findById_EmpNoAndId_DeptNo(empNo, deptNo);
	}

	// 4.Fetch DepartmentEmployee objects by deptno and fromDate
	@Override
	public List<DepartmentEmployee> getDeptEmpByDeptNoAndFromDate(String deptNo, Date fromDate) {
		Department dept = new Department();
		dept.setDeptNo(deptNo);
		return repo.findById_DeptNoAndFromDate(dept, fromDate);
	}

	// 5.Fetch DepartmentEmployee objects by id and fromDate
	@Override
	public DepartmentEmployee getDeptEmpByEmpNoAndFromDate(int empNo, Date fromDate) {
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		return repo.findById_EmpNoAndFromDate(emp, fromDate);
	}

	// 6.Fetch DepartmentEmployee objects by id ,deptno and fromDate
	@Override
	public List<DepartmentEmployee> getDeptEmpByEmpNoDeptNoAndFromDate(Employee empNo, Department deptNo,
			Date fromDate) {
		return repo.findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo, fromDate);
	}

	// ----------------------------updating---------------------------
//  7.Update DepartmentEmployee details for the given empNo and deptNo
	@Override
	public String updateDeptEmpByEmpNoAndDeptNo(Employee empNo, Department deptNo, DepartmentEmployee deptEmp) {
		DepartmentEmployee existingDepartmentEmployee = repo.findById_EmpNoAndId_DeptNo(empNo, deptNo);

		if (existingDepartmentEmployee != null) {
			existingDepartmentEmployee.setFromDate(deptEmp.getFromDate());
			existingDepartmentEmployee.setToDate(deptEmp.getToDate());

			repo.save(existingDepartmentEmployee);
			return "Department Employee updated successfully.";
		}
		throw new CustomException("Department Employee not found");
	}

	// 8.Update DepartmentEmployee details for the given empNo and fromDate
	@Override
	public String updateDeptEmpByEmpNoAndFromDate(Employee empNo, Date fromDate, DepartmentEmployee deptEmp) {
		DepartmentEmployee existingDepartmentEmployee = repo.findById_EmpNoAndFromDate(empNo, fromDate);

		if (existingDepartmentEmployee != null) {
			existingDepartmentEmployee.setFromDate(deptEmp.getFromDate());
			existingDepartmentEmployee.setToDate(deptEmp.getToDate());

			repo.save(existingDepartmentEmployee);
			return "Department Employee updated successfully";
		}
		throw new CustomException("DepartmentEmployee Record not found");
	}

	// 9.Update DepartmentEmployee details for the given deptNo and fromDate
	@Override
	public List<DepartmentEmployee> updateDeptEmpByDeptNoAndFromDate(Department deptNo, Date fromDate) {
		List<DepartmentEmployee> existingDepartmentEmployee = repo.findById_DeptNoAndFromDate(deptNo, fromDate);

		if (existingDepartmentEmployee != null) {
			return repo.saveAll(existingDepartmentEmployee);

		}
		throw new CustomException("Department Employee not found");
	}

	// 10.Update DepartmentEmployee by empNo, deptNo and fromDate
	@Override
	public String updateDeptEmpByEmpNoDeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate,
			DepartmentEmployee deptEmp) {
		DepartmentEmployee existingDepartmentEmployee = repo.findUpdateById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo,
				fromDate);

		if (existingDepartmentEmployee != null) {
			existingDepartmentEmployee.setFromDate(deptEmp.getFromDate());
			existingDepartmentEmployee.setToDate(deptEmp.getToDate());
			repo.save(existingDepartmentEmployee);
			return "Department Employee updated successfully";

		}
		throw new CustomException("Department Employee not found");
	}

//	---------------------delete---------------------

	// 11.Delete DepartmentEmployee by empNo and deptNo
	@Override
	@Transactional
	public String deleteDeptEmpByEmpNoAndDeptNo(int empNo, String deptNo) {
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		Department dept = new Department();
		dept.setDeptNo(deptNo);
		DepartmentEmployee deptEmp = repo.findById_EmpNoAndId_DeptNo(emp, dept);
		if (deptEmp != null) {
			repo.deleteById_EmpNoAndId_DeptNo(emp, dept);
			return "Department employee deleted successfully";
		} else {
			throw new CustomException("Validation Failed, Deleting is unsuccessful");
		}
	}

	// 12.Delete DepartmentEmployee by empNo and fromDate
	@Override
	@Transactional
	public DepartmentEmployee deleteDeptEmpByEmpNoAndFromDate(int empNo, Date fromDate) {
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		DepartmentEmployee deletedDeptEmp = repo.findById_EmpNoAndFromDate(emp, fromDate);
		if (deletedDeptEmp != null) {
			repo.delete(deletedDeptEmp);
		}
		throw new CustomException("Department employee not found..");
	}

	// 13.Delete DepartmentEmployee by deptNo and fromDate
	@Override
	@Transactional
	public List<DepartmentEmployee> deleteDeptEmpByDeptNoAndFromDate(String deptNo, Date fromDate) {
		Department dept = new Department();
		dept.setDeptNo(deptNo);
		List<DepartmentEmployee> list = repo.deleteById_DeptNoAndFromDate(dept, fromDate);
		if (list.size() > 0) {
			return list;
		} else {
			throw new CustomException("No records with given values present...");
		}
	}

	// 14.Delete DepartmentEmployee by empNo, deptNo and fromDate
	@Override
	@Transactional
	public List<DepartmentEmployee> deleteDeptEmpByEmpNoDeptNoAndFromDate(int empNo, String deptNo, Date fromDate) {
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		Department dept = new Department();
		dept.setDeptNo(deptNo);
		List<DepartmentEmployee> list = repo.deleteById_EmpNoAndId_DeptNoAndFromDate(emp, dept, fromDate);
		if (list.size() > 0) {
			return list;
		} else {
			throw new CustomException("No records with given values present...");
		}
	}

}
