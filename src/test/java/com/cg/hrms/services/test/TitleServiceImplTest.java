package com.cg.hrms.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.entities.TitleId;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.repos.ITitleRepo;
import com.cg.hrms.services.TitleServiceImpl;

@SpringBootTest
public class TitleServiceImplTest {

	@InjectMocks
	private TitleServiceImpl titleService;

	@Mock
	private ITitleRepo titrepo;

	@Mock
	private IEmployeeRepo empRepo;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// Add new titile
	@Test
	public void testAddTitle() {
		TitleId titleId = new TitleId();
		Employee empNo = new Employee();
		empNo.setEmpNo(10001);
		titleId.setEmpNo(empNo);
		titleId.setFromDate(Date.valueOf("2022-01-01"));
		titleId.setTitle("Engineer");
		Title title = new Title();
		title.setId(titleId);
		title.setToDate(Date.valueOf("2023-01-01"));

		when(empRepo.findByEmpNo(empNo.getEmpNo())).thenReturn(empNo);

		String result = titleService.addTitle(title);

		assertEquals("New Employee details  added Successfully", result);
		verify(empRepo, times(1)).findByEmpNo(empNo.getEmpNo());
		verify(titrepo, times(1)).save(title);
	}

	// Fetch all The Title
	@Test
	void testFetchAllTitles() {
		Title title1 = new Title(new TitleId(new Employee(), "Title1", Date.valueOf("2022-01-01")),
				Date.valueOf("2023-01-01"));
		Title title2 = new Title(new TitleId(new Employee(), "Title2", Date.valueOf("2022-02-01")),
				Date.valueOf("2023-02-01"));
		when(titrepo.findAll()).thenReturn(Arrays.asList(title1, title2));
		List<Title> result = titleService.fetchAllTitles();
		assertEquals(2, result.size());
		assertEquals(title1, result.get(0));
		assertEquals(title2, result.get(1));
	}

	// Fetch all titles objects by from date
	@Test
	void testGetTitlesByFromDate() {
		Date fromDate = Date.valueOf("2018-01-10");
		List<Title> expectedTitles = new ArrayList<>();
		expectedTitles.add(new Title());
		when(titrepo.findById_FromDate(fromDate)).thenReturn(expectedTitles);
		List<Title> result = titleService.getTitlesByFromDate(fromDate);
		verify(titrepo, times(1)).findById_FromDate(fromDate);
		assertEquals(expectedTitles, result);
	}

	// Fetch all titles objects by title
	@Test
	void testGetTitleByTitle() {
		String title = "Software Enginner";
		List<Title> expectedTitles = new ArrayList<>();
		expectedTitles.add(new Title());
		when(titrepo.findById_Title(title)).thenReturn(expectedTitles);
		List<Title> result = titleService.getTitlesByTitle(title);
		verify(titrepo, times(1)).findById_Title(title);
		assertEquals(expectedTitles, result);

	}

	// get Title details using title and fromdate
	@Test
	void testGetTitleByTitleAndFromDate() {
		String title = "Enginner";
		Date fromDate = Date.valueOf("2022-01-01");
		List<Title> expectedTitles = new ArrayList<>();
		expectedTitles.add(new Title());
		when(titrepo.findById_TitleAndId_FromDate(title, fromDate)).thenReturn(expectedTitles);
		List<Title> result = titleService.getTitleByTitleAndFromDate(title, fromDate);
		verify(titrepo, times(1)).findById_TitleAndId_FromDate(title, fromDate);
		assertEquals(expectedTitles, result);
	}

	// Fetch all titles objects by empno and fromdate
	@Test
	void testGetTitleByEmpNoAndFromDate() {
		Employee empNo = new Employee();
		empNo.setEmpNo(10001);
		Date fromDate = Date.valueOf("2019-01-21");
		List<Title> expectedTitles = new ArrayList<>();
		expectedTitles.add(new Title());
		when(titrepo.findById_EmpNoAndId_FromDate(empNo, fromDate)).thenReturn(expectedTitles);
		List<Title> result = titleService.getTitleByEmpNoAndFromDate(empNo, fromDate);
		verify(titrepo, times(1)).findById_EmpNoAndId_FromDate(empNo, fromDate);
		assertEquals(expectedTitles, result);
	}

	// Fetch all titles objects by empno and title
	@Test
	void testGetTitleByEmpNoAndTitle() {
		Employee empNo = new Employee();
		empNo.setEmpNo(10002);
		String title = "BU";
		List<Title> expectedTitles = new ArrayList<>();
		expectedTitles.add(new Title());
		when(titrepo.findById_EmpNoAndId_Title(empNo, title)).thenReturn(expectedTitles);
		List<Title> result = titleService.getTitleByEmpNoAndTitle(empNo, title);
		verify(titrepo, times(1)).findById_EmpNoAndId_Title(empNo, title);
		assertEquals(expectedTitles, result);

	}

	// update Title By fromDate
	@Test
	void testUpdateTitleByFromDate() {
		Date fromDate = Date.valueOf("2022-01-01");
		TitleId titleId = new TitleId();
		titleId.setFromDate(fromDate);
		titleId.setTitle("Engineer");

		Title titleToUpdate = new Title();
		titleToUpdate.setId(titleId);
		titleToUpdate.setToDate(Date.valueOf("2023-01-01"));

		List<Title> existingTitles = new ArrayList<>();
		Title existingTitle1 = new Title();
		existingTitle1.setId(titleId);
		existingTitle1.setToDate(Date.valueOf("2023-01-01"));
		existingTitles.add(existingTitle1);

		Title existingTitle2 = new Title();
		existingTitle2.setId(titleId);
		existingTitle2.setToDate(Date.valueOf("2023-02-01"));
		existingTitles.add(existingTitle2);

		when(titrepo.findById_FromDate(fromDate)).thenReturn(existingTitles);
		when(titrepo.saveAll(existingTitles)).thenReturn(existingTitles);

		List<Title> updatedTitles = titleService.updateTitleByFromDate(fromDate, titleToUpdate);

		assertEquals(existingTitles, updatedTitles);
		verify(titrepo).saveAll(existingTitles);
	}

	// update title by title
	@Test
	void testUpdateTitleByTitle() {
		String title = "Engineer";

		TitleId existingTitleId = new TitleId(new Employee(), title, Date.valueOf("2022-01-01"));
		Title existingTitle1 = new Title();
		existingTitle1.setId(existingTitleId);
		existingTitle1.setToDate(Date.valueOf("2023-01-01"));

		List<Title> existingTitles = new ArrayList<>();
		existingTitles.add(existingTitle1);

		Title updatedTitle = new Title();
		updatedTitle.setId(existingTitleId);
		updatedTitle.setToDate(Date.valueOf("2023-02-01"));

		when(titrepo.findById_Title(title)).thenReturn(existingTitles);
		when(titrepo.saveAll(existingTitles)).thenReturn(existingTitles);

		List<Title> result = titleService.updateTitleByTitle(title, updatedTitle);

		assertEquals(existingTitles, result);
		assertEquals(Date.valueOf("2023-02-01"), existingTitle1.getToDate());

		verify(titrepo, Mockito.times(1)).findById_Title(title);
		verify(titrepo, Mockito.times(1)).saveAll(existingTitles);
	}

	@Test
	void testUpdateTitleByEmpNo() {
		int empNo = 10002;
		Employee empNoEntity = new Employee();
		empNoEntity.setEmpNo(empNo);

		Title title = new Title();
		TitleId titleId = new TitleId();
		titleId.setTitle("Engineer");
		titleId.setFromDate(Date.valueOf("2022-01-01"));
		title.setId(titleId);
		title.setToDate(Date.valueOf("2023-01-01"));

		List<Title> titles = new ArrayList<>();
		titles.add(title);

		when(empRepo.findById(empNo)).thenReturn(Optional.empty());

		assertThrows(CustomException.class, () -> titleService.updateTitleByEmpNo(empNo, title));

		verify(empRepo, times(1)).findById(empNo);
		verifyNoInteractions(titrepo);
	}

	// deleteTitleByEmpNoFromDateAndTitle
	@Test
	void testDeleteTitleByEmpNoFromDateAndTitle() {
		// Create sample data
		Employee empNo = new Employee();
		empNo.setEmpNo(10006);
		Date fromDate = Date.valueOf("1989-09-12");
		String title = "Enginner";
		Title titleEntity = new Title();
		when(titrepo.findById_EmpNoAndId_FromDateAndId_Title(empNo, fromDate, title)).thenReturn(titleEntity);
		String result = titleService.deleteTitleByEmpNoFromDateAndTitle(empNo, fromDate, title);
		verify(titrepo, times(1)).deleteById_EmpNoAndId_FromDateAndId_Title(empNo, fromDate, title);
		assertEquals("Title deleted Successfully", result);
	}

	// Delete title by empNo
	@Test
	void testDeleteTitleByEmpNo() {
		Employee empNo = new Employee();
		empNo.setEmpNo(10009);
		List<Title> titles = new ArrayList<>();
		titles.add(new Title());
		when(titrepo.findById_empNo(empNo)).thenReturn(titles);
		List<Title> deletedTitles = titleService.deleteTitleByEmpNo(empNo);
		verify(titrepo, times(1)).findById_empNo(empNo);
		verify(titrepo, times(1)).deleteById_EmpNo(empNo);
		assertTrue(deletedTitles.isEmpty());
	}

	// Delete titles by fromdate
	@Test
	void testDeleteTitleByFromDate() {
		Date fromDate = Date.valueOf("1995-12-01");
		List<Title> titles = new ArrayList<>();
		titles.add(new Title());
		when(titrepo.findById_FromDate(fromDate)).thenReturn(titles);
		when(titrepo.deleteById_FromDate(fromDate)).thenReturn(titles);
		List<Title> result = titleService.deleteTitleByFromDate(fromDate);
		verify(titrepo, times(1)).findById_FromDate(fromDate);
		verify(titrepo, times(1)).deleteById_FromDate(fromDate);
		assertEquals(titles, result);
	}

}
