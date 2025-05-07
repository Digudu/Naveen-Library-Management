package com.venky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
	private int id;
	private String title;
	private String author;
	private String type;
	private double price;
	private String isbn;
	private int rating;
	private boolean borrowed;
	private boolean deleted;

}
