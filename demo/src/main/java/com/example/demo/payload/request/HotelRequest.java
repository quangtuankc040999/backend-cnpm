package com.example.demo.payload.request;

import com.example.demo.entity.Localization;

public class HotelRequest {
    public String name;
    public float standard;
    public Localization localization;
    public String typeOfHotel;

    public HotelRequest(String name, float standard, Localization localization, String typeOfHotel) {
        this.name = name;
        this.standard = standard;
        this.localization = localization;
        this.typeOfHotel = typeOfHotel;
    }

    public HotelRequest() {
    }

    public String getTypeOfHotel() {
        return typeOfHotel;
    }

    public void setTypeOfHotel(String typeOfHotel) {
        this.typeOfHotel = typeOfHotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStandard() {
        return standard;
    }

    public void setStandard(float standard) {
        this.standard = standard;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }
}
