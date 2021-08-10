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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                localization.setStreet(hotelRequest.localization.getStreet()); // địa chỉ cụ thể của khách sạn
                hotel.setTypeOfHotel(hotelRequest.typeOfHotel);
                hotel.setAdded(LocalDateTime.now());
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

    // API update hotel

    @GetMapping(value = "/hotel/{hotelId}/update")
    public ResponseEntity<?> updateHotel(@PathVariable("hotelId") Long hotelId) {
        try{
            Hotel hotel = hotelService.findHotelById(hotelId);
            if(hotel.isActive()){
                return ResponseEntity.ok().body(hotelService.findHotelById(hotelId));
            }
            else {
                return ResponseEntity.ok(new MessageResponse("Hotel not Active"));
            }
        }catch (Exception e){
            return  ResponseEntity.ok(new MessageResponse("No Hotel"));
        }
    }

    @Transactional
    @PostMapping(value = "/hotel/{hotelId}/update/save")
    public ResponseEntity<?> SaveUpdateHotel(@RequestParam("hotelRequest") String jsonHotel, @PathVariable("hotelId") Long hotelId,@RequestParam(name = "images") List<String> images ) {
        try {
            Gson gson = new Gson();
            HotelRequest hotelRequest = gson.fromJson(jsonHotel, HotelRequest.class) ;

            Hotel hotel = hotelService.findHotelById(hotelId);
            if(hotel.isActive()){
                hotel.setStandard(hotelRequest.getStandard());
                hotel.setName(hotelRequest.getName());
                Localization localization = localizationService.getLocationById(hotel.getAddress().getId());
                localization.setProvince(hotelRequest.localization.getProvince());// tỉnh thành phố
                localization.setDistricts(hotelRequest.localization.getDistricts()); // thành phố/ quận huyện
                localization.setWard(hotelRequest.localization.getWard()); // phường xã
                localization.setStreet(hotelRequest.localization.getStreet()); // địa chỉ cụ thể của khách sạn
                localization.setStreet(hotelRequest.getLocalization().getStreet());
                hotel.setAddress(localization);
                hotel.setUpdated(LocalDateTime.now());
                localizationService.saveLoacation(localization);
                hotel.setAddress(localization);
                imageService.deleteImgHotel(hotelId);
                for(String image : images){
                    imageService.save(new Image(image, hotel)); // ảnh khách sạn
                }
                hotelService.saveHotel(hotel);
                return ResponseEntity.ok().body(new MessageResponse("Save changes"));

            }else {
                return ResponseEntity.ok(new MessageResponse("Hotel not Active"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(new MessageResponse("Update Hotel fail"));
        }
    }




}
