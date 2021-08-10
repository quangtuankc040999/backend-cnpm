package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Localization {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String country;
	
	private String city;

	private String wards;
	
	private String street;

	@JsonBackReference
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "hotelId", nullable = false)
	private Hotel hotel;

	public Localization() {	}

	public Localization(String country, String city, String wards, String street) {
		this.country = country;
		this.city = city;
		this.street = street;
		this.wards = wards;

	}

	//-------------------------

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getWards() {
		return wards;
	}

	public void setWards(String wards) {
		this.wards = wards;
	}
}
