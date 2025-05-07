package com.venky.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.venky.dto.BookDto;
import com.venky.entity.Book;
import com.venky.entity.User;
import com.venky.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
	
	@Mock
	BookRepository bookRepository;
	
	@InjectMocks
	BookServiceImpl bookServiceImpl;

	@Test
	void testGetAll() {
		List<Book> books=List.of(
				new Book(1, "Java", "Venky", "Book", 5000.0, "ISBN", 3, false, false),
				new Book(2, "React", "Ramesh", "Book", 5000.0, "ISBN", 4, false, false),
				new Book(3, "Data Science", "Venkatesh", "Book", 5000.0, "ISBN", 5, false, false)
				);
		when(bookRepository.findByDeletedFalse()).thenReturn(books);
		List<BookDto> bookDtos=bookServiceImpl.getAllBooks();
		assertAll(
				()->assertEquals(3,bookDtos.size()),
				()->assertEquals("Data Science", bookDtos.get(2).getTitle()),
				()->assertEquals("Venky", bookDtos.get(0).getAuthor())
				);
	}

	@Test
	void testAddBook() {
		Book book=new Book(1, "Java", "Venky", "Book", 100, "ISBN", 5, false, false);
		BookDto bookDto=new BookDto(1,"Java","Venky","Book",100,"ISBN",5, false, false);
		when(bookRepository.save(book)).thenReturn(book);
		BookDto createdBook=bookServiceImpl.addBook(bookDto);
		assertAll(
				()->assertEquals(bookDto, createdBook),
				()->assertEquals("Java", createdBook.getTitle())
				);	
		

	}

	@Test
	void testGetById() {
		Optional<Book> book=Optional.of(new Book(1, "Java", "Venky", "Book", 100, "ISBN", 5, false, false));
		when(bookRepository.findById(1)).thenReturn(book);
		BookDto bookDto=bookServiceImpl.getById(1);
		assertAll(
				()->assertEquals("Venky", bookDto.getAuthor())
				);
		
	}

	@Test
	void testUpdateById() {
		Book originalBook=new Book(1, "Java", "Venky", "Book", 150, "ISBN", 3, false, false);
		BookDto updateBook=new BookDto(0, "Data Science", "Venkatesh", null, 0, null, 0, false, false);
		when(bookRepository.findById(1)).thenReturn(Optional.of(originalBook));
		when(bookRepository.save(any(Book.class))).thenReturn(originalBook);
		BookDto updatedBook=bookServiceImpl.updateById(1, updateBook);
		assertAll(
				()->assertEquals("Venkatesh",updatedBook.getAuthor()),
				()->assertEquals("Data Science", updatedBook.getTitle())
				);
		
	}

	@Test
	void testDeleteById() {
	    Book book = new Book(1, "Java", "Venky", "Book", 150, "ISBN", 3, false, false);
	    when(bookRepository.findById(1)).thenReturn(Optional.of(book));
	    
	    bookServiceImpl.deleteById(1);
	    
	    assertTrue(book.isDeleted());
	    verify(bookRepository, times(1)).save(book);
	}


}
