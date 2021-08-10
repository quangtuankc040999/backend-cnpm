package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Lob
	@Column(name = "img")
	private String img;

	@JsonBackReference(value = "imageRoom")
	@ManyToOne(cascade = {CascadeType.ALL})
	private Room room;

	@JsonBackReference(value = "imageHotel")
	@ManyToOne(cascade = {CascadeType.ALL})
	private Hotel hotel;
	
	//-----------------

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Image() {
	}

	public Image(long id, String img, Room room, Hotel hotel) {
		this.id = id;
		this.img = img;
		this.room = room;
		this.hotel = hotel;
	}

	public Image(String img, Room room) {
		this.img = img;
		this.room = room;
	}

	public Image(String img, Hotel hotel) {
		this.img = img;
		this.hotel = hotel;
	}
}
