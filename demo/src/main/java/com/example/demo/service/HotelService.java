package com.example.demo.service;

import com.example.demo.entity.Hotel;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public void saveHotel(Hotel hotel){hotelRepository.save(hotel);}
    public  Hotel  findHotelById (long id){
        return  hotelRepository.findById(id);
    }
}
