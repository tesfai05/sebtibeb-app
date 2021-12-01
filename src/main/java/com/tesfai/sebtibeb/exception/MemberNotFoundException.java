package com.tesfai.sebtibeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public MemberNotFoundException() {
		super();
	}

	public MemberNotFoundException(String message, Throwable cause) {
		super(message, cause);
		this.message=message;
	
	}

	public MemberNotFoundException(String message) {
		super(message);
		this.message=message;
	
	}
	

}
