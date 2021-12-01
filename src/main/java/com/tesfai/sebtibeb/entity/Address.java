package com.tesfai.sebtibeb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS_TBL")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer addressId;
	
	@NotNull(message = "Street must not be null")
	private String street;
	
	private String apt;
	
	@NotNull(message = "City must not be null")
	private String city;
	
	@NotNull(message = "State must not be null")
	private String state;
	
	@NotNull(message = "Country must not be null")
	private String country;
	
	@NotNull(message = "Zipcode must not be null")
	private Integer zipcode;
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s, %d, %s",street,city, state,zipcode,country);
	}
}
