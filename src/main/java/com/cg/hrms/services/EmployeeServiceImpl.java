package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IEmployeeRepo;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeRepo repo;

	@Override
	public void addEmployee(Employee employee) {
		repo.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return repo.findAll();
	}

	@Override
	public List<Employee> searchByLastName(String lastName) {
		List<Employee> list = repo.findByLastName(lastName);
		if (list.size() > 0) {
			return list;
		} else {
			throw new CustomException("Employee not found with lastname..");
		}
	}

	@Override
	public List<Employee> searchByFirstName(String firstName) {
		List<Employee> list = repo.findByFirstName(firstName);
		if (list.size() > 0) {
			return list;
		} else {
			throw new CustomException("Employee not found with firstname..");
		}
	}

	@Override
	public Employee updateLastName(Integer empno, String lastName) {
		Employee employee = repo.findById(empno).orElse(null);
		if (employee != null) {
			employee.setLastName(lastName);
			return repo.save(employee);
		}

		throw new CustomException("employee with id is not present");
	}

	@Override
	public Employee updateFirstName(Integer empno, String firstName) {
		Employee employee = repo.findById(empno).orElse(null);
		if (employee != null) {
			employee.setFirstName(firstName);
			return repo.save(employee);
		}
		// empoyee with id qis not present throw exception
		throw new CustomException("employee with id is not present");

	}

	@Override
	public Employee updatehireDate(Integer empno, Date hireDate) {
		Employee employee = repo.findById(empno).orElse(null);
		if (employee != null) {
			employee.setHireDate(hireDate);
			return repo.save(employee);
		}
		throw new CustomException("employee with id is not present");
	}

	@Override
	public Employee updateBirthDate(Integer empno, Date birthDate) {
		Employee employee = repo.findById(empno).orElse(null);
		if (employee != null) {
			employee.setBirthDate(birthDate);
			return repo.save(employee);
		}
		throw new CustomException("employee with id is not present");
	}

	@Override
	public List<Employee> searchByGender(String gender) {
		List<Employee> list = repo.findTop800ByGender(gender);
		if (list.size() > 0) {
			return list;
		} else {
			throw new CustomException("Gender does not exist");
		}
	}

	@Override
	public Employee getEmpById(Integer empno) {
		return repo.findById(empno).get();
	}

	@Override
	public List<Employee> getEmpByHireDate(Date hireDate) {
		return repo.findByHireDate(hireDate);
	}

	@Override
	public List<Employee> searchByBirthDate(Date birthDate) {
		return repo.findByBirthDate(birthDate);
	}

	
}
