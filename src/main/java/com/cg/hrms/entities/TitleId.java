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
public class TitleId implements Serializable {

	@ManyToOne
	@JoinColumn(name = "emp_no")
	@NotNull
	private Employee empNo;

	@Column(name = "title", length = 50)
	@NotNull
	private String title;

	@Column(name = "from_date")
	@NotNull
	private Date fromDate;

	public TitleId() {
		super();
	}

	public TitleId(Employee empNo, String title, Date fromDate) {
		super();
		this.empNo = empNo;
		this.title = title;
		this.fromDate = fromDate;
	}

	public Employee getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Employee empNo) {
		this.empNo = empNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@Override
	public String toString() {
		return "TitleId [empNo=" + empNo + ", title=" + title + ", fromDate=" + fromDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(empNo, title, fromDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TitleId other = (TitleId) obj;
		return Objects.equals(empNo, other.empNo) && Objects.equals(title, other.title)
				&& Objects.equals(fromDate, other.fromDate);
	}

}
