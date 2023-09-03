package com.cg.hrms.repos;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.DepartmentEmployeeId;
import com.cg.hrms.entities.Employee;

public interface IDepartmentEmployeeRepo extends JpaRepository<DepartmentEmployee, DepartmentEmployeeId> {

	// 3.Fetch DepartmentEmployee by empNo and deptNo
	DepartmentEmployee findById_EmpNoAndId_DeptNo(Employee empNo, Department deptNo);

	// 4.Fetch DepartmentEmployee objects by deptno and fromDate
	List<DepartmentEmployee> findById_DeptNoAndFromDate(Department deptNo, Date fromDate);

	// 5.Fetch DepartmentEmployee objects by id and fromDate
	DepartmentEmployee findById_EmpNoAndFromDate(Employee empNo, Date fromDate);

	// 6.Fetch DepartmentEmployee objects by id ,deptno and fromDate
	List<DepartmentEmployee> findById_EmpNoAndId_DeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate);

	// 10.Update DepartmentEmployee by empno, deptno and fromdate
	DepartmentEmployee findUpdateById_EmpNoAndId_DeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate);

	// 11.Delete DepartmentEmployee by empNo and deptNo
	String deleteById_EmpNoAndId_DeptNo(Employee empNo, Department deptNo);

	// 12.Delete DepartmentEmployee by empNo and fromDate
	DepartmentEmployee deleteById_EmpNoAndFromDate(Employee empNo, Date fromDate);

	// 13.Delete DepartmentEmployee by deptNo and fromDate
	List<DepartmentEmployee> deleteById_DeptNoAndFromDate(Department deptNo, Date fromDate);

	// 14.Delete DepartmentEmployee by empNo, deptNo and fromDate
	List<DepartmentEmployee> deleteById_EmpNoAndId_DeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate);

}
