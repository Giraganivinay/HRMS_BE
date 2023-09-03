package com.cg.hrms.dto;

import java.time.LocalDate;

public class ErrorResp {

	private String message;
	private LocalDate timeStamp;

	public ErrorResp() {
		super();
	}

	public ErrorResp(String message) {
		super();
		this.message = message;
		this.timeStamp = LocalDate.now();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "ErrorResp [message=" + message + ", time=" + timeStamp + "]";
	}

}
