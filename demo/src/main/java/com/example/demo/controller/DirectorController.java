package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.payload.reponse.MessageResponse;
import com.example.demo.payload.request.HotelRequest;
import com.example.demo.security.jwt.GetUserFromToken;
import com.example.demo.service.HotelService;
import com.example.demo.service.ImageService;
import com.example.demo.service.LocalizationService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/director")
public class DirectorController {

    @Autowired
    private GetUserFromToken getUserFromToken;
    @Autowired
    private ImageService imageService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private LocalizationService localizationService;
    /*
     *  API FOR HOTEL
     * */
    // API thêm khách sạn
    @PostMapping(value = "/hotel/new-hotel", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addHotell(@RequestParam("hotelRequest") String jsonHotel, @RequestParam(name = "images") List<String> images, @RequestHeader("Authorization") String token){
        try{
            String newToken = token.substring(7);
            User hOwner = getUserFromToken.getUserByUserNameFromJwt(newToken);

            if(hOwner.isActive()){
                Gson gson = new Gson();
                HotelRequest hotelRequest = gson.fromJson(jsonHotel, HotelRequest.class) ;
                Hotel hotel = new Hotel();
                for(String image : images){
                    imageService.save(new Image(image, hotel)); // ảnh khách sạn
                }
                hotel.setActive(false);// được active hay chưa
                hotel.sethOwner(hOwner); // chủ ks
                hotel.setName(hotelRequest.getName()); // tên khách sạn
                hotel.setStandard(hotelRequest.getStandard()); // ks bao nhiêu sao
                Localization localization = new Localization();
                localization.setProvince(hotelRequest.localization.getProvince());// tỉnh thành phố
                localization.setDistricts(hotelRequest.localization.getDistricts()); // thành phố/ quận huyện
                localization.setWard(hotelRequest.localization.getWard()); // phường xã
                localization.setStreet(hotelRequest.localization.getStreet()); // địa chỉ cụ thể
                hotel.setTypeOfHotel(hotelRequest.typeOfHotel);
                hotel.setAdded(LocalDate.now());
                localization.setHotel(hotel);
                hotel.setAddress(localization);

                localizationService.saveLoacation(localization);
                hotelService.saveHotel(hotel);
            }
            else{
                return  ResponseEntity.ok(new MessageResponse("your account is not active"));
            }
            return  ResponseEntity.ok(new MessageResponse("add hotel successfully"));
            }catch (Exception e){
            return  ResponseEntity.ok(new MessageResponse("Up hotel Fail"));
        }
    }




}
