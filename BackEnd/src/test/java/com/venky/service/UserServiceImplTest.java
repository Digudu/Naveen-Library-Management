package com.venky.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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

import com.venky.dto.UserDto;
import com.venky.entity.Address;
import com.venky.entity.User;
import com.venky.repository.AddressRepository;
import com.venky.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	@Mock
	UserRepository userRepository;
	
	@Mock
	AddressRepository addressRepository;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Test
	void testGetAllUsers() {
	    List<User> users = List.of(
	            new User(1, "John", "Male", LocalDate.of(1990, 1, 1), "john@example.com", "1234567890", false, new Address(), new ArrayList<>()),
	            new User(2, "Jane", "Female", LocalDate.of(1992, 2, 2), "jane@example.com", "0987654321", false, new Address(), new ArrayList<>())
	    );
	    when(userRepository.findByDeletedFalse()).thenReturn(users);
	    
	    List<UserDto> userDtos = userServiceImpl.getAllUsers();
	    
	    assertAll(
	            () -> assertEquals(2, userDtos.size()),
	            () -> assertEquals("John", userDtos.get(0).getName()),
	            () -> assertEquals("Jane", userDtos.get(1).getName())
	    );
	}


	@Test
	void testAddUser() {
	    Address address = new Address(1, "123 Main St", "City", "State", "Country", "123456");
	    User user = new User(1, "John", "Male", LocalDate.of(1990, 1, 1), "john@example.com", "1234567890", false, address, new ArrayList<>());
	    UserDto userDto = new UserDto(1, "John", "Male", LocalDate.of(1990, 1, 1), "john@example.com", "1234567890", address, new ArrayList<>(), false);
	    
	    when(addressRepository.findById(1)).thenReturn(Optional.of(address));
	    when(userRepository.save(any(User.class))).thenReturn(user);
	    
	    UserDto createdUser = userServiceImpl.addUser(userDto);
	    
	    assertAll(
	            () -> assertEquals(userDto.getName(), createdUser.getName()),
	            () -> assertEquals(userDto.getEmail(), createdUser.getEmail())
	    );
	}


	@Test
	void testGetUserById() {
	    User user = new User(1, "John", "Male", LocalDate.of(1990, 1, 1), "john@example.com", "1234567890", false, new Address(), new ArrayList<>());
	    when(userRepository.findById(1)).thenReturn(Optional.of(user));
	    
	    UserDto userDto = userServiceImpl.getUserById(1);
	    
	    assertAll(
	            () -> assertEquals("John", userDto.getName()),
	            () -> assertEquals("john@example.com", userDto.getEmail())
	    );
	}


	@Test
	void testDeleteUserById() {
	    User user = new User(1, "John", "Male", LocalDate.of(1990, 1, 1), "john@example.com", "1234567890", false, new Address(), new ArrayList<>());
	    when(userRepository.findById(1)).thenReturn(Optional.of(user));
	    
	    String result = userServiceImpl.deleteUserById(1);
	    
	    assertTrue(user.isDeleted());  
	  
	    verify(userRepository, times(1)).save(user);
	    assertEquals("User with id 1 Deleted Successfully", result);
	}



}
