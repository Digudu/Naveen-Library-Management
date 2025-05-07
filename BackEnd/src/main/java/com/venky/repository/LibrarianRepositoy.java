package com.venky.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venky.entity.Librarian;

public interface LibrarianRepositoy extends JpaRepository<Librarian, Integer> {
	Optional<Librarian> findByEmail(String email);

}
