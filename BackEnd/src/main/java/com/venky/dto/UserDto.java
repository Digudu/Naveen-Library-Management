package com.venky.dto;

import java.time.LocalDate;
import java.util.List;

import com.venky.entity.Address;
import com.venky.entity.Book;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
	private int id;
	
	private String name;
	
	private String gender;
	
	private LocalDate dob;
	
	private String email;
	
	private String mobile;
	
	private Address address;
	
	private List<Book> books;
	
	private boolean deleted;

}
