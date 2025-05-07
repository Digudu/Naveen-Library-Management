package com.venky.service;

import java.util.List;

import com.venky.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAllAddress();
	AddressDto getAddressById(int id);
	AddressDto addAddress(AddressDto addressDto);
	AddressDto updateAddress(int id,AddressDto addressDto);
	String deleteAddress(int id);

}
