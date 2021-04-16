package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class House {
	private Apartment[] apartments = null;
	
	// set
	public void setApartments(Apartment[] apartments)
	{
		this.apartments = apartments;
	}
	
	public Apartment[] getApartments()
	{
		return apartments;
	} 
}