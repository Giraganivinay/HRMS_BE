package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;

import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.dto.EmployeeTitleDto;
import com.cg.hrms.dto.ManagerDto;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.projection.DepartmentReportDto;
import com.cg.hrms.projection.DesignationReportDto;

public interface IEmployeeConsumerService {

	Employee updateLastName(Integer empNo, String lastName);

	List<ManagerDto> getManagers();

	List<Employee> getMidAgeEmployees();

	List<String> getAllDesignations();

	List<Employee> getManagersAfterDate(Date fromDate);

	//

	List<EmployeeDto> getEmployeesByDepartmentAndFromDate(String deptNo, Date fromDate);

	List<EmployeeTitleDto> getEmployeesByTitle(String title);
	
	List<DepartmentReportDto> getListByDeptAndYear();
	
	List<DesignationReportDto> getDesignationList();

}
