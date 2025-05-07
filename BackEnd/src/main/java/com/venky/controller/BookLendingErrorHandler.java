package com.venky.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.venky.exception.BookLendingNotFoundException;


@ControllerAdvice
public class BookLendingErrorHandler {
	@ExceptionHandler(BookLendingNotFoundException.class)
	public ResponseEntity<String> handleProductNotFound(BookLendingNotFoundException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex){
		StringBuilder errors=new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach(err->{
			String e=err.getDefaultMessage();
			errors.append(e+"\n");
		});
		return new ResponseEntity<String>(errors.toString(),HttpStatus.BAD_REQUEST);
	}

}
