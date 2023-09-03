package com.cg.hrms.repos;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.entities.TitleId;
import com.cg.hrms.projection.DesignationReportDto;

public interface ITitleRepo extends JpaRepository<Title, TitleId> {

	// Search titles by from date, empno, title
	Title findById_EmpNoAndId_FromDateAndId_Title(Employee empNo, Date fromDate, String title);

	// Fetch all titles objects by from date
	List<Title> findById_FromDate(Date fromDate);

	// Fetch all titles objects by title
	List<Title> findById_Title(String title);

	// Fetch all titles objects by title and fromdate
	List<Title> findById_TitleAndId_FromDate(String title, Date fromDate);

	// Fetch all titles objects by empno and fromdate
	List<Title> findById_EmpNoAndId_FromDate(Employee empNo, Date fromDate);

	// Fetch all titles objects by empno and title
	List<Title> findById_EmpNoAndId_Title(Employee empNo, String title);

	// Update titles details for the given employee no
	List<Title> findById_EmpNo(Employee empNo);
	// Update title by empno, fromdate, title

	// Delete title by empno, from date and title name.
	String deleteById_EmpNoAndId_FromDateAndId_Title(Employee empNo, Date fromDate, String title);

	// Delete titles by empno
	List<Title> findById_empNo(Employee empNo);

	List<Title> deleteById_EmpNo(Employee empNo);

	// Delete titles by fromdate
	List<Title> deleteById_FromDate(Date fromDate);

	// Delete titles by title
	List<Title> deleteById_Title(String title);

	// -------------------------------------------empConsumer

	@Query("SELECT DISTINCT t.id.title FROM Title t")
	List<String> findAllDistinctTitles();

	@Query(nativeQuery = true, value = "select  t.title as designation, avg(s.salary)as average_salary, min(s.salary) as min_salary, max(s.salary)as max_salary from titles t join salaries s on t.emp_no = s.emp_no group by t.title")
	List<DesignationReportDto> getDesignationDetails();
	

}
