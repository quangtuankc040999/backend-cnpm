package com.example.demo.payload.reponse;

import java.time.LocalDate;

public interface InfoNotifyResponse {
    public  Long getIdBooking();
    public String getHotel();
    public  String getRoom();
    public LocalDate getStart();
    public LocalDate getEnd();
    public Long  getForUser();
    public Long getHotelId();

}
