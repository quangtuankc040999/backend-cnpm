package com.example.demo.service;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public void saveHotel(Hotel hotel){hotelRepository.save(hotel);}

    public  Hotel  findHotelById (long id){
        return  hotelRepository.findById(id);
    }


    // Active hotel
    public List<Hotel> getHotelIsActiveFalse(){
        return hotelRepository.getHotelIsActiveFalse();
    }

    public boolean activeHotel(Long hotelId)  {
        Hotel hotel = hotelRepository.getById(hotelId);
        hotel.setActive(true);
        hotelRepository.save(hotel);
        return true;
    }

    public List<Hotel> findAllHotelByDirectorId(Long directorId) {
        return hotelRepository.getAllHotelByDirectorId(directorId);
    }


    // ======================================
    public List<Hotel> findAllHotelByProvice(String province) {
        return hotelRepository.findAllHotelByProvice(province);
    }
}
