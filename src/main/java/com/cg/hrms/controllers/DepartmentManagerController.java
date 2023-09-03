package com.cg.hrms.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.services.IDepartmentManagerService;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/public/api/v1/deptmanager")
@Validated
public class DepartmentManagerController {

	@Autowired
	IDepartmentManagerService service;

	// 1.Dept_manager Add new department manager object in DB
	@PostMapping("/")
	public ResponseEntity<String> addDeptManager(@Valid @RequestBody DepartmentManager deptManager) {
		try {
			service.addDeptManager(deptManager);
			return ResponseEntity.status(HttpStatus.CREATED).body("New employee assigned as manager successfully");
		} catch (ValidationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add dept manager");
		}
	}

	// 2.search department manager by empNo and deptNo
	@GetMapping("/empno/{empno}/deptno/{deptno}")
	public ResponseEntity<?> fetchDeptManagerById(@PathVariable int empno, @PathVariable String deptno) {
		return new ResponseEntity<>(service.getDeptManagerById(empno, deptno), HttpStatus.OK);
	}

	// 3.Fetch all department manager objects
	@GetMapping("/")
	public ResponseEntity<List<DepartmentManager>> getAllDeptManagers() {
		List<DepartmentManager> deptManagers = service.getAllDeptManagers();
		return ResponseEntity.ok(deptManagers);
	}

	// 4.Fetch all department manager objects by , deptno and from date
	@GetMapping("/deptno/{deptno}/fromdate/{fromdate}")
	public ResponseEntity<List<DepartmentManager>> getDeptManagersByDeptNoAndFromDate(@PathVariable String deptno,
			@PathVariable Date fromdate) {
		List<DepartmentManager> deptManagers = service.getDeptManagersByDeptNoAndFromDate(deptno, fromdate);
		return ResponseEntity.ok(deptManagers);
	}

	// 5.Fetch all deptmanager objects by , id and from date
	@GetMapping("/empno/{empno}/fromdate/{fromdate}")
	public ResponseEntity<Optional<DepartmentManager>> getDeptManagersByEmpNoAndFromDate(@PathVariable int empno,
			@PathVariable Date fromdate) {
		Optional<DepartmentManager> deptManagers = service.getDeptManagersByEmpNoAndFromDate(empno, fromdate);
		return ResponseEntity.ok(deptManagers);
	}

	// 6.Fetch all deptmanager objects by , id ,deptno,and from date
	@GetMapping("/empno/{empno}/deptno/{deptno}/fromdate/{fromdate}")
	public ResponseEntity<?> getDeptManagersByEmpNoDeptNoAndFromDate(@PathVariable int empno,
			@PathVariable String deptno, @PathVariable Date fromdate) {
		Optional<DepartmentManager> deptManagers = service.getDeptManagersByEmpNoDeptNoAndFromDate(empno, deptno,
				fromdate);
		return ResponseEntity.ok(deptManagers);
	}

	// 7.Update deptmanager details for the given empno and deptno
	@PutMapping("/{empno}/{deptno}")
	public ResponseEntity<String> updateDepartmentManagerByEmpNoAndDeptNo(@Valid @PathVariable Employee empno,
			@PathVariable Department deptno, @RequestBody DepartmentManager departmentManager) {
		service.updateDepartmentManagerByEmpNoAndDeptNo(empno, deptno, departmentManager);
		return ResponseEntity
				.ok("Department manager for empNo: " + empno + " and deptNo: " + deptno + " updated successfully.");
	}

	// 8.Update deptmanager details for the given empno and fromdate
	@PutMapping("/empno/{empno}/fromdate/{fromdate}")
	public ResponseEntity<String> updateDepartmentManagerByEmpNoAndFromDate(@Valid @PathVariable Employee empno,
			@PathVariable Date fromdate, @RequestBody DepartmentManager departmentManager) {
		service.updateDepartmentManagerByEmpNoAndFromDate(empno, fromdate, departmentManager);
		return ResponseEntity
				.ok("Department manager for empNo: " + empno + " and fromDate: " + fromdate + " updated successfully.");
	}

	// 9.Update deptmanager details for the given deptno and fromdate
	@PutMapping("/deptno/{deptno}/fromdate/{fromdate}")
	public ResponseEntity<String> updateDeptManagerDetailsByDeptNoAndFromDate(@Valid  @PathVariable Department deptno,
			@PathVariable Date fromdate, @RequestBody DepartmentManager departmentManager) {
		return new ResponseEntity<>("Department manager details updated successfully.", HttpStatus.OK);
	}

	// 10.Update deptmanager by empno, deptno, fromdate
	@PutMapping("/empno/{empno}/deptno/{deptno}/fromdate/{fromdate}")
	public String updateDepartmentManager(@Valid  @PathVariable Employee empno, @PathVariable Department deptno,
			@PathVariable Date fromdate, @RequestBody DepartmentManager departmentManager) {

		return service.updateDepartmentManagerByEmpNoDeptNoAndFromDate(empno, deptno, fromdate, departmentManager);

	}

	// 11.delete by empno and deptno
	@DeleteMapping("/{empno}/deptno/{deptno}")
	public ResponseEntity<String> deleteDepartmentManager(@Valid @PathVariable("empno") Employee empNo,
			@PathVariable("deptno") Department deptNo) {
		service.deleteDepartmentManagerByEmpNoAndDeptNo(empNo, deptNo);
		return ResponseEntity.ok("Department manager deleted successfully");
	}

	// 12. delete by empno and fromdate
	@DeleteMapping("/empno/{empno}/fromdate/{fromdate}")
	public ResponseEntity<DepartmentManager> deleteDeptEmpByEmpNoAndFromDate(@PathVariable("empno") Employee empNo,
			@PathVariable("fromdate") Date fromDate) {
		DepartmentManager deletedDepartmentManager = service.deleteDepartmentManagerByEmpNoAndFromDate(empNo, fromDate);

		return ResponseEntity.ok(deletedDepartmentManager);
	}

	// 13.Delete deptmanager by deptno and fromdate
	@DeleteMapping("/deptno/{deptno}/fromdate/{fromdate}")
	public ResponseEntity<List<DepartmentManager>> deleteDepartmentManagers(@PathVariable Department deptno,
			@PathVariable Date fromdate) {
		List<DepartmentManager> deletedDepartmentManagers = service.deleteDepartmentManagersByDeptNoAndFromDate(deptno,
				fromdate);
		return ResponseEntity.ok(deletedDepartmentManagers);
	}

	// 14.Delete deptmanager by empno, deptno and fromdate
	@DeleteMapping("/empno/{empno}/deptno/{deptno}/fromdate/{fromdate}")
	public ResponseEntity<DepartmentManager> deleteDepartmentManagerByEmpId_NoAndFromDate(
			@PathVariable("empno") Employee empNo, @PathVariable("deptno") Department deptNo,
			@PathVariable("fromdate") Date fromDate) {
		DepartmentManager deletedDepartmentManager = service.deleteDepartmentManagerByEmpNoDeptNoAndFromDate(empNo,
				deptNo, fromDate);

		return ResponseEntity.ok(deletedDepartmentManager);
	}

}
