package com.cg.hrms.services;

import java.sql.Date;
import java.util.List;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;

public interface ITitleService {

	// Add new titles object in DB
	String addTitle(Title title);

	// Fetch all titles objects
	List<Title> fetchAllTitles();

	// Search titles by from date, empno, title
	Title getTitleByEmpNoFromDateTitle(int empNo, Date fromDate, String title);

	// Fetch all titles objects by from date
	List<Title> getTitlesByFromDate(Date fromDate);

	// Fetch all titles objects by title
	List<Title> getTitlesByTitle(String title);

	// Fetch all titles objects by title and fromdate
	List<Title> getTitleByTitleAndFromDate(String title, Date fromDate);

	// Fetch all titles objects by empno and fromdate
	List<Title> getTitleByEmpNoAndFromDate(Employee empNo, Date fromDate);

	// Fetch all titles objects by empno and title
	List<Title> getTitleByEmpNoAndTitle(Employee empNo, String title);

	// Update titles details for the given fromdate
	List<Title> updateTitleByFromDate(Date fromDate, Title title);

	// Update titles details for the given title
	List<Title> updateTitleByTitle(String title, Title title1);

	// Update titles details for the given employee no
	List<Title> updateTitleByEmpNo(int empNo, Title title);

	// Update title by empno, fromdate, title
	String updateTitleByEmpNoAndFromDate(Employee empNo, Date fromDate, String title, Title title2);

	// Delete title by empno, from date and title name.
	String deleteTitleByEmpNoFromDateAndTitle(Employee empNo, Date fromDate, String title);

	// Delete titles by empno
	List<Title> deleteTitleByEmpNo(Employee empNo);

	// Delete titles by fromdate
	List<Title> deleteTitleByFromDate(Date fromDate);

	// Delete titles by title
	List<Title> deleteTitleByTitle(String title);

}
