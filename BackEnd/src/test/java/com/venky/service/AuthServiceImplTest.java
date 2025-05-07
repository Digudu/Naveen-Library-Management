package com.venky.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.venky.dto.LoginDto;
import com.venky.entity.Librarian;
import com.venky.repository.LibrarianRepositoy;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    LibrarianRepositoy librarianRepositoy;
    
    @InjectMocks
    AuthServiceImpl authServiceImpl;

    @Test
    void testLogin() {
        LoginDto loginDto = new LoginDto("librarian@example.com", "password123");
        Librarian librarian = new Librarian();
        librarian.setEmail("librarian@example.com");
        librarian.setPassword("password123");

        when(librarianRepositoy.findByEmail("librarian@example.com")).thenReturn(Optional.of(librarian));


        Librarian result = authServiceImpl.login(loginDto);

   
        assertNotNull(result);
        assertEquals("librarian@example.com", result.getEmail());
        assertEquals("password123", result.getPassword());

        // Verify that the mock was called with the correct argument
        verify(librarianRepositoy).findByEmail("librarian@example.com");
    }

    @Test
    void testLogin_LibrarianNotFound() {
       
        LoginDto loginDto = new LoginDto("nonexistent@example.com", "password123");

       
        when(librarianRepositoy.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

      
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> authServiceImpl.login(loginDto));
        assertEquals("Librarian not found with email: nonexistent@example.com", thrown.getMessage());


        verify(librarianRepositoy).findByEmail("nonexistent@example.com");
    }

    @Test
    void testLogin_InvalidPassword() {

        LoginDto loginDto = new LoginDto("librarian@example.com", "wrongpassword");
        Librarian librarian = new Librarian();
        librarian.setEmail("librarian@example.com");
        librarian.setPassword("password123");

        
        when(librarianRepositoy.findByEmail("librarian@example.com")).thenReturn(Optional.of(librarian));


        RuntimeException thrown = assertThrows(RuntimeException.class, () -> authServiceImpl.login(loginDto));
        assertEquals("Invalid password", thrown.getMessage());

       
        verify(librarianRepositoy).findByEmail("librarian@example.com");
    }
}
