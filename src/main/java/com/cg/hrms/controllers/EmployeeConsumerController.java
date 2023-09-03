package com.cg.hrms.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.dto.EmployeeTitleDto;
import com.cg.hrms.dto.ManagerDto;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.projection.DepartmentReportDto;
import com.cg.hrms.projection.DesignationReportDto;
import com.cg.hrms.services.IEmployeeConsumerService;

@RestController
@RequestMapping("/api/v1/employeehrmsconsumer")
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class EmployeeConsumerController {

	@Autowired
	private IEmployeeConsumerService service;

	// Update last name for the given empno employee.
	@PutMapping("/employee/{empno}")
	public ResponseEntity<Employee> updateLastName(@PathVariable Integer empno, @RequestParam String lastName) {
		Employee updatedEmployee = service.updateLastName(empno, lastName);
		if (updatedEmployee != null) {
			return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Get details of manager [id, name, dob, and department name] who are manager.
	@GetMapping("/manager")
	public ResponseEntity<List<ManagerDto>> getManager() {
		return new ResponseEntity<>(service.getManagers(), HttpStatus.OK);
	}

	@GetMapping("/midageemp")
	public ResponseEntity<List<Employee>> getMidAgeEmployees() {
		return new ResponseEntity<>(service.getMidAgeEmployees(), HttpStatus.OK);
	}

	@GetMapping("/designations")
	public ResponseEntity<List<String>> getAllDesignations() {
		return new ResponseEntity<>(service.getAllDesignations(), HttpStatus.OK);
	}

	@GetMapping("/manager/{fromdate}")
	public ResponseEntity<List<Employee>> getManagersAfterDate(@PathVariable("fromdate") Date fromDate) {
		return new ResponseEntity<>(service.getManagersAfterDate(fromDate), HttpStatus.OK);
	}

	//
	@GetMapping("/employees/department/{deptno}/fromdate/{fromdate}")
	public ResponseEntity<List<EmployeeDto>> getEmployeesInDepartmentWorkingInYear(@PathVariable("deptno") String deptNo,
			@PathVariable("fromdate") Date fromDate) {
		return new ResponseEntity<>(service.getEmployeesByDepartmentAndFromDate(deptNo, fromDate), HttpStatus.OK);
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<EmployeeTitleDto>> getEmployeesByTitle(@PathVariable("title") String title) {
		return new ResponseEntity<>(service.getEmployeesByTitle(title), HttpStatus.OK);
	}
	
	@GetMapping("/department/details")
	public ResponseEntity<List<DepartmentReportDto>> getDepartmentwiseDetails(){
		return new ResponseEntity<>(service.getListByDeptAndYear(), HttpStatus.OK);
	}
	
	@GetMapping("/designation/details")
	public ResponseEntity<List<DesignationReportDto>> getDesignationWiseDetails(){
		return new ResponseEntity<>(service.getDesignationList(), HttpStatus.OK);
	}
}

