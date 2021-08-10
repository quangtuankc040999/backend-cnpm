package com.example.demo.payload.request;

import java.time.LocalDate;

public class UpdateInformationRequest {
    private String nameUserDetail;
    private String phoneNumber;
    private LocalDate birth;

    public UpdateInformationRequest(String nameUserDetail, String phoneNumber, LocalDate birth) {
        this.nameUserDetail = nameUserDetail;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
    }

    public UpdateInformationRequest() {
    }

    public String getNameUserDetail() {
        return nameUserDetail;
    }

    public void setNameUserDetail(String nameUserDetail) {
        this.nameUserDetail = nameUserDetail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
