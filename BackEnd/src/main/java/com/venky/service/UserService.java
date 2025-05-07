package com.venky.service;

import java.util.List;

import com.venky.dto.UserDto;

public interface UserService {
	List<UserDto> getAllUsers();
	UserDto addUser(UserDto userDto);
	UserDto getUserById(int id);
	UserDto updateUserById(int id,UserDto userDto);
	String deleteUserById(int id);

}
