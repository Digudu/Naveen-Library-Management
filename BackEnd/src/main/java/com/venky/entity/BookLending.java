package com.venky.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BookLending {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int userId;
	
	private List<Integer> bookId;
	
	 @ManyToOne
	private Librarian issuedBy;
	
	private LocalDate date;
	
	private LocalDate dueDate;
	
	private LocalDate returnDate;
	
	private double fee;
	
	private double penalty;
	

}
