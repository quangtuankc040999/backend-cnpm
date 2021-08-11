package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private double area;

	private double price;

	@NotBlank
	private String type;

	private String name;


	@JsonBackReference(value = "booking")
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
	private List<BookingRoom> bookingRoom;



	@JsonBackReference(value = "room")
	@ManyToOne
	@JoinColumn(name = "hotelId")

	private Hotel hotel;

	private String description;

	private String utilities; // tiện ích chỗ ở

	@JsonManagedReference(value = "imageRoom")
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private List<Image> images;

	private LocalDateTime added;
	private LocalDateTime uppdate;



	@Column(columnDefinition = "Decimal(2,1) default 0.0")
	private double rate = 0;
	
	private int capacity;

	public Room() {
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<BookingRoom> getBookingRoom() {
		return bookingRoom;
	}

	public void setBookingRoom(List<BookingRoom> bookingRoom) {
		this.bookingRoom = bookingRoom;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public LocalDateTime getAdded() {
		return added;
	}

	public void setAdded(LocalDateTime added) {
		this.added = added;
	}

	public LocalDateTime getUppdate() {
		return uppdate;
	}

	public void setUppdate(LocalDateTime uppdate) {
		this.uppdate = uppdate;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getUtilities() {
		return utilities;
	}

	public void setUtilities(String utilities) {
		this.utilities = utilities;
	}

	public Room(long id, double area, double price, @NotBlank String type, String name, List<BookingRoom> bookingRoom,  Hotel hotel, String description, List<Image> images, LocalDateTime added, LocalDateTime uppdate, double rate, int capacity) {
		this.id = id;
		this.area = area;
		this.price = price;
		this.type = type;
		this.name = name;
		this.bookingRoom = bookingRoom;
		this.hotel = hotel;
		this.description = description;
		this.images = images;
		this.added = added;
		this.uppdate = uppdate;
		this.rate = rate;
		this.capacity = capacity;
	}

	public Room(long id, String name, Hotel hotel, String description) {
		this.id = id;
		this.name = name;
		this.hotel = hotel;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", area=" + area + ", type=" + type + ", availability="  + ", date="
				+ bookingRoom + ", hotel=" + hotel + ", host=" + ", description="
				+ description + ", image=" + images + "]";
	}

}
