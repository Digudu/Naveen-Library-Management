package com.venky.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.venky.dto.UserDto;
import com.venky.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> users=userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(users,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserDto> addBook(@RequestBody UserDto userDto){
		UserDto createdUser=userService.addUser(userDto);
		return new ResponseEntity<UserDto>(createdUser,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id){
		UserDto user=userService.getUserById(id);
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateById(@PathVariable("id") int id,@RequestBody UserDto userDto){
		UserDto updatedUser=userService.updateUserById(id, userDto);
		return new ResponseEntity<UserDto>(updatedUser,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") int id){
		String deletedUser=userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	
	
	}
}
