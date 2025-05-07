package com.venky.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.venky.dto.AddressDto;
import com.venky.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/address")
@CrossOrigin
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	@GetMapping
	public ResponseEntity<List<AddressDto>> getAllAddress()
	{
		List<AddressDto> address=addressService.getAllAddress();
		return new ResponseEntity<List<AddressDto>>(address,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<AddressDto> getById(@PathVariable int id)
	{
		AddressDto address=addressService.getAddressById(id);
		return new ResponseEntity<AddressDto>(address,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody AddressDto addressDto)
	{
		System.out.println(addressDto);
		AddressDto createdAddress=addressService.addAddress(addressDto);
		
		return new ResponseEntity<AddressDto>(createdAddress,HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<AddressDto> updateAddress(@PathVariable int id,@Valid@RequestBody AddressDto addressDto)
	{
		AddressDto updatedAddress=addressService.updateAddress(id, addressDto);
		return new ResponseEntity<AddressDto>(updatedAddress,HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAddress(@PathVariable int id)
	{
		String deleteAddress=addressService.deleteAddress(id);
		return ResponseEntity.noContent().build();
	}
	
}

