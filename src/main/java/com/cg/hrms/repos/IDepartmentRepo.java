package com.cg.hrms.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.hrms.entities.Department;
import com.cg.hrms.projection.DepartmentReportDto;


public interface IDepartmentRepo extends JpaRepository<Department, String> {
	
	Department findByDeptName(String name);

	void deleteByDeptNo(String deptNo);

	void deleteByDeptName(String deptName);
	
	Department findByDeptNo(String deptNo);
	
	// -------------------employee
	@Query("SELECT dept.deptNo as deptNo, dept.deptName as deptName , AVG(sal.salary) as average,  MIN(sal.salary) as min, MAX(sal.salary) as max FROM Department dept JOIN DepartmentEmployee deptemp ON dept.deptNo = deptemp.id.deptNo.deptNo JOIN Employee emp ON deptemp.id.empNo.empNo = emp.empNo JOIN Salary sal  ON emp.empNo = sal.id.empNo.empNo GROUP BY dept.deptNo, dept.deptName")
	List<DepartmentReportDto> getDetails();
	
//	SELECT dp.dept_no as deptNo, dp.dept_name as deptName, avg(s.salary) as average, min(s.salary) as min, max(s.salary) as max FROM employees.departments dp join employees.dept_emp de on dp.dept_no = de.dept_no join employees.employees e on de.emp_no = e.emp_no join salaries s on e.emp_no = s.emp_no group by dp.dept_no
	
	Department findFirstByOrderByDeptNoDesc();

}
