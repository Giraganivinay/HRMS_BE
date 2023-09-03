package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;

import com.cg.hrms.entities.Employee;

public interface IEmployeeService {

	List<Employee> getAllEmployees();

	void addEmployee(Employee employee);

	List<Employee> searchByLastName(String lastName);

	Employee getEmpById(Integer empno);

	Employee updateLastName(Integer empno, String lastName);

	Employee updateFirstName(Integer empno, String firstName);

	Employee updatehireDate(Integer empno, Date hireDate);

	List<Employee> searchByGender(String gender);

	List<Employee> getEmpByHireDate(Date hiredate);

	List<Employee> searchByFirstName(String firstName);

	Employee updateBirthDate(Integer empno, Date birthDate);

	List<Employee> searchByBirthDate(Date birthDate);

}
