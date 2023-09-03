package com.cg.hrms.entities;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "dept_manager")
public class DepartmentManager implements Serializable {
	@EmbeddedId
	private DepartmentManagerId id;

	@Column(name = "from_date")
	@NotNull
	private Date fromDate;

	@Column(name = "to_date")
	@NotNull
	private Date toDate;

	public DepartmentManager() {
		super();
	}

	public DepartmentManager(DepartmentManagerId id, Date fromDate, Date toDate) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public DepartmentManagerId getId() {
		return id;
	}

	public void setId(DepartmentManagerId id) {
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
		return "DepartmentManager [id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}

}