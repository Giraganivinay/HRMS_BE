package com.cg.hrms.entities;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "dept_emp")
public class DepartmentEmployee implements Serializable {

	@EmbeddedId
	private DepartmentEmployeeId id;

	@Column(name = "from_date")
	@NotNull
	private Date fromDate;

	@Column(name = "to_date")
	@NotNull
	private Date toDate;

	public DepartmentEmployee() {
		super();
	}

	public DepartmentEmployee(DepartmentEmployeeId id, Date fromDate, Date toDate) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public DepartmentEmployeeId getId() {
		return id;
	}

	public void setId(DepartmentEmployeeId id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "DepartmentEmployee [id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}

}
