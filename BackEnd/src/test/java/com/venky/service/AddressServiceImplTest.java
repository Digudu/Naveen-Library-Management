package com.venky.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.venky.dto.AddressDto;
import com.venky.entity.Address;
import com.venky.repository.AddressRepository;
@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {
	
	@Mock
	AddressRepository addressRepository;
	
	@InjectMocks
	AddressServiceImpl addressService;

	@Test
	void testGetAllAddress() {
		
		List<Address> address=List.of(new Address(12,"Church Street","Banglore","Karnataka","India","534102"),
				new Address(17,"T Nagar","Chennai","Tamil Nadu","India","600001")
				);
		when(addressRepository.findAll()).thenReturn(address);
		
		List<AddressDto> addressDto=addressService.getAllAddress();
		
		assertAll(
				
				()->assertEquals(addressDto.size(),2),
				()->assertEquals(addressDto.get(1).getCity(),"Chennai")
				);
	}

	@Test
	void testGetAddressById() {
		Optional<Address> address=Optional.of(new Address(17,"T Nagar","Chennai","Tamil Nadu","India","600001"));
		when(addressRepository.findById(17)).thenReturn(address);
		AddressDto addressDto=addressService.getAddressById(17);
		
		assertAll(
				()->assertEquals(addressDto.getId(),17),
				()->assertEquals(addressDto.getPincode(),"600001")
				);	
	}

	@Test
	void testAddAddress() {
		
		Address address=new Address(13,"Kormagala","Banglore","Karanataka","India","535001");
		
		AddressDto addressDto=new AddressDto(13,"Kormagala","Banglore","Karanataka","India","535001");
		
		
		when(addressRepository.save(address)).thenReturn(address);
		
		AddressDto createdAddress=addressService.addAddress(addressDto);
		
		assertAll(
		
				()->assertEquals(addressDto,createdAddress),
				()->assertEquals(createdAddress.getCountry(),"India"));
	}

	@Test
	void testUpdateAddress() {
		Address address=new Address(17,"T Nagar","Chennai","Tamil Nadu","India","600001");
		AddressDto updateAddress=new AddressDto(0,"Adyar",null,null,null,"600200");
		when(addressRepository.findById(17)).thenReturn(Optional.of(address));
		when(addressRepository.save(any(Address.class))).thenReturn(address);
		AddressDto updatedAddress=addressService.updateAddress(17, updateAddress);
		
		assertAll(
				()->assertEquals(updatedAddress.getStreet(),"Adyar"),
				()->assertEquals(updatedAddress.getPincode(),"600200"),
				()->assertEquals(updatedAddress.getCountry(),"India")
				);	
	}
		
		
		
	@Test
	void testDeleteAddress() {
		Address address=new Address(12,"Church Street","Banglore","Karnataka","India","534102");
		when(addressRepository.findById(12)).thenReturn(Optional.of(address));
		addressService.deleteAddress(12);
		verify(addressRepository,times(1)).deleteById(12);
	}

}
