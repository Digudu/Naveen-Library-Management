package com.venky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venky.dto.LoginDto;
import com.venky.entity.Librarian;
import com.venky.repository.LibrarianRepositoy;

@Service
public class AuthServiceImpl implements AuthService {

    private final LibrarianRepositoy librarianRepositoy;

    @Autowired
    public AuthServiceImpl(LibrarianRepositoy librarianRepositoy) {
        this.librarianRepositoy = librarianRepositoy;
    }

    @Override
    public Librarian login(LoginDto loginDto) {
        Librarian librarian = librarianRepositoy.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Librarian not found with email: " + loginDto.getEmail()));

        // Directly compare plain-text passwords (not secure for production)
        if (!loginDto.getPassword().equals(librarian.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return librarian;
    }
}
