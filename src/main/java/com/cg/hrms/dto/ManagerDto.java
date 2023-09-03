package com.cg.hrms.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
	
	private int id;
	
	private String name;
	
	private Date dateOfBirth;
	
	private String deptName;

}
