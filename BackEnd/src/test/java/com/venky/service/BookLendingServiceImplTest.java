package com.venky.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.venky.dto.BookLendingDto;
import com.venky.entity.Book;
import com.venky.entity.BookLending;
import com.venky.entity.User;
import com.venky.repository.BookLendingRepository;
import com.venky.repository.BookRepository;
import com.venky.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class BookLendingServiceImplTest {
	@Mock
	BookLendingRepository bookLendingRepository;
	
	@Mock
	 UserRepository userRepository;
	
	@Mock
	 BookRepository bookRepository;
	
	@InjectMocks
	BookLendingServiceImpl bookLendingServiceImpl;

	@Test
	void testAddBookLending() {
	    // Arrange
	    BookLendingDto bookLendingDto = new BookLendingDto();
	    bookLendingDto.setBookId(List.of(1, 2));
	    bookLendingDto.setDueDate(LocalDate.now().plusDays(7));
	    bookLendingDto.setFee(100);
	    bookLendingDto.setIssuedBy(null);
	    bookLendingDto.setUserId(1);

	    BookLending bookLending = new BookLending();
	    bookLending.setId(1);
	    bookLending.setBookId(List.of(1, 2));

	    User user = new User();
	    user.setId(1);
	    user.setBooks(new ArrayList());

	    Book book1 = new Book();
	    book1.setId(1);

	    Book book2 = new Book();
	    book2.setId(2);

	    when(userRepository.findById(1)).thenReturn(Optional.of(user));
	    when(bookRepository.findById(1)).thenReturn(Optional.of(book1));
	    when(bookRepository.findById(2)).thenReturn(Optional.of(book2));
	    when(bookLendingRepository.save(any(BookLending.class))).thenReturn(bookLending);

	    // Act
	    BookLendingDto result = bookLendingServiceImpl.addBookLending(bookLendingDto);

	    // Assert
	    assertNotNull(result);
	    assertEquals(2, result.getBookId().size());
	    assertTrue(result.getBookId().contains(1));
	    assertTrue(result.getBookId().contains(2));

	    verify(userRepository).save(any(User.class));
	   
	}





	@Test
	void testGetAll() {
	    // Arrange
	    BookLending bookLending1 = new BookLending();
	    bookLending1.setId(1);
	    bookLending1.setBookId(List.of(1));

	    BookLending bookLending2 = new BookLending();
	    bookLending2.setId(2);
	    bookLending2.setBookId(List.of(2));

	    when(bookLendingRepository.findAll()).thenReturn(List.of(bookLending1, bookLending2));

	    // Act
	    List<BookLendingDto> result = bookLendingServiceImpl.getAll();

	    // Assert
	    assertNotNull(result);
	    assertEquals(2, result.size());
	    assertEquals(1, result.get(0).getId());
	    assertEquals(2, result.get(1).getId());

	    verify(bookLendingRepository).findAll();
	}


	@Test
	void testGetById() {
	    // Arrange
	    BookLending bookLending = new BookLending();
	    bookLending.setId(1);
	    bookLending.setBookId(List.of(1));

	    when(bookLendingRepository.findById(1)).thenReturn(Optional.of(bookLending));

	    // Act
	    BookLendingDto result = bookLendingServiceImpl.getById(1);

	    // Assert
	    assertNotNull(result);
	    assertEquals(1, result.getId());
	    assertEquals(1, result.getBookId().size());
	    assertTrue(result.getBookId().contains(1));

	    verify(bookLendingRepository).findById(1);
	}


}
