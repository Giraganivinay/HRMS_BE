package com.cg.hrms.entities;

import java.sql.Date;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "salaries")
public class Salary {

	@EmbeddedId
	private SalaryId id;

	@Column(name = "salary")
	@NotNull
	private int salary;

	@Column(name = "to_date")
	@NotNull
	private Date toDate;

	public Salary() {
		super();
	}

	public Salary(SalaryId id, int salary, Date toDate) {
		super();
		this.id = id;
		this.salary = salary;
		this.toDate = toDate;
	}

	public SalaryId getId() {
		return id;
	}

	public void setId(SalaryId id) {
		this.id = id;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "Salary [id=" + id + ", salary=" + salary + ", toDate=" + toDate + "]";
	}

}
