package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Localization {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String province;
	private String districts;
	private String ward;
	private String street;

	@JsonBackReference
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "hotelId", nullable = false)
	private Hotel hotel;


	public Localization() {
	}

	public Localization(long id, String province, String districts, String ward, String street, Hotel hotel) {
		this.id = id;
		this.province = province;
		this.districts = districts;
		this.ward = ward;
		this.street = street;
		this.hotel = hotel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistricts() {
		return districts;
	}

	public void setDistricts(String districts) {
		this.districts = districts;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
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
}
