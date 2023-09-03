package com.cg.hrms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.entities.Department;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IDepartmentRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	IDepartmentRepo repo;

	@Override
	public Department getDepartmentById(String id) {
		Optional<Department> dept = repo.findById(id);
		if(dept.isPresent()) {
			return dept.get();
		}else {
			throw new CustomException("Department not exist");
		}
	}

	@Override
    public Department addDepartment(Department department) {
        String deptNo = department.getDeptNo();
        if (deptNo.length() > 4) {
            throw new CustomException("Invalid deptNo: Maximum length should be 4 characters");
        }
        return repo.save(department);
    }

	@Override
	public List<Department> getAllDepartments() {
		return repo.findAll();
	}

	@Override
	public Department getDepartmentByDeptNo(String deptNo) {
		return repo.findByDeptNo(deptNo);
	}

	@Override
	public Department getDepartmentByName(String name) {
		return repo.findByDeptName(name);
	}

	@Override
	public Department updateDepartment(String deptNo, Department department) {
		Department existingDepartment = repo.findById(deptNo).orElse(null);
		if (existingDepartment != null) {
			existingDepartment.setDeptName(department.getDeptName());
			return repo.save(existingDepartment);
		}
		throw new CustomException("Department number is invalid");
	}

	@Override
	public Department updateDepartmentByName(String name, Department updatedDepartment) {
		Department department = repo.findByDeptName(name);
		if (department != null) {
			department.setDeptNo(updatedDepartment.getDeptNo());
			department.setDeptName(updatedDepartment.getDeptName());
			return repo.save(department);
		} else {
			throw new CustomException("Department name is not found");
		}
	}

	@Override
	public void deleteDepartmentByDeptNo(String deptNo) {
		repo.deleteByDeptNo(deptNo);
	}

	@Override
	public void deleteDepartmentByName(String name) {
		repo.deleteByDeptName(name);
	}

}
