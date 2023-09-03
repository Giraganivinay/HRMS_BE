package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;

public interface IDepartmentManagerService {

	// 1.Dept_manager Add new department manager object in DB
	DepartmentManager addDeptManager(DepartmentManager deptManager);

	// 2.search department manager by empNo and deptNo
	DepartmentManager getDeptManagerById(int empNo, String deptNo);

	// 3.Fetch all department manager objects
	List<DepartmentManager> getAllDeptManagers();

	// 4.Fetch all department manager objects by , deptno and from date
	List<DepartmentManager> getDeptManagersByDeptNoAndFromDate(String deptNo, Date fromDate);

	// 5.Fetch all deptmanager objects by , id and from date
	Optional<DepartmentManager> getDeptManagersByEmpNoAndFromDate(int empNo, Date fromDate);

	// 6.Fetch all deptmanager objects by , empNo ,deptno,and from date
	Optional<DepartmentManager> getDeptManagersByEmpNoDeptNoAndFromDate(int empNo, String deptNo,
			Date fromDate);

	// 7.Update deptmanager details for the given empno and deptno
	String updateDepartmentManagerByEmpNoAndDeptNo(Employee empNo, Department deptNo,
			DepartmentManager departmentManager);

	// 8.Update deptmanager details for the given empno and fromdate
	String updateDepartmentManagerByEmpNoAndFromDate(Employee empNo, Date fromDate,
			DepartmentManager departmentManager);

	// 9.Update deptmanager details for the given deptno and fromdate
	List<DepartmentManager> updateDeptManagerByDeptNoAndFromDate(Department deptno, Date fromdate);

	// 10.Update deptmanager by empno, deptno, fromdate
	String updateDepartmentManagerByEmpNoDeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate,
			DepartmentManager departmentManager);

	// 11. delete by empno and deptno
	String deleteDepartmentManagerByEmpNoAndDeptNo(Employee empNo, Department deptNo);

	// 12. delete by empno and fromdate
	DepartmentManager deleteDepartmentManagerByEmpNoAndFromDate(Employee empNo, Date fromDate);

	// 13.Delete deptmanager by deptno and fromdate
	List<DepartmentManager> deleteDepartmentManagersByDeptNoAndFromDate(Department deptno, Date fromdate);

	// 14.Delete deptmanager by empno, deptno and fromdate
	DepartmentManager deleteDepartmentManagerByEmpNoDeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate);

}
