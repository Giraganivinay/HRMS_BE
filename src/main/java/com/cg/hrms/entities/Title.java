package com.cg.hrms.entities;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "titles")
public class Title implements Serializable {
	@EmbeddedId
	private TitleId id;
	
	@Column(name = "to_date")
	private Date toDate;

	public Title() {
		super();
	}

	public Title(TitleId id, Date toDate) {
		super();
		this.id = id;
		this.toDate = toDate;
	}

	public TitleId getId() {
		return id;
	}

	public void setId(TitleId id) {
		this.id = id;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "Title [id=" + id + ", toDate=" + toDate + "]";
	}

}
