package com.venky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venky.dto.AddressDto;
import com.venky.entity.Address;
import com.venky.exception.AddressNotFoundException;
import com.venky.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRespository;
	
	
	@Override
	public List<AddressDto> getAllAddress() {
		
		List<Address> addressEs=addressRespository.findAll();
		List<AddressDto> addressDtos=new ArrayList<AddressDto>();
		
		for(Address address:addressEs)
		{
			AddressDto addressDto=new AddressDto();
			addressDto.setId(address.getId());
			addressDto.setStreet(address.getStreet());
			addressDto.setCity(address.getCity());
			addressDto.setState(address.getState());
			addressDto.setCountry(address.getCountry());
			addressDto.setPincode(address.getPincode());
			addressDtos.add(addressDto);
		}
		
		return addressDtos;
	}

	@Override
	public AddressDto getAddressById(int id) {
		
		Address address=addressRespository.findById(id).
				orElseThrow(()->new AddressNotFoundException("Address is not found "+id));
		
		AddressDto addressDto=new AddressDto();
		addressDto.setId(address.getId());
		addressDto.setStreet(address.getStreet());
		addressDto.setCity(address.getCity());
		addressDto.setState(address.getState());
		addressDto.setCountry(address.getCountry());
		addressDto.setPincode(address.getPincode());
		
		
		return addressDto;
				
	}

	@Override
	public AddressDto addAddress(AddressDto addressDto) {
		
		Address address=new Address();
		address.setId(addressDto.getId());
		address.setStreet(addressDto.getStreet());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setCountry(addressDto.getCountry());
		address.setPincode(addressDto.getPincode());
		
		Address saveAddress=addressRespository.save(address);
		AddressDto saveAddressDto=new AddressDto();
		saveAddressDto.setId(saveAddress.getId());
		saveAddressDto.setStreet(saveAddress.getStreet());
		saveAddressDto.setCity(saveAddress.getCity());
		saveAddressDto.setState(saveAddress.getState());
		saveAddressDto.setCountry(saveAddress.getCountry());
		saveAddressDto.setPincode(saveAddress.getPincode());
		
		return saveAddressDto;
		
	}

	@Override
	public AddressDto updateAddress(int id, AddressDto addressDto) {
		Optional<Address> optAddress=addressRespository.findById(id);
		
		if(!optAddress.isPresent())
		{
			throw new AddressNotFoundException("Address is not found "+id);
		}
		Address orginialAddress=optAddress.get();
		if(addressDto.getStreet()!=null)
		{
			orginialAddress.setStreet(addressDto.getStreet());
		}
		if(addressDto.getCity()!=null)
		{
			orginialAddress.setCity(addressDto.getCity());
		}
		if(addressDto.getState()!=null)
		{
			orginialAddress.setState(addressDto.getState());
		}	
		if(addressDto.getCountry()!=null)
		{
			orginialAddress.setCountry(addressDto.getCountry());
		}
		if(addressDto.getPincode()!=null)
		{
			orginialAddress.setPincode(addressDto.getPincode());
		}
		Address updateAddress=addressRespository.save(orginialAddress);
		AddressDto updatedAddressDto=new AddressDto();
		
		updatedAddressDto.setId(updateAddress.getId());
		updatedAddressDto.setStreet(updateAddress.getStreet());
		updatedAddressDto.setCity(updateAddress.getCity());
		updatedAddressDto.setState(updateAddress.getState());
		updatedAddressDto.setCountry(updateAddress.getCountry());
		updatedAddressDto.setPincode(updateAddress.getPincode());
		
		return updatedAddressDto;
	}

	@Override
	public String deleteAddress(int id) {
		Optional<Address> optAddress=addressRespository.findById(id);
		if(!optAddress.isPresent())
		{
			throw new AddressNotFoundException("Address is not found "+id);
		}
		addressRespository.deleteById(id);
		return "Address with id "+id+" is successful Deleted";
	}

}
