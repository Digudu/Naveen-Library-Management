package com.venky.dto;

import java.time.LocalDate;
import java.util.List;

import com.venky.entity.Librarian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookLendingDto {
    private int id;
	
	private int userId;
	
	private List<Integer> bookId;
	
	private Librarian issuedBy;
	
	private LocalDate date;
	
	private LocalDate dueDate;
	
	private LocalDate returnDate;
	
	private double fee;
	
	private double penalty;
	

}
