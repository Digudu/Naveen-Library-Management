package com.venky.controller;

import static org.hamcrest.CoreMatchers.is;
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
import com.venky.dto.AddressDto;
import com.venky.service.AddressService;
@ExtendWith(MockitoExtension.class)
class AddressControllerTest {
	
	@Mock
	AddressService addressService;
	
	@InjectMocks
	AddressController addressController;
	
	MockMvc mockMvc;
	
	ObjectMapper mapper=new ObjectMapper();
	
	@BeforeEach
	public void setUp()
	{
		mockMvc=MockMvcBuilders.standaloneSetup(addressController).build();
	}

	@Test
	void testGetAllAddress() throws Exception {
		
		List<AddressDto> address=List.of(
				new AddressDto(12,"Church Street","Banglore","Karnataka","India","534102"),
				new AddressDto(13,"Kormagala","Banglore","Karanataka","India","535001"),
				new AddressDto(23,"T Nagar","Chennai","Tamil Nadu","India","600001")
				);
		when(addressService.getAllAddress()).thenReturn(address);
		
		mockMvc.perform(get("/address").accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").exists())
		.andExpect(jsonPath("$.size()").value(3));
		
	}

	@Test
	void testGetById() throws Exception {
		int id=23;
		
		AddressDto addressDto=new AddressDto(23,"Adyar","Chennai","Tamil Nadu","India","600001");
		when(addressService.getAddressById(id)).thenReturn(addressDto);
		
		mockMvc.perform(get("/address/{id}",id).accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.city").value("Chennai"));
	}

	@Test
	void testAddAddress() throws Exception {
		AddressDto addressDto=new AddressDto(13,"Kormagala","Banglore","Karanataka","India","535001");
		String addressJson=mapper.writeValueAsString(addressDto);
		
		when(addressService.addAddress(any(AddressDto.class))).thenReturn(addressDto);
		
		mockMvc.perform(post("/address").content(addressJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$").exists())
		.andExpect(jsonPath("$.street",is("Kormagala")));	
	}

	@Test
	void testUpdateAddress() throws Exception {
		int id=12;
		AddressDto originalAddress=new AddressDto(12,"Church Street","Banglore","Karnataka","India","534102");
		String addressJson=mapper.writeValueAsString(originalAddress);
		when(addressService.updateAddress(anyInt(), any(AddressDto.class))).thenReturn(originalAddress);
		mockMvc.perform(put("/address/{id}",id).content(addressJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
		
	}

	@Test
	void testDeleteAddress() throws Exception {
		int id=5;
		mockMvc.perform(delete("/address/{id}",id))
		.andExpect(status().isNoContent());
	}

}
