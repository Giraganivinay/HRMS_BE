package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.Employee;

public interface IDepartmentEmployeeService {

	// 1.Add new DepartmentEmployee object in Data Base
	DepartmentEmployee addDeptEmp(DepartmentEmployee deptEmp);

	// 2.Fetch all DepartmentEmployee objects
	List<DepartmentEmployee> getAllDeptEmps();

	// 3.Fetch DepartmentEmployee by empNo and deptNo
	DepartmentEmployee getDeptEmpByEmpNoDeptNo(Employee empNo, Department deptNo);

	// 4.Fetch DepartmentEmployee objects by deptno and fromDate
	List<DepartmentEmployee> getDeptEmpByDeptNoAndFromDate(String deptNo, Date fromDate);

	// 5.Fetch DepartmentEmployee objects by id and fromDate
	DepartmentEmployee getDeptEmpByEmpNoAndFromDate(int empNo, Date fromDate);

	// 6.Fetch DepartmentEmployee objects by id ,deptno and fromDate
	List<DepartmentEmployee> getDeptEmpByEmpNoDeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate);

	// 7.Update DepartmentEmployee details for the given empno and deptno
	String updateDeptEmpByEmpNoAndDeptNo(Employee empNo, Department deptNo, DepartmentEmployee deptEmp);

	// 8.Update DepartmentEmployee details for the given empno and fromDate
	String updateDeptEmpByEmpNoAndFromDate(Employee empNo, Date fromDate, DepartmentEmployee deptEmp);

	// 9.Update DepartmentEmployee details for the given deptno and fromDate
	List<DepartmentEmployee> updateDeptEmpByDeptNoAndFromDate(Department deptNo, Date fromDate);

	// 10.Update DepartmentEmployee by empno, deptno and fromdate
	String updateDeptEmpByEmpNoDeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate,
			DepartmentEmployee deptEmp);

	// 11.Delete DepartmentEmployee by empNo and deptNo
	String deleteDeptEmpByEmpNoAndDeptNo(int empNo, String deptNo);

	// 12.Delete DepartmentEmployee by empNo and fromDate
	DepartmentEmployee deleteDeptEmpByEmpNoAndFromDate(int empNo, Date fromDate);

	// 13.Delete DepartmentEmployee by deptNo and fromDate
	List<DepartmentEmployee> deleteDeptEmpByDeptNoAndFromDate(String deptNo, Date fromDate);

	// 14.Delete DepartmentEmployee by empNo, deptNo and fromDate
	List<DepartmentEmployee> deleteDeptEmpByEmpNoDeptNoAndFromDate(int empNo, String deptNo, Date fromDate);
}
