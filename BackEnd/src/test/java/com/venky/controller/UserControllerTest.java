package com.venky.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


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
import com.venky.dto.UserDto;
import com.venky.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = List.of(new UserDto(1, "John", "Doe", null, null, null, null, null, false), new UserDto(2, "Jane", "Doe", null, null, null, null, null, false));
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(users)));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testAddBook() throws Exception {
        UserDto userDto = new UserDto(1, "John", "Doe", null, null, null, null, null, false);
        when(userService.addUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(userDto)))
            .andExpect(status().isCreated())
            .andExpect(content().json(mapper.writeValueAsString(userDto)));

        verify(userService, times(1)).addUser(any(UserDto.class));
    }

    @Test
    void testGetUserById() throws Exception {
        UserDto userDto = new UserDto(1, "John", "Doe", null, null, null, null, null, false);
        when(userService.getUserById(1)).thenReturn(userDto);

        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(userDto)));

        verify(userService, times(1)).getUserById(1);
    }

    @Test
    void testUpdateById() throws Exception {
        UserDto userDto = new UserDto(1, "John", "Doe", null, null, null, null, null, false);
        when(userService.updateUserById(eq(1), any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(put("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(userDto)))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(userDto)));

        verify(userService, times(1)).updateUserById(eq(1), any(UserDto.class));
    }

//    @Test
//    void testDeleteUserById() throws Exception {
//        doNothing().when(userService).deleteUserById(1);
//
//        mockMvc.perform(delete("/api/users/1"))
//            .andExpect(status().isNoContent());
//
//       // verify(userService, times(1)).deleteUserById(1);
//    }
}
