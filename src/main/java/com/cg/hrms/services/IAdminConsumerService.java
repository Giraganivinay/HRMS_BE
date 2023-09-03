package com.cg.hrms.services;

import java.util.List;

import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.exceptions.CustomException;

public interface IAdminConsumerService {


	String assignDeptToEmp(DepartmentEmployee deptEmp);

	String assignManagerToDept(DepartmentManager deptManager);

	String assignTitleToEmp(Title title);

	boolean hasEmployeeJoinedBeforeYear(int empNo, int year);

	String addNewDepartment(Department dept) throws CustomException;

	Employee addNewEmployee(Employee emp);

	String updateSalary(int employeeId, int rating, boolean promoted);

	//

	int getCountOfEmployeesJoinedInLastYears(int noOfYears);

	List<EmployeeDto> getEmployeesJoinedAfterYear(int year);

	List<EmployeeDto> getEmployeesJoinedInLastYears(int noOfYears);

	int getCountOfEmployeesJoinedAfterYear(int year);

	void deleteEmployeeByEmpNo(int empNo);

	List<Employee> getEmployeesByExperience(int noOfYears);

	Integer getCountOfWomenEmployess(String gender);

	List<Employee> getWomenEmployeesByGender(String gender);
}
