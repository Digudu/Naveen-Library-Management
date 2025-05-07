package com.venky.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venky.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	 List<Book> findByBorrowedFalse();
	 List<Book> findByDeletedFalse();

}
