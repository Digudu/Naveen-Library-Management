package com.venky.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venky.entity.BookLending;

public interface BookLendingRepository extends JpaRepository<BookLending, Integer> {

}
