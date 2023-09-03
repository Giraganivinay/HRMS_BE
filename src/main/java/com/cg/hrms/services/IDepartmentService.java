package com.cg.hrms.services;

import java.util.List;

import com.cg.hrms.entities.Department;

public interface IDepartmentService {

	Department addDepartment(Department department);

	List<Department> getAllDepartments();

	Department getDepartmentByDeptNo(String deptNo);

	Department getDepartmentByName(String name);

	Department getDepartmentById(String deptNo);

	Department updateDepartment(String deptNo, Department department);

	Department updateDepartmentByName(String name, Department updatedDepartment);

	void deleteDepartmentByDeptNo(String deptNo);

	void deleteDepartmentByName(String name);
}
