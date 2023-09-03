package com.cg.hrms.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class SalaryId implements Serializable {

	@ManyToOne
	@JoinColumn(name = "emp_no")
	@NotNull
	private Employee empNo;

	@Column(name = "from_date")
	@NotNull
	private Date fromDate;

	public SalaryId() {
		super();
	}

	public SalaryId(Employee empNo, Date fromDate) {
		super();
		this.empNo = empNo;
		this.fromDate = fromDate;
	}

	public Employee getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Employee empNo) {
		this.empNo = empNo;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@Override
	public String toString() {
		return "SalaryId [empNo=" + empNo + ", fromDate=" + fromDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(empNo, fromDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaryId other = (SalaryId) obj;
		return empNo == other.empNo && Objects.equals(fromDate, other.fromDate);
	}

}