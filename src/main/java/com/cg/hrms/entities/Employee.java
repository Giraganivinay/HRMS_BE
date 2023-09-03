package com.cg.hrms.entities;


import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "employees")
@SequenceGenerator(name = "my_sequence", sequenceName = "employee_sequence", initialValue = 500000, allocationSize = 1)
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence") 
	@Column(name = "emp_no")
	private int empNo;

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date birthDate;

	@Column(name = "first_name", length = 14)
	@NotNull
	private String firstName;

	@Column(name = "last_name", length = 16)
	@NotNull
	private String lastName;

	@Column(name = "gender", columnDefinition = "ENUM('M', 'F')")
	@NotNull
	private String gender;

	@Column(name = "hire_date")
	@NotNull
	private Date hireDate;

	public Employee() {
		super();
	}

	public Employee(int empNo, Date birthDate, String firstName, String lastName, String gender, Date hireDate) {
		super();
		this.empNo = empNo;
		this.birthDate = birthDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.hireDate = hireDate;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	@Override
	public String toString() {
		return "Employee [empNo=" + empNo + ", birthDate=" + birthDate + ", firstName=" + firstName + ", lastName="
				+ lastName + ", gender=" + gender + ", hireDate=" + hireDate + "]";
	}
}
