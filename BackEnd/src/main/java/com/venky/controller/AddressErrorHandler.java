package com.venky.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.venky.exception.AddressNotFoundException;

@ControllerAdvice
public class AddressErrorHandler {
	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<String> handleAddressNotFound(AddressNotFoundException ex)
	{
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException ex)
	{
		StringBuilder error=new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach(err->{
			
			String e=err.getDefaultMessage();
			error.append(e+"\n");
		});
		return new ResponseEntity<String>(error.toString(),HttpStatus.BAD_REQUEST);
	}


}
