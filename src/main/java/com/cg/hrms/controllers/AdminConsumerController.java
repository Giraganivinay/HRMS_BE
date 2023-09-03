package com.cg.hrms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.services.IAdminConsumerService;

@RestController
@RequestMapping("/api/v1/adminhrmsconsumer")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminConsumerController {

	@Autowired
	private IAdminConsumerService service;

	//  assigned employee to the department.
	@PostMapping("/assigndept")
	public ResponseEntity<String> assignDept(@RequestBody DepartmentEmployee deptEmp) {
		return new ResponseEntity<>(service.assignDeptToEmp(deptEmp), HttpStatus.OK);
	}

	// Assign manager to specific department from fromdate to end date.
	@PostMapping("/assignmgr")
	public ResponseEntity<String> assignManagerToDept(@RequestBody DepartmentManager deptManager) {
		return new ResponseEntity<>(service.assignManagerToDept(deptManager), HttpStatus.OK);
	}

	// Assign employee with title [designation] from start to end date. [Assuming
	// that by default employee will be eligible for promotion after 5 years]
	@PostMapping("/assigntitle")
	public ResponseEntity<String> assignTitleToEmp(@RequestBody Title title) {
		return new ResponseEntity<>(service.assignTitleToEmp(title), HttpStatus.OK);
	}

	// Add new department
	@PostMapping("/department")
//	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public ResponseEntity<String> addDeptartment(@RequestBody Department dept) {
		return new ResponseEntity<>(service.addNewDepartment(dept), HttpStatus.OK);
	}

	// Add new employee
	@PostMapping("/employee")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp) {
		return new ResponseEntity<>(service.addNewEmployee(emp), HttpStatus.OK);
	}

	// Update the salary for employee when employee is appraised each year depending
	// on rating.
	@PutMapping("/salaries/{empno}")
	public ResponseEntity<String> updateSalary(@PathVariable int empno, @RequestParam int rating,
			@RequestParam boolean promoted) {
		service.updateSalary(empno, rating, promoted);
		return new ResponseEntity<>("Employee salary updated successfully", HttpStatus.OK);
	}

	//

	@GetMapping("/employees/count/{noofyears}")
	public ResponseEntity<Integer> getCountOfEmployeesJoinedInLastYears(@PathVariable("noofyears") int noOfYears) {
		return new ResponseEntity<>(service.getCountOfEmployeesJoinedInLastYears(noOfYears), HttpStatus.OK);
	}

	@GetMapping("/employees/{year}")
	public ResponseEntity<List<EmployeeDto>> getEmployeesJoinedAfterYear(@PathVariable int year) {
		return ResponseEntity.ok(service.getEmployeesJoinedAfterYear(year));
	}

	@GetMapping("/employees/joined-after/{noOfYears}")
	public ResponseEntity<List<EmployeeDto>> getEmployeesJoinedInLastYears(@PathVariable int noOfYears) {
		return new ResponseEntity<>(service.getEmployeesJoinedInLastYears(noOfYears), HttpStatus.OK);
	}

	@GetMapping("/employees/countAfter/{year}")
	public ResponseEntity<Integer> getCountOfEmployeesJoinedAfterYear(@PathVariable("year") int year) {
		return new ResponseEntity<>(service.getCountOfEmployeesJoinedAfterYear(year), HttpStatus.OK);
	}

	@DeleteMapping("/employees/{empNo}")
	public ResponseEntity<String> deleteEmployeeByEmpNo(@PathVariable int empNo) {
		service.deleteEmployeeByEmpNo(empNo);
		return new ResponseEntity<>(" Employee  is deleted successfully", HttpStatus.OK);
	}

	@GetMapping("/employees/experience/{noofyear}")
	public ResponseEntity<?> getEmployeesByExperience(@PathVariable("noofyear") int noOfYears) {
		return new ResponseEntity<>(service.getEmployeesByExperience(noOfYears), HttpStatus.OK);
	}

	@GetMapping("/employees/gender/{gender}/count")
	public ResponseEntity<Integer> getCountOfWomenEmployess(@PathVariable String gender) {
		return new ResponseEntity<>(service.getCountOfWomenEmployess(gender), HttpStatus.OK);
	}

	@GetMapping("/employees/gender/{gender}")
	public ResponseEntity<List<Employee>> getWomenEmployeeByGender(@PathVariable String gender) {
		return new ResponseEntity<>(service.getWomenEmployeesByGender(gender), HttpStatus.OK);
	}
	

}
