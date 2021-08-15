package com.example.demo.payload.reponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface  BookingResponse {
    Long getIdBooking();
    String getHost();
    String getHotelName();
    String getRoomName();
    LocalDate getStart();
    LocalDate getEnd();
    LocalDateTime getTimeBook();
    Long getTotal();
    String getStatus();
}
