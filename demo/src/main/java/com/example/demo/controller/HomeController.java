package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.payload.reponse.HotelSearchResponse;
import com.example.demo.payload.reponse.MessageResponse;
import com.example.demo.payload.request.PasswordRequest;
import com.example.demo.payload.request.SearchRequest;
import com.example.demo.payload.request.UpdateInformationRequest;
import com.example.demo.repository.ConfirmationTokenRepository;
import com.example.demo.repository.UserDetailRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.GetUserFromToken;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class HomeController {

    @Autowired
    GetUserFromToken getUserFromToken;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserService userService;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    UserDetailRepository userDetailRepository;
    @Autowired
    BookingRoomService bookingRoomService;
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomService roomService;

    List<HotelSearchResponse> hotelSearchResponseList = new ArrayList<>();

    @PostMapping(value = "/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest searchRequest) {

        List<Hotel> hotels = hotelService.findAllHotelByProvice(searchRequest.getProvince()); // lấy tất cả hotel trong tỉnh , thành phố
        List<Room> roomList = new ArrayList<>();

        List<BookingRoom> bookingRoomList = bookingRoomService.getAllRoomByDateBooking(searchRequest.getStart(), searchRequest.getEnd());// trả về những hotel có trạng thái waiting, wait for pay, paid. ,

        for (Hotel hotel: hotels) {
            List<Room> rooms = roomService.searchRoomByCapacity(hotel.getId(), searchRequest.getCapacity());

            for (Room room: rooms) {
                roomList.add(room);
                for (BookingRoom bk: bookingRoomList) {
                    if(bk.getRoom().getId() == room.getId()) {
                        roomList.remove(room);
                        break;
                    }
                }
            }
        }
        List<Hotel> hotelList = new ArrayList<>();
        for(Room room : roomList) {
            Hotel hotel = room.getHotel();
            hotelList.add(hotel);
        }
        for (int i = 0; i < hotelList.size(); i++ ) {
            for (int j = i+1; j < hotelList.size(); j++) {
                if (hotelList.get(i).getId() == hotelList.get(j).getId()) {
                    hotelList.remove(hotelList.get(j));
                    j--;
                }
            }
        }
        hotelSearchResponseList.clear();
        for (Hotel hotel: hotelList) {
            HotelSearchResponse hotelSearchResponse = new HotelSearchResponse();
            Hotel hotel1 = hotel;

            List<Room> rooms = new ArrayList<>();
            for(Room room: roomList) {
                if (hotel.getId() == room.getHotel().getId()) {
                    rooms.add(room);
                }
            }
            hotel1.setRooms(rooms);
            hotelSearchResponse.setHotel(hotel1);
            hotelSearchResponseList.add(hotelSearchResponse);
        }
        return ResponseEntity.ok(hotelSearchResponseList);
    }
    //=======================================================================================================================

    // API update information

    @GetMapping(value = "/update-information")
    public ResponseEntity<?> updateInformation(@RequestHeader("Authorization") String token) {
        User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/update-information")
    public ResponseEntity<?> updateInformation(@RequestHeader("Authorization") String token, @RequestBody UpdateInformationRequest userRequest) {
        User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));
        UserDetail userDetail = user.getUserDetail();
        userDetail.setPhoneNumber(userRequest.getPhoneNumber());
        userDetail.setBirth(userRequest.getBirth());
        userDetail.setNameUserDetail(userRequest.getNameUserDetail());
        user.setUserDetail(userDetail);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("Update successfully"));
    }

    // API change password
    @PostMapping(value = "/update-information/save-password")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String token, @RequestBody PasswordRequest passwordRequest) {
        User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));
        if(encoder.matches(passwordRequest.getOldPassword(), user.getPassword() )) {
            user.setPassword(encoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().body(new MessageResponse("change password successfully"));
        } else {
            return ResponseEntity.ok().body(new MessageResponse("current password incorrect"));
        }
    }


    // forget password

    @PostMapping(value = "/forgot-password/{email}")
    public ResponseEntity<?> forgotUserPassword(@PathVariable("email") String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            // Create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
            // Save it
            confirmationTokenRepository.save(confirmationToken);
            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setText("Code: "+confirmationToken.getConfirmationToken());

            // Send the email
            emailSenderService.sendEmail(mailMessage);
            return ResponseEntity.ok().body(confirmationToken.getConfirmationToken());
        } else {
            return ResponseEntity.ok().body(new MessageResponse("Email does not exist"));
        }
    }

    @PostMapping(value = "/forgot-password/reset-password/{token}")
    public ResponseEntity<?> confirmPassword(@PathVariable("token") String token, @RequestParam("password") String passwordRequest) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        User user = confirmationToken.getUser();
        user.setPassword(encoder.encode(passwordRequest));

        userRepository.save(user);

        return ResponseEntity.ok().body(new MessageResponse("change password successfully"));
    }

    // Change Avatar
    @PostMapping(value = "/change-avatar")
    public ResponseEntity<?> changeAvatar(@RequestHeader("Authorization") String token,@RequestParam(name = "avatar") String avatar){
        User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));
        UserDetail userDetail = user.getUserDetail();
        try {
            userDetail.setAvatar(avatar);
            userDetailRepository.save(userDetail);
            return ResponseEntity.ok().body(new MessageResponse("Change Avatar successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(new MessageResponse("Change Avatar false"));
        }

    }


}
