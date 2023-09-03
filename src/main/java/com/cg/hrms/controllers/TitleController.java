package com.cg.hrms.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.services.ITitleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public/api/v1/titles")
@Validated
public class TitleController {

	@Autowired
	ITitleService service;

	// Add new titles object in DB
	@PostMapping
	public ResponseEntity<String> addTitle(@Valid @RequestBody Title title) {

		return new ResponseEntity<>(service.addTitle(title), HttpStatus.CREATED);
	}

	// Fetch all titles objects
	@GetMapping
	public ResponseEntity<List<Title>> fetchAllTitles() {
		return new ResponseEntity<>(service.fetchAllTitles(), HttpStatus.OK);
	}

	// Search titles by from_date, empno, title
	@GetMapping("/empNo/{empNo}/fromDate/{fromDate}/title/{title}")
	public ResponseEntity<Title> fetchTitleByEmpNoFromDateTitle(@PathVariable int empNo, @PathVariable Date fromDate,
			@PathVariable String title) {
		return new ResponseEntity<>(service.getTitleByEmpNoFromDateTitle(empNo, fromDate, title), HttpStatus.OK);
	}

	// Fetch all titles objects by from date
	@GetMapping("/fromDate/{fromDate}")
	public ResponseEntity<List<Title>> fetchTitlesByFromDate(@PathVariable Date fromDate) {
		return new ResponseEntity<>(service.getTitlesByFromDate(fromDate), HttpStatus.OK);
	}

	// Fetch all titles objects by title
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Title>> fetchTitlesByTitle(@PathVariable String title) {
		return new ResponseEntity<>(service.getTitlesByTitle(title), HttpStatus.OK);
	}

	// Fetch all titles objects by title and fromdate
	@GetMapping("/title/{title}/fromDate/{fromDate}")
	public ResponseEntity<List<Title>> fetchTitleByTitleAndFromDate(@PathVariable String title, @PathVariable Date fromDate) {
		return new ResponseEntity<>(service.getTitleByTitleAndFromDate(title, fromDate), HttpStatus.OK);
	}

	// Fetch all titles objects by empno and fromdate
	@GetMapping("/empNo/{empNo}/fromDate/{fromDate}")
	public ResponseEntity<List<Title>> fetchTitleByEmpNoAndfromDate(@PathVariable Employee empNo, @PathVariable Date fromDate) {
		return new ResponseEntity<>(service.getTitleByEmpNoAndFromDate(empNo, fromDate), HttpStatus.OK);
	}

	// Fetch all titles objects by empno and title
	@GetMapping("/empNo/{empNo}/title/{title}")
	public ResponseEntity<List<Title>> fetchTitleByEmpNoAndTitle(@PathVariable Employee empNo, @PathVariable String title) {
		return new ResponseEntity<>(service.getTitleByEmpNoAndTitle(empNo, title), HttpStatus.OK);
	}

	// Update titles details for the given fromdate
	@PutMapping("/fromDate/{fromDate}")
	public ResponseEntity<List<Title>> updateTitleByFromDate(@Valid @PathVariable Date fromDate, @RequestBody Title title) {
		return new ResponseEntity<>(service.updateTitleByFromDate(fromDate, title), HttpStatus.OK);
	}

	// Update titles details for the given title
	@PutMapping("/title/{title}")
	public ResponseEntity<List<Title>> updateTitleByTitle(@Valid @PathVariable String title, @RequestBody Title title1) {
		return new ResponseEntity<>(service.updateTitleByTitle(title, title1), HttpStatus.OK);
	}

	// Update titles details for the given employee no
	@PutMapping("/empNo/{empNo}")
	public ResponseEntity<List<Title>> updateTitleByEmpNo(@Valid @PathVariable int empNo, @RequestBody Title title) {
		return new ResponseEntity<>(service.updateTitleByEmpNo(empNo, title), HttpStatus.OK);
	}

	// Update title by empno, fromdate, title
	@PutMapping("/empNo/{empNo}/fromDate/{fromDate}/title/{title}")
	public ResponseEntity<String> updateTitleByEmpNoAndFromDate(@Valid @PathVariable Employee empNo,
			@PathVariable Date fromDate, @PathVariable String title, @RequestBody Title title2) {
		return new ResponseEntity<>(service.updateTitleByEmpNoAndFromDate(empNo, fromDate, title, title2),
				HttpStatus.OK);
	}

	// Delete title by empno, from date and title name.
	@DeleteMapping("/empNo/{empNo}/fromDate/{fromDate}/title/{title}")
	public ResponseEntity<String> deletsTitleByEmpNoFromDateAndTitle(@Valid @PathVariable Employee empNo,
			@PathVariable Date fromDate, @PathVariable String title) {
		return new ResponseEntity<>(service.deleteTitleByEmpNoFromDateAndTitle(empNo, fromDate, title), HttpStatus.OK);
	}

	// Delete titles by empno
	@DeleteMapping("/empNo/{empNo}")
	public ResponseEntity<List<Title>> deleteTitleByEmpNo(@PathVariable Employee empNo) {
		return new ResponseEntity<>(service.deleteTitleByEmpNo(empNo), HttpStatus.OK);
	}

	// Delete titles by fromdate
	@DeleteMapping("/fromDate/{fromDate}")
	public ResponseEntity<List<Title>> deleteTitleByFromDate(@PathVariable Date fromDate) {
		return new ResponseEntity<>(service.deleteTitleByFromDate(fromDate), HttpStatus.OK);
	}

	// Delete titles by title
	@DeleteMapping("/title/{title}")
	public ResponseEntity<List<Title>> deleteTitleByTitle(@PathVariable String title) {
		return new ResponseEntity<>(service.deleteTitleByTitle(title), HttpStatus.OK);
	}

}
