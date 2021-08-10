package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class CancelBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonManagedReference(value = "cancelBooking")
    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    //	@Column(columnDefinition = "DATE")
    private LocalDate start;
    private LocalDate end;
    @OneToOne
    private User host;

    public CancelBooking(Room room, LocalDate start, LocalDate end, User host) {
        this.room = room;
        this.start = start;
        this.end = end;
        this.host = host;
    }

    public CancelBooking() {
    }

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

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }
}
