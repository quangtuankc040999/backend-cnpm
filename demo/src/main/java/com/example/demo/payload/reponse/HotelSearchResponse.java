package com.example.demo.payload.reponse;

import com.example.demo.entity.Hotel;

public class HotelSearchResponse {
    private Hotel hotel;

    public HotelSearchResponse() {
    }

    public HotelSearchResponse(Hotel hotel) {
        this.hotel = hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
