package com.venky.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venky.dto.BookLendingDto;
import com.venky.entity.Book;
import com.venky.entity.BookLending;
import com.venky.entity.User;
import com.venky.exception.BookLendingNotFoundException;
import com.venky.repository.BookLendingRepository;
import com.venky.repository.BookRepository;
import com.venky.repository.UserRepository;

@Service
public class BookLendingServiceImpl implements BookLendingService {
	@Autowired
	private BookLendingRepository bookLendingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public BookLendingDto addBookLending(BookLendingDto bookLendingDto) {
	    // Set the current date
	    LocalDate currentDate = LocalDate.now();

	    // Create and populate BookLending entity
	    BookLending bookLending = new BookLending();
	    bookLending.setBookId(bookLendingDto.getBookId());
	    bookLending.setDate(currentDate); // Set current date
	    bookLending.setDueDate(bookLendingDto.getDueDate());
	    bookLending.setFee(bookLendingDto.getFee());
	    bookLending.setIssuedBy(bookLendingDto.getIssuedBy());
	    bookLending.setPenalty(bookLendingDto.getPenalty());
	    bookLending.setReturnDate(bookLendingDto.getReturnDate());
	    bookLending.setUserId(bookLendingDto.getUserId());

	    BookLending savedBookLending = bookLendingRepository.save(bookLending);

	    // Fetch User entity and update its books list
	    User user = userRepository.findById(bookLendingDto.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found with id " + bookLendingDto.getUserId()));

	    List<Book> books = user.getBooks();
	    List<Integer> bookIds = bookLendingDto.getBookId();
	    
	    for (Integer bookId : bookIds) {
	        Book book = bookRepository.findById(bookId)
	                .orElseThrow(() -> new RuntimeException("Book not found with id " + bookId));

	        if (!books.contains(book)) {
	            books.add(book);
	        }

	        // Update the isBorrowed status to true
	        book.setBorrowed(true);
	        bookRepository.save(book);
	    }

	    user.setBooks(books);
	    userRepository.save(user);

	    // Create and populate BookLendingDto to return
	    BookLendingDto savedBookLendingDto = new BookLendingDto();
	    savedBookLendingDto.setBookId(savedBookLending.getBookId());
	    savedBookLendingDto.setDate(savedBookLending.getDate());
	    savedBookLendingDto.setDueDate(savedBookLending.getDueDate());
	    savedBookLendingDto.setFee(savedBookLending.getFee());
	    savedBookLendingDto.setId(savedBookLending.getId());
	    savedBookLendingDto.setIssuedBy(savedBookLending.getIssuedBy());
	    savedBookLendingDto.setPenalty(savedBookLending.getPenalty());
	    savedBookLendingDto.setReturnDate(savedBookLending.getReturnDate());
	    savedBookLendingDto.setUserId(savedBookLending.getUserId());

	    return savedBookLendingDto;
	}
	@Override
	public BookLendingDto updateBookLending(int id, BookLendingDto bookLendingDto) {
	    System.out.println(bookLendingDto);
	    // Fetch the existing BookLending record
	    Optional<BookLending> optBookLending = bookLendingRepository.findById(id);
	    if (!optBookLending.isPresent()) {
	        throw new BookLendingNotFoundException("Record with id " + id + " not found");
	    }

	    BookLending originalBookLending = optBookLending.get();

	    // Update fields directly
//	    if (bookLendingDto.getBookId() != null) {
//	        originalBookLending.setBookId(bookLendingDto.getBookId());
//	    }
	    if (bookLendingDto.getDate() != null) {
	        originalBookLending.setDate(bookLendingDto.getDate());
	    }
	    if (bookLendingDto.getDueDate() != null) {
	        originalBookLending.setDueDate(bookLendingDto.getDueDate());
	    }
	    if (bookLendingDto.getFee() != 0) {
	        originalBookLending.setFee(bookLendingDto.getFee());
	    }
	    if (bookLendingDto.getIssuedBy() != null) {
	        originalBookLending.setIssuedBy(bookLendingDto.getIssuedBy());
	    }
	    if (bookLendingDto.getUserId() != 0) {
	        originalBookLending.setUserId(bookLendingDto.getUserId());
	    }

	    // Set returnDate to current date and calculate penalty
	    LocalDate currentDate = LocalDate.now();
	    originalBookLending.setReturnDate(currentDate);

	    LocalDate dueDate = originalBookLending.getDueDate();
	    if (currentDate.isAfter(dueDate)) {
	        long daysOverdue = java.time.Duration.between(dueDate.atStartOfDay(), currentDate.atStartOfDay()).toDays();
	        double penalty = daysOverdue * 5; // Rs 5 per day
	        originalBookLending.setPenalty(penalty);
	    }
	    System.out.println("original"+originalBookLending);

	    // Save the updated BookLending record
	    BookLending updatedBookLending = bookLendingRepository.save(originalBookLending);

	    // Remove the returned books from the user's book list and update `isBorrowed` status
	    User user = userRepository.findById(updatedBookLending.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found with id " + updatedBookLending.getUserId()));

	    List<Book> books = user.getBooks();
	    List<Integer> bookIds = updatedBookLending.getBookId();

	    // Prepare a list to store remaining book IDs after removal
	    List<Integer> remainingBookIds = new ArrayList<>(originalBookLending.getBookId());

	    for (Integer returnedBookId : bookLendingDto.getBookId()) {
	        Book book = bookRepository.findById(returnedBookId)
	                .orElseThrow(() -> new RuntimeException("Book not found with id " + returnedBookId));

	        // Remove the book from the user's list and update its `isBorrowed` status to false
	        books.remove(book);
	        book.setBorrowed(false);
	        bookRepository.save(book);

	        // Remove the bookId from the remainingBookIds list
	        remainingBookIds.remove(returnedBookId);
	    }

	    user.setBooks(books);
	    userRepository.save(user);

	    // Update the bookId list in BookLending with the remaining book IDs
	    updatedBookLending.setBookId(remainingBookIds);
	    System.out.println("updated"+updatedBookLending);
	    bookLendingRepository.save(updatedBookLending);

	    // Create and return BookLendingDto with updated details
	    BookLendingDto updatedBookLendingDto = new BookLendingDto();
	    updatedBookLendingDto.setBookId(updatedBookLending.getBookId());
	    updatedBookLendingDto.setDate(updatedBookLending.getDate());
	    updatedBookLendingDto.setDueDate(updatedBookLending.getDueDate());
	    updatedBookLendingDto.setFee(updatedBookLending.getFee());
	    updatedBookLendingDto.setId(updatedBookLending.getId());
	    updatedBookLendingDto.setIssuedBy(updatedBookLending.getIssuedBy());
	    updatedBookLendingDto.setPenalty(updatedBookLending.getPenalty());
	    updatedBookLendingDto.setReturnDate(updatedBookLending.getReturnDate());
	    updatedBookLendingDto.setUserId(updatedBookLending.getUserId());

	    return updatedBookLendingDto;
	}


	@Override
	public List<BookLendingDto> getAll() {
	    List<BookLending> bookLendings = bookLendingRepository.findAll();
	    List<BookLendingDto> bookLendingDtos = new ArrayList<>();

	    for (BookLending bookLending : bookLendings) {
	        // Ensure that bookId list is not empty
	        if (bookLending.getBookId() != null && !bookLending.getBookId().isEmpty()) {
	            BookLendingDto bookLendingDto = new BookLendingDto();
	            bookLendingDto.setBookId(bookLending.getBookId());
	            bookLendingDto.setDate(bookLending.getDate());
	            bookLendingDto.setDueDate(bookLending.getDueDate());
	            bookLendingDto.setFee(bookLending.getFee());
	            bookLendingDto.setId(bookLending.getId());
	            bookLendingDto.setIssuedBy(bookLending.getIssuedBy());
	            bookLendingDto.setPenalty(bookLending.getPenalty());
	            bookLendingDto.setReturnDate(bookLending.getReturnDate());
	            bookLendingDto.setUserId(bookLending.getUserId());
	            bookLendingDtos.add(bookLendingDto);
	        }
	    }
	    return bookLendingDtos;
	}




	@Override
	public BookLendingDto getById(int id) {
		BookLending bookLending=bookLendingRepository.findById(id).orElseThrow(()->new BookLendingNotFoundException("BookLending with id "+id+" not found"));
		BookLendingDto bookLendingDto=new BookLendingDto();
		bookLendingDto.setBookId(bookLending.getBookId());
		bookLendingDto.setDate(bookLending.getDate());
		bookLendingDto.setDueDate(bookLending.getDueDate());
		bookLendingDto.setFee(bookLending.getFee());
		bookLendingDto.setId(bookLending.getId());
		bookLendingDto.setPenalty(bookLending.getPenalty());
		bookLendingDto.setReturnDate(bookLending.getReturnDate());
		bookLendingDto.setUserId(bookLending.getUserId());
		return bookLendingDto;
	}


}
