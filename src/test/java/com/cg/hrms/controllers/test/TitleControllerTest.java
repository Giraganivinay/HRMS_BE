package com.cg.hrms.controllers.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cg.hrms.controllers.TitleController;
import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.services.ITitleService;

public class TitleControllerTest {

	@InjectMocks
	private TitleController titleController;

	@Mock
	private ITitleService titleService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	// Add new title

	@Test
	void testAddTitle() {
		Title title = new Title();
		when(titleService.addTitle(any(Title.class))).thenReturn("Success");
		ResponseEntity<String> response = titleController.addTitle(title);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Success", response.getBody());
		verify(titleService, times(1)).addTitle(any(Title.class));
	}

	// Fetch All Titles
	@Test
	void testFetchAllTitles() {
		List<Title> titles = new ArrayList<>();
		when(titleService.fetchAllTitles()).thenReturn(titles);
		ResponseEntity<?> response = titleController.fetchAllTitles();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(titles, response.getBody());
		verify(titleService, times(1)).fetchAllTitles();
	}

	// Fetch titles by from_date, empno, title
	@Test
	void testFetchTitleByEmPNoAndTitle() {
		Employee empNo = new Employee();
		empNo.setEmpNo(10020);
		String title = "Enginner";
		Title titles = new Title();
		when(titleService.getTitleByEmpNoAndTitle(empNo, title)).thenReturn(null);
		ResponseEntity<?> response = titleController.fetchTitleByEmpNoAndTitle(empNo, title);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		//assertEquals(titles, response.getBody());
		verify(titleService, times(1)).getTitleByEmpNoAndTitle(empNo, title);
	}

	// Fetch all titles objects by from date
	@Test
	void testFetchTitleByFromDate() {
		Date fromDate = Date.valueOf("1995-04-17");
		List<Title> title = new ArrayList<>();
		title.add(new Title());
		when(titleService.getTitlesByFromDate(fromDate)).thenReturn(title);
		ResponseEntity<?> response = titleController.fetchTitlesByFromDate(fromDate);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(title, response.getBody());
		verify(titleService, times(1)).getTitlesByFromDate(fromDate);
	}

	// Fetch all titles objects by title
	@Test
	void testGetTitleByTitle() {
		String title = "Testing";
		List<Title> titles = new ArrayList<>();
		titles.add(new Title());
		when(titleService.getTitlesByTitle(title)).thenReturn(titles);
		ResponseEntity<?> response = titleController.fetchTitlesByTitle(title);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(titles, response.getBody());
		verify(titleService, times(1)).getTitlesByTitle(title);

	}

	// Fetch all titles objects by title and fromdate
	@Test
	void testFetchTitleByTitleAndFromDate() {
		String title = "Software Enginner";
		Date fromDate = Date.valueOf("1999-01-03");
		List<Title> titles = new ArrayList<>();
		titles.add(new Title());
		when(titleService.getTitleByTitleAndFromDate(title, fromDate)).thenReturn(titles);
		ResponseEntity<?> response = titleController.fetchTitleByTitleAndFromDate(title, fromDate);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(titles, response.getBody());
		verify(titleService, times(1)).getTitleByTitleAndFromDate(title, fromDate);
	}

	// Fetch all titles objects by empno and fromdate
	@Test
	void testFetchTitleByEmpNoAndFromDate() {
		Employee empNo = new Employee();
		Date fromDate = Date.valueOf("1995-08-27");
		List<Title> title = new ArrayList<>();
		title.add(new Title());
		when(titleService.getTitleByEmpNoAndFromDate(empNo, fromDate)).thenReturn(title);
		ResponseEntity<?> response = titleController.fetchTitleByEmpNoAndfromDate(empNo, fromDate);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(title, response.getBody());
		verify(titleService, times(1)).getTitleByEmpNoAndFromDate(empNo, fromDate);
	}
	// Fetch title by empNo and Title

	@Test
	void testFetchTitleByEmpNoAndTitle() {
		Employee empNo = new Employee();
		empNo.setEmpNo(10001);
		String title = "Senior Conceltent";
		List<Title> titles = new ArrayList<>();
		titles.add(new Title());
		when(titleService.getTitleByEmpNoAndTitle(empNo, title)).thenReturn(titles);
		ResponseEntity<?> response = titleController.fetchTitleByEmpNoAndTitle(empNo, title);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(titles, response.getBody());
		verify(titleService, times(1)).getTitleByEmpNoAndTitle(empNo, title);
	}

//	// Upadte title by empNo
//	@Test
//	void testUpdateTitleByEmpNo() {
//		Employee empNo = new Employee(); // Create a mock Employee object
//		empNo.setEmpNo(10001);
//		Title title = new Title(); // Create a mock Title object
//		List<Title> updatedTitles = new ArrayList<>();
//		updatedTitles.add(new Title());
//		when(titleService.updateTitleByEmpNo(empNo, title)).thenReturn(updatedTitles);
//		ResponseEntity<?> response = titleController.updateTitleByEmpNo(empNo, title);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(updatedTitles, response.getBody());
//		verify(titleService, times(1)).updateTitleByEmpNo(empNo, title);
//	}

	// Update titles details for the given title
	@Test
	void updateTitleByTitle() {
		String title = "Assistent Enginner";
		Title updatedTitle = new Title(); // Create an instance of the updated title object
		List<Title> updatedTitles = new ArrayList<>(); // Create a list to hold the updated titles
		updatedTitles.add(new Title());
		when(titleService.updateTitleByTitle(title, updatedTitle)).thenReturn(updatedTitles);
		ResponseEntity<?> response = titleController.updateTitleByTitle(title, updatedTitle);
		verify(titleService, times(1)).updateTitleByTitle(title, updatedTitle);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	// update title by for the given from date
	@Test
	void testUpdateTitleByFromDate() {
		Date fromDate =  Date.valueOf("2023-11-11");
		Title title = new Title();
		List<Title> updatedTitles = new ArrayList<>();
		updatedTitles.add(new Title());
		when(titleService.updateTitleByFromDate(fromDate, title)).thenReturn(updatedTitles);
		ResponseEntity<?> response = titleController.updateTitleByFromDate(fromDate, title);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedTitles, response.getBody());
		verify(titleService, times(1)).updateTitleByFromDate(fromDate, title);
	}

	// Update title by empno, fromdate, title
	@Test
	void testUpdateTitleByEmpNoAndFromDate() {
		Employee empNo = new Employee();
		empNo.setEmpNo(10010);
		Date fromDate = Date.valueOf("2022-11-10");
		String title = "Engineer";
		Title title2 = new Title();
		String updatedResult = "Title updated";
		when(titleService.updateTitleByEmpNoAndFromDate(empNo, fromDate, title, title2)).thenReturn(updatedResult);
		ResponseEntity<String> response = titleController.updateTitleByEmpNoAndFromDate(empNo, fromDate, title, title2);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedResult, response.getBody());
		verify(titleService, times(1)).updateTitleByEmpNoAndFromDate(empNo, fromDate, title, title2);
	}

	// Delete title by empno, from date and title name.
	@Test
	void testDeleteTitleByEmpNoFromDateAndTitle() {
		Employee empNo = new Employee();
		empNo.setEmpNo(10010);
		Date fromDate = Date.valueOf("2023-05-10");
		String title = "Senior staff";
		String deletionResult = "Title deleted";
		when(titleService.deleteTitleByEmpNoFromDateAndTitle(empNo, fromDate, title)).thenReturn(deletionResult);
		ResponseEntity<String> response = titleController.deletsTitleByEmpNoFromDateAndTitle(empNo, fromDate, title);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deletionResult, response.getBody());
		verify(titleService, times(1)).deleteTitleByEmpNoFromDateAndTitle(empNo, fromDate, title);
	}

	// Delete title by title
	@Test
	void deleteTitleByTitle() {
		String title = "Staff";
		List<Title> deletedTitles = new ArrayList<>(); // Create a list to hold the deleted titles
		deletedTitles.add(new Title());
		when(titleService.deleteTitleByTitle(title)).thenReturn(deletedTitles);
		ResponseEntity<?> response = titleController.deleteTitleByTitle(title);
		verify(titleService, times(1)).deleteTitleByTitle(title);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	// Delete titles by fromdate
	@Test
	void deleteTitleByFromDate() {
		Date fromDate = Date.valueOf("2022-10-11"); // Replace with the desired fromDate value
		List<Title> deletedTitles = new ArrayList<>(); // Create a list to hold the deleted titles
		deletedTitles.add(new Title());
		when(titleService.deleteTitleByFromDate(fromDate)).thenReturn(deletedTitles);
		ResponseEntity<?> response = titleController.deleteTitleByFromDate(fromDate);
		verify(titleService, times(1)).deleteTitleByFromDate(fromDate);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	// Delete titles by empno
	@Test
	void testDeleteTitleByEmpNo() {
		// Arrange
		Employee empNo = new Employee();
		empNo.setEmpNo(100011);
		List<Title> deletionResult = new ArrayList<>();
		when(titleService.deleteTitleByEmpNo(empNo)).thenReturn(deletionResult);
		ResponseEntity<?> response = titleController.deleteTitleByEmpNo(empNo);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deletionResult, response.getBody());
		verify(titleService, times(1)).deleteTitleByEmpNo(empNo);
	}

}
