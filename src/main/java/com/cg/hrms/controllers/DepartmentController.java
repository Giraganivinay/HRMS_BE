package com.cg.hrms.controllers;

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
import com.cg.hrms.services.IDepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public/api/v1/department")
@Validated
public class DepartmentController {


	@Autowired
     IDepartmentService service;
	
	@PostMapping
    public ResponseEntity<Department> addNewDepartment(@Valid @RequestBody Department department) {
        Department newDepartment = service.addDepartment(department);
        return new ResponseEntity<>(newDepartment,HttpStatus.OK);
    }
	
	@GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = service.getAllDepartments();
        return ResponseEntity.ok(departments);
}
	
	@GetMapping("/{deptNo}")
    public ResponseEntity<Department> getDepartmentByDeptNo(@PathVariable String deptNo) {
        Department department = service.getDepartmentByDeptNo(deptNo);
        if (department != null) {
            return ResponseEntity.ok(department);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	 @GetMapping("/name/{name}")
	    public ResponseEntity<Department> getDeptByName(@PathVariable String name) {
	        Department department = service.getDepartmentByName(name);
	        if (department != null) {
	            return new ResponseEntity<>(department, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	 
	 @PutMapping("/{deptNo}")
	    public ResponseEntity<Department> updateDepartment(@Valid @PathVariable String deptNo, @RequestBody Department department) {
	        Department updatedDepartment = service.updateDepartment(deptNo, department);
	        if (updatedDepartment != null) {
	            return ResponseEntity.ok(updatedDepartment);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 
	 @PutMapping("/name/{name}")
	    public ResponseEntity<Department> updateDepartmentByName(
	    		@Valid
	            @PathVariable("name") String name,
	            @RequestBody Department updatedDepartment) {
	        Department department = service.updateDepartmentByName(name, updatedDepartment);
	        if (department != null) {
	            return ResponseEntity.ok(department);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	 
	 @DeleteMapping("/{deptno}")
	    public ResponseEntity<String> deleteDepartmentByDeptNo(@PathVariable String deptno) {
	        service.deleteDepartmentByDeptNo(deptno);
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Department deleted successfully");
	    }
	 
	 @DeleteMapping("/name/{name}")
	    public ResponseEntity<String> deleteDepartmentByName(@PathVariable String name) {
	        service.deleteDepartmentByName(name);
	        return ResponseEntity.ok("Department deleted successfully");
	    }
}
