package com.example.demo.payload.request;

import java.time.LocalDate;

public class BookingRequest {

    private Long idRoom;
    private LocalDate start;
    private LocalDate end;

    public BookingRequest() {
    }

    public BookingRequest(Long idRoom, LocalDate start, LocalDate end) {
        this.idRoom = idRoom;
        this.start = start;
        this.end = end;
    }



    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
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
}
