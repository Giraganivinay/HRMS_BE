package com.cg.hrms.dto;

import java.sql.Date;

public class EmployeeTitleDto {

	private int empno;

	private String name;

	private Date fromDate;

	private Date toDate;

	private String designation;

	public EmployeeTitleDto() {
		super();
	}

	public EmployeeTitleDto(int empno, String name, Date fromDate, Date toDate, String designation) {
		super();
		this.empno = empno;
		this.name = name;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.designation = designation;
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return "EmployeeTitleDto [empno=" + empno + ", name=" + name + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", designation=" + designation + "]";
	}

}