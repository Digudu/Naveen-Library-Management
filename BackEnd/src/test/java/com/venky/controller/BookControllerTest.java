package com.venky.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venky.dto.BookDto;
import com.venky.service.BookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	
	@Mock
	BookService bookService;
	
	@InjectMocks
	BookController bookController;
	
    MockMvc mockMvc;
	
	ObjectMapper mapper=new ObjectMapper();
	
	@BeforeEach
	public void setUp() {
		mockMvc=MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	void testGetAll() throws Exception {
		List<BookDto> books=List.of(
				new BookDto(1, "Java","venky","Book",1000,"ISBN",3, false, false),
				new BookDto(2, "React","venky","Book",2000,"ISBN",4, false, false),
				new BookDto(3, "Data Science","venky","Male",5000,"ISBN",5, false, false)
				);
		when(bookService.getAllBooks()).thenReturn(books);
		mockMvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").exists())
		.andExpect(jsonPath("$.size()").value(3));
		
	}

	@Test
	void testAddBook() throws Exception {
		BookDto book=new BookDto(5,"Java","venky","Book",260,"ISBN",5, false, false);
		String bookJson=mapper.writeValueAsString(book);
		when(bookService.addBook(any(BookDto.class))).thenReturn(book);
		mockMvc.perform(post("/api/books").content(bookJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$").exists())
		.andExpect(jsonPath("$.author", is("venky")));
		
	}

	@Test
	void testGetById() throws Exception {
		int id=5;
		BookDto book=new BookDto(1,"Java","venky","Book",260,"ISBN",5, false, false);
		when(bookService.getById(id)).thenReturn(book);
		mockMvc.perform(get("/api/books/{id}",id).accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title").value("Java"));
	
	}

	@Test
	void testUpdateById() throws Exception {
		int id=5;
		BookDto original=new BookDto(5,"Java","venk","Book",260,"ISBN",5, false, false);
		String bookJson=mapper.writeValueAsString(original);
		when(bookService.updateById(anyInt(), any(BookDto.class))).thenReturn(original);
		mockMvc.perform(put("/api/books/{id}",id).content(bookJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
		
	}

	@Test
	void testDeleteById() throws Exception {
		int id=5;
		mockMvc.perform(delete("/api/books/{id}",id))
		.andExpect(status().isNoContent());
		
	}

}
