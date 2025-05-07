package com.venky.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
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
import com.venky.dto.BookLendingDto;
import com.venky.service.BookLendingService;

@ExtendWith(MockitoExtension.class)
class BookLendingControllerTest {

    @Mock
    BookLendingService bookLendingService;

    @InjectMocks
    BookLendingController bookLendingController;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookLendingController).build();
    }

    @Test
    void testAddBookLending() throws Exception {
        BookLendingDto bookLendingDto = new BookLendingDto();
        when(bookLendingService.addBookLending(any(BookLendingDto.class))).thenReturn(bookLendingDto);

        mockMvc.perform(post("/api/bookLending")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(bookLendingDto)))
            .andExpect(status().isCreated())
            .andExpect(content().json(mapper.writeValueAsString(bookLendingDto)));

        verify(bookLendingService, times(1)).addBookLending(any(BookLendingDto.class));
    }

    @Test
    void testUpdateBookLending() throws Exception {
        BookLendingDto bookLendingDto = new BookLendingDto();
        when(bookLendingService.updateBookLending(eq(1), any(BookLendingDto.class))).thenReturn(bookLendingDto);

        mockMvc.perform(put("/api/bookLending/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(bookLendingDto)))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(bookLendingDto)));

        verify(bookLendingService, times(1)).updateBookLending(eq(1), any(BookLendingDto.class));
    }

    @Test
    void testGetAll() throws Exception {
        List<BookLendingDto> bookLendingDtos = Arrays.asList(
            new BookLendingDto(),
            new BookLendingDto()
        );
        when(bookLendingService.getAll()).thenReturn(bookLendingDtos);

        mockMvc.perform(get("/api/bookLending"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(bookLendingDtos)));

        verify(bookLendingService, times(1)).getAll();
    }

    @Test
    void testGetById() throws Exception {
        BookLendingDto bookLendingDto = new BookLendingDto();
        when(bookLendingService.getById(1)).thenReturn(bookLendingDto);

        mockMvc.perform(get("/api/bookLending/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(bookLendingDto)));

        verify(bookLendingService, times(1)).getById(1);
    }
}
