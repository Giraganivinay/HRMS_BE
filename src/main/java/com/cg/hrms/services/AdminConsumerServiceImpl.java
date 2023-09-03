package com.cg.hrms.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.dto.EmployeeDto;
import com.cg.hrms.entities.Department;
import com.cg.hrms.entities.DepartmentEmployee;
import com.cg.hrms.entities.DepartmentManager;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Salary;
import com.cg.hrms.entities.SalaryId;
import com.cg.hrms.entities.Title;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IDepartmentEmployeeRepo;
import com.cg.hrms.repos.IDepartmentManagerRepo;
import com.cg.hrms.repos.IDepartmentRepo;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.repos.ISalaryRepo;
import com.cg.hrms.repos.ITitleRepo;

@Service
public class AdminConsumerServiceImpl implements IAdminConsumerService {

	@Autowired
	private IDepartmentEmployeeRepo deptEmpRepo;

	@Autowired
	private IEmployeeRepo empRepo;

	@Autowired
	private IDepartmentRepo deptRepo;

	@Autowired
	private IDepartmentManagerRepo deptManagerRepo;

	@Autowired
	private ITitleRepo titleRepo;

	@Autowired
	private ISalaryRepo salRepo;

	// Assign department to employee from fromdate to end date.
	@Override
	public String assignDeptToEmp(DepartmentEmployee deptEmp) {
		Employee emp = empRepo.findByEmpNo(deptEmp.getId().getEmpNo().getEmpNo());
		Department dept = deptRepo.findByDeptNo(deptEmp.getId().getDeptNo().getDeptNo());
		if (emp != null && dept != null) {
			if(deptEmp.getFromDate().compareTo(deptEmp.getToDate()) > 0)
				throw new CustomException("From Date should appear before To Date..");

			deptEmpRepo.save(deptEmp);
			return "Employee is assigned to the department successfully";
		} else {
			throw new CustomException("Employee Or Department not exist..");
		}
	}

	// Assign manager to specific department from fromdate to end date.
	@Override
	public String assignManagerToDept(DepartmentManager deptManager) {
		Employee emp = empRepo.findByEmpNo(deptManager.getId().getEmpNo().getEmpNo());
		Department dept = deptRepo.findByDeptNo(deptManager.getId().getDeptNo().getDeptNo());
		
		if (emp != null && dept != null) {
			if(deptManager.getFromDate().compareTo(deptManager.getToDate()) > 0) 
				throw new CustomException("From Date should appear before To Date");
			
			deptManagerRepo.save(deptManager);
			return "Employee is assigned as manager for department successfully.";
		} else {
			throw new CustomException("Employee Or Department not exist..");
		}
	}

	// Assign employee with title [designation] from start to end date. [Assuming
	// that by default employee will be eligible for promotion after 5 years]
	@Override
	public String assignTitleToEmp(Title title) {
		Employee emp = empRepo.findByEmpNo(title.getId().getEmpNo().getEmpNo());
		if (emp != null) {
			if (hasEmployeeJoinedBeforeYear(emp.getEmpNo(), 5)) {
				if(title.getId().getFromDate().compareTo(title.getToDate()) > 0) 
					throw new CustomException("From Date should appear before To Date");
				
				titleRepo.save(title);
				return "Employee is assigned with title  from fromdate to enddate";
			} else {
				throw new CustomException("Employee will be eligible for promotion after 5 years");
			}

		} else {
			throw new CustomException("Employee not exist..");
		}
	}

	@Override
	public boolean hasEmployeeJoinedBeforeYear(int empNo, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -year);
		java.util.Date time = calendar.getTime();
		return empRepo.hasCompletedFiveYears(empNo, new Date(time.getTime()));
	}

	// Add new department
	@Override
	public String addNewDepartment(Department dept) {
		String deptName = dept.getDeptName();
		Department exist = deptRepo.findByDeptName(deptName);
		if(exist != null) 
			throw new CustomException("Department name " + deptName + " already exist...");
		
		dept.setDeptName(dept.getDeptName().toUpperCase());
		
		Department existingDept = deptRepo.findFirstByOrderByDeptNoDesc();
		
	    if (existingDept != null) {
	    	// increasing department no by 1
	        String lastDeptNo = existingDept.getDeptNo();
	        int lastDeptNumber = Integer.parseInt(lastDeptNo.substring(1));
	        int newDeptNumber = lastDeptNumber + 1;
	        dept.setDeptNo("d" + String.format("%03d", newDeptNumber));
	    } else {
	        dept.setDeptNo("d001");
	    }

	    deptRepo.save(dept);
	    return "Department is added successfully";
	}

	// Add new employee
	@Override
	public Employee addNewEmployee(Employee emp) {
		if(emp.getBirthDate().compareTo(emp.getHireDate()) > 0) 
			throw new CustomException("Birdate should appear before Hiredate");
		return empRepo.save(emp);
	}

	// Update the salary for employee when employee is appraised each year depending 
	// on rating.
	@Override
	public String updateSalary(int employeeId, int rating, boolean promoted) { // need to check
		Employee employee = empRepo.findByEmpNo(employeeId);
		if (employee != null) {
			if(rating > 3 || rating < 1)
				throw new CustomException("Please provide rating between:  1 | 2 | 3");
			List<Salary> salaries = salRepo.findById_EmpNo(employee);
			if (!salaries.isEmpty()) { 								// if employee has entries in salary table
				Salary lastUpdate = salaries.get(salaries.size() - 1);
				int currentSalary = lastUpdate.getSalary();

				if (rating == 1) {
					currentSalary += 10000;
				} else if (rating == 2) {
					currentSalary += 7000;
				} else if (rating == 3) {
					currentSalary += 5000;
				}

				if (promoted) {
					currentSalary += 100000;
				}

				Employee emNo = lastUpdate.getId().getEmpNo();
				Date toDate = lastUpdate.getToDate();

				// creating new salary obj to be added
				Salary s = new Salary();
				s.setId(new SalaryId(emNo, toDate));
				s.setSalary(currentSalary);

				// converting to sql date
				LocalDate today = LocalDate.now();
				Date sqlDate = Date.valueOf(today);

				// adding one year
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sqlDate);
				calendar.add(Calendar.YEAR, 1);
				Date updatedDate = new Date(calendar.getTimeInMillis());

				s.setToDate(updatedDate);
				salRepo.save(s);
				return "Employee year wise salary details updated successfully";
			} else { // if new employee is added in salary table
				Salary s = new Salary();
				s.setId(new SalaryId(employee, Date.valueOf("2022-06-26")));
				s.setToDate(Date.valueOf("2023-06-26"));
				s.setSalary(600000);
				salRepo.save(s);
				return "New salary added";
			}
		} else {
			throw new CustomException("Employee with id : " + employeeId + " not exist");
		}
	}

	//

	@Override
	public int getCountOfEmployeesJoinedInLastYears(int noOfYears) {
		// Calculate the date X years ago from the current date
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -noOfYears);
		java.util.Date utilStartDate = calendar.getTime();

		// Convert java.util.Date to java.sql.Date
		Date startDate = new Date(utilStartDate.getTime());

		return empRepo.countByHireDateAfter(startDate);
	}

	// Employee to EmployeeDto
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
	private List<EmployeeDto> convertToDtoList(List<Employee> employees) {
		List<EmployeeDto> employeeDtos = new ArrayList<>();
		for (Employee employee : employees) {
			EmployeeDto employeeDto = convertToDto(employee);
			employeeDtos.add(employeeDto);
		}
		return employeeDtos;
	}

	@Override
	public List<EmployeeDto> getEmployeesJoinedAfterYear(int year) {
		Date startDate = Date.valueOf(year + "-01-01");
		List<Employee> employees = empRepo.findTop500ByHireDateAfter(startDate);
		return convertToDtoList(employees);
	}

	@Override
	public List<EmployeeDto> getEmployeesJoinedInLastYears(int noOfYears) {
		// Calculate the date X years ago from the current date
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -noOfYears);
		java.util.Date utilStartDate = calendar.getTime();

		Date startDate = new Date(utilStartDate.getTime());
		List<Employee> employees = empRepo.findTop500ByHireDateAfter(startDate);

		return convertToDtoList(employees);
	}

	@Override
	public int getCountOfEmployeesJoinedAfterYear(int year) {
		Date startDate = Date.valueOf(year + "-01-01");
		return empRepo.countByHireDateAfter(startDate);
	}

	@Override
	public void deleteEmployeeByEmpNo(int empNo) {
		Optional<Employee> emp = empRepo.findById(empNo);
		if(emp.isPresent()) {
			empRepo.deleteById(empNo);
		}else {
			throw new CustomException("Employee not presend with id : " + empNo);
		}
		
	}

	@Override
	public List<Employee> getEmployeesByExperience(int noOfYears) {
		// Calculate the date X years ago from the current date
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -noOfYears);
		java.util.Date utilStartDate = calendar.getTime();

		Date startDate = new Date(utilStartDate.getTime());
		return empRepo.findTop800ByHireDateBefore(startDate);
	}

	@Override
	public Integer getCountOfWomenEmployess(String gender) {
		return empRepo.countByGender(gender);
	}

	@Override
	public List<Employee> getWomenEmployeesByGender(String gender) {
		return empRepo.findTop800ByGender(gender);
	}

}
