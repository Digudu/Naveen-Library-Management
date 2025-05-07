package com.venky.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venky.dto.BookLendingDto;
import com.venky.service.BookLendingService;

@RestController
@RequestMapping("/api/bookLending")
@CrossOrigin
public class BookLendingController {
	@Autowired
	private BookLendingService bookLendingService;
	
	@PostMapping
	public ResponseEntity<BookLendingDto> addBookLending(@RequestBody BookLendingDto bookLendingDto) {
		System.out.println(bookLendingDto+"venky");
		BookLendingDto createdBookLendingDto=bookLendingService.addBookLending(bookLendingDto);
		return new ResponseEntity<BookLendingDto>(createdBookLendingDto,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BookLendingDto> updateBookLending(@PathVariable("id") int id,@RequestBody BookLendingDto bookLendingDto){
		System.out.println(bookLendingDto+"update");
		BookLendingDto updatedBookLendingDto=bookLendingService.updateBookLending(id, bookLendingDto);
	   return new ResponseEntity<BookLendingDto>(updatedBookLendingDto,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<BookLendingDto>> getAll(){
		List<BookLendingDto> bookLendingDtos=bookLendingService.getAll();
		return new ResponseEntity<List<BookLendingDto>>(bookLendingDtos,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookLendingDto> getById(@PathVariable("id") int id){
		BookLendingDto bookLendingDto=bookLendingService.getById(id);
		return new ResponseEntity<BookLendingDto>(bookLendingDto,HttpStatus.OK);
	}

}
