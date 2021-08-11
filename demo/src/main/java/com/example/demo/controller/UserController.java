package com.example.demo.controller;

import com.example.demo.entity.BookingRoom;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.payload.reponse.MessageResponse;
import com.example.demo.payload.request.BookingRequest;
import com.example.demo.security.jwt.GetUserFromToken;
import com.example.demo.service.BookingRoomService;
import com.example.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    GetUserFromToken getUserFromToken;
    @Autowired
    RoomService roomService;
    @Autowired
    BookingRoomService bookingRoomService;

    // User page
    @GetMapping(value = "/")
    public ResponseEntity<?> getUser(@RequestHeader(name ="Authorization") String token) {
        String newToken = token.substring(7);
        User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
        return ResponseEntity.ok().body(user);
    }

    //API booking
    @PostMapping("/booking")
    public ResponseEntity<?> bookingRoom(@RequestBody BookingRequest bookingRequest, @RequestHeader(name ="Authorization") String token) {
        try{
            long idRoom = bookingRequest.getIdRoom();
            List<BookingRoom> bookingRoomList = bookingRoomService.findBookingOfRoomInPeriodOfTime(idRoom, bookingRequest.getStart(), bookingRequest.getEnd());
            if(bookingRoomList.isEmpty()){
                LocalDate from = bookingRequest.getStart();
                LocalDate to = bookingRequest.getEnd();
                Room room = roomService.findOne(idRoom);
                User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));
                bookingRoomService.bookRoom(from, to, idRoom, user); // luu vao bang booking room
                roomService.saveRoom(room);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                return ResponseEntity.ok(new MessageResponse("Done booking"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Can't booking this room"));
            }

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}
