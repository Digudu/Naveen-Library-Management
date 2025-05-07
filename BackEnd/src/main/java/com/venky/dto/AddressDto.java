package com.venky.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {
	private int id;
	@NotBlank(message="Street is mandatory")
	@Size(min=3,message="Street shout contains atleast 3 character")
	@Pattern(regexp="[A-Za-z\s]+", message="Street should contains only alphabets")
	private String street;
	@Pattern(regexp="[A-Za-z\s]+", message="City should contains only alphabets")
	@NotBlank(message="City is mandatory")
	@Size(min=3,message="City shout contains atleast 3 character")
	private String city;
	@Pattern(regexp="[A-Za-z\s]+", message="State should contains only alphabets")
	@NotBlank(message="State is mandatory")
	@Size(min=3,message="State shout contains atleast 3 character")
	private String state;
	@Pattern(regexp="[A-Za-z\s]+", message="Contry should contains only alphabets")
	@NotBlank(message="Country is mandatory")
	@Size(min=3,message="Contry shout contains atleast 3 character")
	private String country;
	@NotBlank(message="Pincode is mandatory")
	@Size(min=5,message="Pincode shout contains atleast 6 charater")
	@Pattern(regexp="^[0-9]{6}$",message="Pincode must be excatly 6 digit long")
	private String pincode;

}
