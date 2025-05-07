package com.venky.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venky.dto.BookDto;
import com.venky.service.BookService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {
	private BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping
	public ResponseEntity<List<BookDto>> getAllAvailableBooks(){
		List<BookDto> books=bookService.getAllAvailableBooks();
		return new ResponseEntity<List<BookDto>>(books,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<BookDto>> getAllBooks(){
		List<BookDto> books=bookService.getAllBooks();
		return new ResponseEntity<List<BookDto>>(books,HttpStatus.OK);
	}
	
	
	
	@PostMapping
	public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto){
		BookDto createdBook=bookService.addBook(bookDto);
		return new ResponseEntity<BookDto>(createdBook,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getById(@PathVariable("id") int id){
		BookDto book=bookService.getById(id);
		return new ResponseEntity<BookDto>(book,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BookDto> updateById(@PathVariable("id") int id,@RequestBody BookDto bookDto){
		BookDto updatedBook=bookService.updateById(id, bookDto);
		return new ResponseEntity<BookDto>(updatedBook,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id){
		String deletedBook=bookService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
