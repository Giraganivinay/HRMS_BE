package com.cg.hrms.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.services.IDepartmentEmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public/api/v1/deptemp")
@Validated
public class DepartmentEmployeeController {

	@Autowired
	private IDepartmentEmployeeService service;

	// 1.Add new DepartmentEmployee object in Data Base
	@PostMapping
	public ResponseEntity<String> addDeptEmp(@Valid @RequestBody DepartmentEmployee deptEmp) {
		DepartmentEmployee savedDeptEmp = service.addDeptEmp(deptEmp);
		if (savedDeptEmp != null) {
			return new ResponseEntity<>("New employee assigned to department successfully", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Failed to add the deptemp object", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 2.Fetch all DepartmentEmployee objects
	@GetMapping
	public ResponseEntity<List<DepartmentEmployee>> getAllDeptEmps() {
		List<DepartmentEmployee> deptEmps = service.getAllDeptEmps();
		return ResponseEntity.status(HttpStatus.OK).body(deptEmps);
	}

	// 3.Fetch DepartmentEmployee by empNo and deptNo
	@GetMapping("/empno/{empNo}/deptno/{deptNo}")
	public ResponseEntity<DepartmentEmployee> getDeptEmpById(@PathVariable Employee empNo, @PathVariable Department deptNo) {
		return new ResponseEntity<>(service.getDeptEmpByEmpNoDeptNo(empNo, deptNo), HttpStatus.OK);
	}

	// 4.Fetch DepartmentEmployee objects by deptno and fromDate
	@GetMapping("/deptno/{deptno}/fromdate/{fromdate}")
	public List<DepartmentEmployee> getByDeptNoAndFromDate(@PathVariable("deptno") String deptNo,
			@PathVariable("fromdate") Date fromDate) {
		return service.getDeptEmpByDeptNoAndFromDate(deptNo, fromDate);
	}

	// 5.Fetch DepartmentEmployee objects by id and fromDate
	@GetMapping("/empno/{empNo}/fromdate/{fromDate}")
	public ResponseEntity<DepartmentEmployee> getByEmpNoAndFromDate(@PathVariable("empNo") int empNo,
			@PathVariable("fromDate") Date fromDate) {
		return new ResponseEntity<>(service.getDeptEmpByEmpNoAndFromDate(empNo, fromDate), HttpStatus.OK);
	}

	// 6.Fetch DepartmentEmployee objects by id ,deptno and fromDate
	@GetMapping("/empno/{empNo}/deptno/{deptNo}/fromdate/{fromDate}")
	public List<DepartmentEmployee> getByEmpNoDeptNoAndFromDate(@PathVariable("empNo") Employee empNo,
			@PathVariable("deptNo") Department deptNo, @PathVariable("fromDate") Date fromDate) {
		return service.getDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo, fromDate);
	}

//	 7.Update DepartmentEmployee details for the given empno and deptno
	@PutMapping("/empno/{empNo}/deptno/{deptNo}")
	public ResponseEntity<String> updateDeptEmpByEmpNoAndDeptNo(@Valid @PathVariable("empNo") Employee empNo,
			@PathVariable("deptNo") Department deptNo, @RequestBody DepartmentEmployee deptEmp) {

		String response = service.updateDeptEmpByEmpNoAndDeptNo(empNo, deptNo, deptEmp);
		if (response != null) {
			return ResponseEntity.ok("Department Employee updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Department Employee");
		}
	}

	// 8.Update DepartmentEmployee details for the given empNo and fromDate
	@PutMapping("/empno/{empNo}/fromdate/{fromDate}")
	public ResponseEntity<String> updateDeptEmpByEmpNoAndFromDate( @Valid @PathVariable Employee empNo,
			@PathVariable Date fromDate, @RequestBody DepartmentEmployee deptEmp) {

		String response = service.updateDeptEmpByEmpNoAndFromDate(empNo, fromDate, deptEmp);
		if (response != null) {
			return ResponseEntity.ok("Department Employee updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Department Employee");
		}
	}

	// 9.Update DepartmentEmployee details for the given deptno and fromDate
	@PutMapping("/deptno/{deptNo}/fromdate/{fromDate}")
	public ResponseEntity<String> updateDepartmentEmployees(@Valid  @PathVariable("deptNo") Department deptNo,
			@PathVariable("fromDate") Date fromDate, @RequestBody List<DepartmentEmployee> deptEmp) {

		List<DepartmentEmployee> updatedDepartmentEmployee = service.updateDeptEmpByDeptNoAndFromDate(deptNo, fromDate);

		if (updatedDepartmentEmployee != null) {
			return ResponseEntity.ok("Department Employees updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to update Department Employees");
		}
	}

	// 10.Update DepartmentEmployee by empNo, deptNo and fromDate
	@PutMapping("/empno/{empno}/deptno/{deptno}/fromdate/{fromdate}")
	public ResponseEntity<String> updateDepartmentEmployee(@Valid  @PathVariable Employee empno,
			@PathVariable Department deptno, @PathVariable Date fromdate, @RequestBody DepartmentEmployee deptEmp) {
		String response = service.updateDeptEmpByEmpNoDeptNoAndFromDate(empno, deptno, fromdate, deptEmp);
		if (response != null) {
			return ResponseEntity.ok("Department Employee updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Department Employee");
		}
	}

	// 11.Delete DepartmentEmployee by empNo and deptNo
	@DeleteMapping("/empno/{empNo}/deptno/{deptNo}")
	public ResponseEntity<String> deleteDeptEmpByEmpNoAndDeptNo(@Valid @PathVariable("empNo") int empNo,
			@PathVariable("deptNo") String deptNo) {

		service.deleteDeptEmpByEmpNoAndDeptNo(empNo, deptNo);
		return ResponseEntity.ok("Department Employee deleted successfully");
	}

	// 12.Delete DepartmentEmployee by empNo and fromDate
	@DeleteMapping("/empno/{empNo}/fromdate/{fromDate}")
	public ResponseEntity<DepartmentEmployee> deleteDeptEmpByEmpNoAndFromDate(@PathVariable("empNo") int empNo,
			@PathVariable("fromDate") Date fromDate) {
		DepartmentEmployee deletedDeptEmp = service.deleteDeptEmpByEmpNoAndFromDate(empNo, fromDate);

		return ResponseEntity.ok(deletedDeptEmp);
	}

	// 13.Delete DepartmentEmployee by deptNo and fromDate
	@DeleteMapping("/deptno/{deptNo}/fromdate/{fromDate}")
	public ResponseEntity<List<DepartmentEmployee>> getDeptEmpByDeptNoAndFromDate(
			@PathVariable("deptNo") String deptNo, @PathVariable("fromDate") Date fromDate) {
		List<DepartmentEmployee> deptEmpList = service.deleteDeptEmpByDeptNoAndFromDate(deptNo, fromDate);
		return ResponseEntity.ok(deptEmpList);
	}

	// 14.Delete DepartmentEmployee by empNo, deptNo and fromDate
	@DeleteMapping("/empno/{empNo}/deptno/{deptNo}/fromdate/{fromDate}")
	public ResponseEntity<List<DepartmentEmployee>> deleteDeptEmpByEmpNoDeptNoAndFromDate(
			@PathVariable("empNo") int empNo, @PathVariable("deptNo") String deptNo,
			@PathVariable("fromDate") Date fromDate) {
		List<DepartmentEmployee> deletedDeptEmp = service.deleteDeptEmpByEmpNoDeptNoAndFromDate(empNo, deptNo,
				fromDate);
		return ResponseEntity.ok(deletedDeptEmp);
	}

}
