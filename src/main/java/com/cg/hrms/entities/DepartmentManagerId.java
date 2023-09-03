package com.cg.hrms.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class DepartmentManagerId implements Serializable {
	
	@ManyToOne
	@JoinColumn(name = "emp_no")
	@NotNull
	private Employee empNo;

	@ManyToOne
	@JoinColumn(name = "dept_no", columnDefinition = "char(4)")
	@NotNull
	private Department deptNo;

	public DepartmentManagerId() {
		super();
	}

	public DepartmentManagerId(Employee empNo, Department deptNo) {
		super();
		this.empNo = empNo;
		this.deptNo = deptNo;
	}

	public Employee getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Employee empNo) {
		this.empNo = empNo;
	}

	public Department getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Department deptNo) {
		this.deptNo = deptNo;
	}

	@Override
	public String toString() {
		return "DepartmentManagerId [empNo=" + empNo + ", deptNo=" + deptNo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(deptNo, empNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentManagerId other = (DepartmentManagerId) obj;
		return Objects.equals(deptNo, other.deptNo) && empNo == other.empNo;
	}

}
