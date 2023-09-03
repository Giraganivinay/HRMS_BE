package com.cg.hrms.repos;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.hrms.entities.Employee;

@Repository
public interface IEmployeeRepo extends JpaRepository<Employee, Integer> {

	List<Employee> findByLastName(String lastName);

	List<Employee> findTop800ByGender(String gender);

	List<Employee> findByHireDate(Date hiredate);

	List<Employee> findByFirstName(String firstName);

	List<Employee> findByBirthDate(Date birthDate);

	// -----------------admin consumer--------------
	Employee findByEmpNo(int no);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.empNo = :empNo AND e.hireDate <= :fiveYearsAgo")
	boolean hasCompletedFiveYears(int empNo, Date fiveYearsAgo);

	List<Employee> findByBirthDateBetween(Date minBirthDate, Date maxBirthDate);

	@Query("SELECT COUNT(e) FROM Employee e WHERE e.hireDate > :startDate")
	int countByHireDateAfter(@Param("startDate") Date startDate);

	List<Employee> findTop500ByHireDateAfter(Date startDate);

	List<Employee> findTop800ByHireDateBefore(Date startDate);

	Integer countByGender(String gender);

}
