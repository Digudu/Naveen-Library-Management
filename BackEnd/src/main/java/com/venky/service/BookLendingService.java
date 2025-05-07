package com.venky.service;

import java.util.List;


import com.venky.dto.BookLendingDto;

public interface BookLendingService {
	List<BookLendingDto> getAll();
	BookLendingDto addBookLending(BookLendingDto bookLendingDto);
	BookLendingDto updateBookLending(int id,BookLendingDto bookLendingDto);
	BookLendingDto getById(int id);
	

}
