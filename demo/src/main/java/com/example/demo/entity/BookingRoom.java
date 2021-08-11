package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class BookingRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonManagedReference(value = "booking")
	@ManyToOne
	@JoinColumn(name = "roomId")
	private Room room;

//	@Column(columnDefinition = "DATE")
	private LocalDate start;

	private LocalDate end;
	@OneToOne
	private User host;

	private String status;

	private LocalDateTime timeBook;
	//======================

	public long getId() {
		return id;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
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

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTimeBook() {
		return timeBook;
	}

	public void setTimeBook(LocalDateTime timeBook) {
		this.timeBook = timeBook;
	}
}
