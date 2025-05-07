package com.venky.service;

import java.util.List;

import com.venky.dto.BookDto;

public interface BookService {
	public List<BookDto> getAllAvailableBooks();
	public BookDto addBook(BookDto bookDto);
	public BookDto getById(int id);
	public BookDto updateById(int id, BookDto bookDto);
	public String deleteById(int id);
	public List<BookDto> getAllBooks();

}
