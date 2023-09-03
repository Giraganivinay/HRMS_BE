package com.cg.hrms.repos;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployeeId;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;

public interface IDepartmentManagerRepo extends JpaRepository<DepartmentManager, DepartmentEmployeeId> {

	DepartmentManager findById_EmpNoAndId_DeptNo(Employee empNo, Department deptNo);

	List<DepartmentManager> findById_DeptNoAndFromDate(Department deptNo, Date fromDate);

	Optional<DepartmentManager> findById_EmpNoAndFromDate(Employee empNo, Date fromDate);

	Optional<DepartmentManager> findById_EmpNoAndId_DeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate);

	List<DepartmentManager> deleteAllById_DeptNoAndFromDate(Department deptNo, Date fromDate);

	DepartmentManager deleteById_EmpNoAndId_DeptNoAndFromDate(Employee empNo, Department deptNo, Date fromDate);

	List<DepartmentManager> findByFromDateGreaterThan(Date fromDate);
	
	
}
