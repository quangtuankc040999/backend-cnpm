package com.example.demo.controller;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.payload.reponse.MessageResponse;
import com.example.demo.payload.reponse.ThongKeAdminTaiKhoan;
import com.example.demo.payload.reponse.ThongKeAdminTaiKhoan2;
import com.example.demo.service.HotelService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/admin")
public class UserManagerController {
    @Autowired
    UserService userService;
    @Autowired
    HotelService hotelService;

    /*
     * Tat ca cac director da dang ky nhung tai khoan chua active
     * */
    @GetMapping("/getDirector")
    public ResponseEntity<?> directorActiveFalse(){
        List<User> listDirector = userService.getDirectorActiveFalse();
        return ResponseEntity.ok().body(listDirector);
    }

    /*
     * API unlock tai khoan
     */
    @PutMapping("/getDirector/unlock/{directorId}")
    public ResponseEntity<?> activeAccount(@PathVariable("directorId") long directorId){
        try {
            userService.UnlockUser(directorId);
        }catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find this account on database", e);
        }
        return  ResponseEntity.ok().body(new MessageResponse("Done unlock"));
    }

    // API get toan bộ khách sạn chưa active
    @GetMapping("/hotel")
    public ResponseEntity<?> hotelActiveFalse(){
        List<Hotel> listHotel = hotelService.getHotelIsActiveFalse();
        return ResponseEntity.ok().body(listHotel);
    }

    @PutMapping("/hotel/unlock/{hotelId}")
    public ResponseEntity<?> activeHotel(@PathVariable("hotelId") long hotelId){
        try {
            hotelService.activeHotel(hotelId);
            return  ResponseEntity.ok().body(new MessageResponse("Done unlock hotel"));
        }catch (Exception e){
           return ResponseEntity.badRequest().body(new MessageResponse("Can't done active hotel"));
        }
    }

    /*
    * THỐNG KÊ ADMIN
    *
    * */
    /*
    * Thống kê số khách sạn mói trong tháng
    * Thống kê số user số director mới trong tháng
    *
    * */


    /*
    * THỐNG KÊ NGƯỜI DÙNG
    * */

    @GetMapping(value = "/statistical")
    public ResponseEntity<?> thongKeTaiKhoan (){
        try {
            List<Integer> month = new ArrayList<>();
            for(int i = 1 ; i <= 12; i++){
                month.add(i);
            }
            List<ThongKeAdminTaiKhoan> thongKeAdminTaiKhoans = new ArrayList<>();
            for(Integer i : month){
                ThongKeAdminTaiKhoan thongKeAdminTaiKhoan = new ThongKeAdminTaiKhoan();
                thongKeAdminTaiKhoan.setMonth(i);
                thongKeAdminTaiKhoan.setThongKe(userService.thongKeTaiKhoan(i));

                thongKeAdminTaiKhoans.add(thongKeAdminTaiKhoan);

            }
            return  ResponseEntity.ok().body(thongKeAdminTaiKhoans);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
