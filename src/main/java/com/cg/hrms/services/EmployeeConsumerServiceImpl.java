package com.cg.hrms.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.dto.EmployeeTitleDto;
import com.cg.hrms.dto.ManagerDto;
import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.projection.DepartmentReportDto;
import com.cg.hrms.projection.DesignationReportDto;
import com.cg.hrms.repos.IDepartmentEmployeeRepo;
import com.cg.hrms.repos.IDepartmentManagerRepo;
import com.cg.hrms.repos.IDepartmentRepo;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.repos.ITitleRepo;

@Service
public class EmployeeConsumerServiceImpl implements IEmployeeConsumerService {

	@Autowired
	private ITitleRepo titlerepo;

	@Autowired
	private IEmployeeRepo empRepo;

	@Autowired
	private IDepartmentManagerRepo deptManagerRepo;
	
	@Autowired
	private IDepartmentEmployeeRepo deptEmpRepo;
	
	@Autowired
	private IDepartmentRepo deptRepo;

	// Update last name for the given empno employee.
	@Override
	public Employee updateLastName(Integer empNo, String lastName) {
		Employee employee = empRepo.findByEmpNo(empNo);
		if (employee != null) {
			employee.setLastName(lastName);
			return empRepo.save(employee);
		}
		throw new CustomException("Employee not found with id : " + empNo);
	}

	// Get details of employees [id, name, dob, and department name] who are manager.
	@Override
	public List<ManagerDto> getManagers() {
		List<DepartmentManager> list = deptManagerRepo.findAll();
		List<ManagerDto> result = new ArrayList<>();
		for (DepartmentManager mng : list) {
			result.add(mapToDto(mng));
		}
		return result;
	}

	public ManagerDto mapToDto(DepartmentManager mng) {
		return ManagerDto.builder()
				.id(mng.getId().getEmpNo().getEmpNo())
				.name(mng.getId().getEmpNo().getFirstName())
				.dateOfBirth(mng.getId().getEmpNo().getBirthDate())
				.deptName(mng.getId().getDeptNo().getDeptName())
				.build();
	}

	@Override
	public List<Employee> getMidAgeEmployees() {
		LocalDate currentDate = LocalDate.now();

		LocalDate minBirthDate = currentDate.minusYears(59);
		LocalDate maxBirthDate = currentDate.minusYears(50);

		Date minDate = java.sql.Date.valueOf(minBirthDate);
		Date maxDate = java.sql.Date.valueOf(maxBirthDate.plusDays(1));

		return empRepo.findByBirthDateBetween(minDate, maxDate);
	}

	@Override
	public List<String> getAllDesignations() {
		return titlerepo.findAllDistinctTitles();
	}

	@Override
	public List<Employee> getManagersAfterDate(Date fromDate) {
		List<DepartmentManager> departmentManagers = deptManagerRepo.findByFromDateGreaterThan(fromDate);
		List<Employee> managers = new ArrayList<>();
		for (DepartmentManager departmentManager : departmentManagers) {
			Employee manager = departmentManager.getId().getEmpNo();
			managers.add(manager);
		}
		return managers;
	}

	//
	private EmployeeDto convertToDto(Employee employee) {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmpNo(employee.getEmpNo());
		employeeDto.setFirstName(employee.getFirstName());
		employeeDto.setLastName(employee.getLastName());
		employeeDto.setHireDate(employee.getHireDate());
		employeeDto.setBirthDate(employee.getBirthDate());
		employeeDto.setGender(employee.getGender());
		return employeeDto;
	}

	// Employee list to EmployeeDto list
//	private List<EmployeeDto> convertToDtoList(List<Employee> employees) {
//		List<EmployeeDto> employeeDtos = new ArrayList<>();
//		for (Employee employee : employees) {
//			EmployeeDto employeeDto = convertToDto(employee);
//			employeeDtos.add(employeeDto);
//		}
//		return employeeDtos;
//	}

	@Override
	public List<EmployeeDto> getEmployeesByDepartmentAndFromDate(String deptNo, Date fromDate) {
		Department department = new Department();
		department.setDeptNo(deptNo);
		List<DepartmentEmployee> departmentEmployees = deptEmpRepo.findById_DeptNoAndFromDate(department, fromDate);

		List<EmployeeDto> employeeDTOs = new ArrayList<>();
		for (DepartmentEmployee departmentEmployee : departmentEmployees) {
			Employee employee = departmentEmployee.getId().getEmpNo();
			EmployeeDto employeeDto = convertToDto(employee);
			employeeDTOs.add(employeeDto);
		}

		return employeeDTOs;
	}

	@Override
	public List<EmployeeTitleDto> getEmployeesByTitle(String title) {
		List<Title> titles = titlerepo.findById_Title(title);
		if(titles.isEmpty())
			throw new CustomException("No designation record found");
		
		List<EmployeeTitleDto> result = new ArrayList<>();
		for (Title t : titles) {
			EmployeeTitleDto dto = new EmployeeTitleDto();
			dto.setEmpno(t.getId().getEmpNo().getEmpNo());

			Employee emp = empRepo.findById(t.getId().getEmpNo().getEmpNo()).get();
			dto.setName(emp.getFirstName());
			dto.setFromDate(t.getId().getFromDate());
			dto.setToDate(t.getToDate());
			dto.setDesignation(t.getId().getTitle());
			result.add(dto);
		}
		return result;
	}

	@Override
	public List<DepartmentReportDto> getListByDeptAndYear() {
		return deptRepo.getDetails();
	}

	@Override
	public List<DesignationReportDto> getDesignationList() {
		return titlerepo.getDesignationDetails();
	}
	
	
}
