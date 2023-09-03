package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IDepartmentManagerRepo;


@Service
public class DepartmentManagerImpl implements IDepartmentManagerService {

	@Autowired
	IDepartmentManagerRepo repo;

	// 1.Dept_manager Add new department manager object in DB
	@Override
	public DepartmentManager addDeptManager(DepartmentManager deptManager) {
		if (deptManager.getId().getEmpNo() != null && deptManager.getId().getDeptNo() != null
				&& deptManager.getFromDate() != null && deptManager.getToDate() != null) {
			return repo.save(deptManager);
		}
		return null;
	}

	// 2.search department manager by empNo and deptNo
	@Override
	public DepartmentManager getDeptManagerById(int empNo, String deptNo) {
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		Department dept = new Department();
		dept.setDeptNo(deptNo);
		return repo.findById_EmpNoAndId_DeptNo(emp, dept);
	}

	// 3.Fetch all department manager objects
	@Override
	public List<DepartmentManager> getAllDeptManagers() {

		return repo.findAll();
	}

	// 4.Fetch all department manager objects by , deptno and from date
	@Override
	public List<DepartmentManager> getDeptManagersByDeptNoAndFromDate(String deptNo, Date fromDate) {
		Department dept = new Department();
		dept.setDeptNo(deptNo);
		return repo.findById_DeptNoAndFromDate(dept, fromDate);
	}

	// 5.Fetch all deptmanager objects by , id and from date
	@Override
	public Optional<DepartmentManager> getDeptManagersByEmpNoAndFromDate(int empNo, Date fromDate) {
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		return repo.findById_EmpNoAndFromDate(emp, fromDate);
	}

	// 6.Fetch all deptmanager objects by , empNo ,deptno,and from date
	@Override
	public Optional<DepartmentManager> getDeptManagersByEmpNoDeptNoAndFromDate(int empNo, String deptNo,
			Date fromDate) {
		Employee emp = new Employee();
		emp.setEmpNo(empNo);
		Department dept = new Department();
		dept.setDeptNo(deptNo);
		return repo.findById_EmpNoAndId_DeptNoAndFromDate(emp, dept, fromDate);
	}

	// -----------------------update-----------------------------

	// 7.Update deptmanager details for the given empno and deptno
	@Override
	public String updateDepartmentManagerByEmpNoAndDeptNo(Employee empNo, Department deptNo,
			DepartmentManager departmentManager) {
		DepartmentManager existingManager = repo.findById_EmpNoAndId_DeptNo(empNo, deptNo);
		if (existingManager == null) {
			throw new CustomException("DepartmentManager not found");
		}
		existingManager.setFromDate(departmentManager.getFromDate());
		existingManager.setToDate(departmentManager.getToDate());
		repo.save(existingManager);
		return "DepartmentManager updated successfully";
	}

	// 8.Update deptmanager details for the given empno and fromdate
	@Override
	public String updateDepartmentManagerByEmpNoAndFromDate(Employee empNo, Date fromDate,
			DepartmentManager departmentManager) {
		Optional<DepartmentManager> existingManager = repo.findById_EmpNoAndFromDate(empNo, fromDate);
		if (existingManager.isPresent()) {
			DepartmentManager e = existingManager.get();
			e.setToDate(departmentManager.getToDate());
			repo.save(e);
			return "DepartmentManager updated successfully";
		} else {
			throw new CustomException("DepartmentManager not found");
		}
	}

	// 9.Update deptmanager details for the given deptno and fromdate
	@Override
	public List<DepartmentManager> updateDeptManagerByDeptNoAndFromDate(Department deptNo, Date fromdate) {

		return repo.findById_DeptNoAndFromDate(deptNo, fromdate);
	}

	// 10.Update deptmanager by empno, deptno, fromdate
	@Override
	public String updateDepartmentManagerByEmpNoDeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate,
			DepartmentManager departmentManager) {
		Optional<DepartmentManager> existingManager = repo.findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo,
				fromDate);
		if (existingManager.isPresent()) {
			DepartmentManager e = existingManager.get();
			e.setToDate(departmentManager.getToDate());
			repo.save(e);
			return "DepartmentManager updated successfully";
		} else {
			throw new CustomException("DepartmentManager not found");
		}
	}

//--------------------------DELETE------------------------------

	// 11.delete by empno and deptno
	@Override
	public String deleteDepartmentManagerByEmpNoAndDeptNo(Employee empNo, Department deptNo) {
		DepartmentManager deptEmp = repo.findById_EmpNoAndId_DeptNo(empNo, deptNo);
		if (deptEmp != null) {
			repo.delete(deptEmp);
		} else {
			System.out.println("Validation Failed, Deleting is unsuccessful");
		}
		throw new CustomException("DepartmentManager not found");
	}

	// 12.delete by empno and fromdate
	@Override
	public DepartmentManager deleteDepartmentManagerByEmpNoAndFromDate(Employee empNo, Date fromDate) {
		Optional<DepartmentManager> deletedDepartmentManager = repo.findById_EmpNoAndFromDate(empNo, fromDate);
		if (deletedDepartmentManager.isPresent()) {
			repo.delete(deletedDepartmentManager.get());
			return deletedDepartmentManager.get();
		} else {
			throw new CustomException("DepartmentManager not found");
		}
	}

	// 13.Delete deptmanager by deptno and fromdate
	@Override
	public List<DepartmentManager> deleteDepartmentManagersByDeptNoAndFromDate(Department deptNo, Date fromDate) {
		List<DepartmentManager> departmentManagers = repo.findById_DeptNoAndFromDate(deptNo, fromDate);
		repo.deleteAll(departmentManagers);
		return departmentManagers;
	}

	// 14.Delete deptmanager by empno, deptno and fromdate
	@Override
	public DepartmentManager deleteDepartmentManagerByEmpNoDeptNoAndFromDate(Employee empNo, Department deptNo,
			Date fromDate) {
		Optional<DepartmentManager> departmentManagers = repo.findById_EmpNoAndId_DeptNoAndFromDate(empNo, deptNo,
				fromDate);
		if (!departmentManagers.isEmpty()) {
			DepartmentManager deletedDepartmentManager = departmentManagers.get();
			repo.delete(deletedDepartmentManager);
			return deletedDepartmentManager;
		} else {
			throw new CustomException("DepartmentManager not found");		}
	}

}
