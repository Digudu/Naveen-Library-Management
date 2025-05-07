package com.venky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.venky.dto.BookDto;
import com.venky.entity.Book;
import com.venky.exception.BookNotFoundException;
import com.venky.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	BookRepository bookRepository;
	

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<BookDto> getAllAvailableBooks() {
	    // Fetch all books where isBorrowed is false
	    List<Book> books = bookRepository.findByBorrowedFalse();
	    List<BookDto> bookDtos = new ArrayList<>();

	    for (Book book : books) {
	        BookDto bookDto = new BookDto();
	        bookDto.setAuthor(book.getAuthor());
	        bookDto.setId(book.getId());
	        bookDto.setIsbn(book.getIsbn());
	        bookDto.setPrice(book.getPrice());
	        bookDto.setRating(book.getRating());
	        bookDto.setTitle(book.getTitle());
	        bookDto.setType(book.getType());
	        bookDtos.add(bookDto);
	    }
	    return bookDtos;
	}
	
	@Override
	public List<BookDto> getAllBooks() {
	    // Fetch all books where isBorrowed is false
	    List<Book> books = bookRepository.findByDeletedFalse();
	    List<BookDto> bookDtos = new ArrayList<>();

	    for (Book book : books) {
	        BookDto bookDto = new BookDto();
	        bookDto.setAuthor(book.getAuthor());
	        bookDto.setId(book.getId());
	        bookDto.setIsbn(book.getIsbn());
	        bookDto.setPrice(book.getPrice());
	        bookDto.setRating(book.getRating());
	        bookDto.setTitle(book.getTitle());
	        bookDto.setType(book.getType());
	        bookDtos.add(bookDto);
	    }
	    return bookDtos;
	}



	@Override
	public BookDto addBook(BookDto bookDto) {
		Book book=new Book();
		book.setBorrowed(false);
		book.setAuthor(bookDto.getAuthor());
		book.setId(bookDto.getId());
		book.setIsbn(bookDto.getIsbn());
		book.setPrice(bookDto.getPrice());
		book.setRating(bookDto.getRating());
		book.setTitle(bookDto.getTitle());
		book.setType(bookDto.getType());
	    Book savedBook	=bookRepository.save(book);
	    BookDto savedBookDto=new BookDto();
	    savedBookDto.setAuthor(savedBook.getAuthor());
	    savedBookDto.setId(savedBook.getId());
	    savedBookDto.setIsbn(savedBook.getIsbn());
	    savedBookDto.setRating(savedBook.getRating());
	    savedBookDto.setPrice(savedBook.getPrice());
	    savedBookDto.setTitle(savedBook.getTitle());
	    savedBookDto.setType(savedBook.getType());
	    
		return savedBookDto;
	}

	@Override
	public BookDto getById(int id) {
		Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book With id "+id+" not found"));
		BookDto bookDto=new BookDto();
		bookDto.setAuthor(book.getAuthor());
		bookDto.setId(book.getId());
		bookDto.setIsbn(book.getIsbn());
		bookDto.setPrice(book.getPrice());
		bookDto.setRating(book.getRating());
		bookDto.setTitle(book.getTitle());
		bookDto.setType(book.getType());
		return bookDto;
	}

	@Override
	public BookDto updateById(int id, BookDto bookDto) {
		Optional<Book> optBook=bookRepository.findById(id);
		if(!optBook.isPresent()) {
			throw new BookNotFoundException("Book with id "+id+" not found");
		}
		Book originalBook=optBook.get();
		if(bookDto.getAuthor()!=null) {
			originalBook.setAuthor(bookDto.getAuthor());
		}
		if(bookDto.getIsbn()!=null) {
			originalBook.setIsbn(bookDto.getIsbn());
		}
		if(bookDto.getPrice()!=0) {
			originalBook.setPrice(bookDto.getPrice());
		}
		if(bookDto.getRating()!=0) {
			originalBook.setRating(bookDto.getRating());
		}
		if(bookDto.getTitle()!=null) {
			originalBook.setTitle(bookDto.getTitle());
		}
		if(bookDto.getType()!=null) {
			originalBook.setType(bookDto.getType());
		}
		Book updatedBook=bookRepository.save(originalBook);
		BookDto updatedBookDto=new BookDto();
		updatedBookDto.setAuthor(updatedBook.getAuthor());
		updatedBookDto.setId(updatedBook.getId());
		updatedBookDto.setIsbn(updatedBook.getIsbn());
		updatedBookDto.setPrice(updatedBook.getPrice());
		updatedBookDto.setRating(updatedBook.getRating());
		updatedBookDto.setTitle(updatedBook.getTitle());
		updatedBookDto.setType(updatedBook.getType());
		return updatedBookDto;
	}

	@Override
	public String deleteById(int id) {
		Optional<Book> optBook=bookRepository.findById(id);
		if(!optBook.isPresent()) {
			throw new BookNotFoundException("Book with id "+id+" not found");
		}
		Book book= optBook.get();
		book.setDeleted(true);
		book.setBorrowed(true);
		bookRepository.save(book);
		return "Book With id "+id+" is Successfully Deleted";
	}

}
