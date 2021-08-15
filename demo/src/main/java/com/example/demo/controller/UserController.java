package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.payload.reponse.BookingResponse;
import com.example.demo.payload.reponse.MessageResponse;
import com.example.demo.payload.request.BookingRequest;
import com.example.demo.payload.request.CommentRequest;
import com.example.demo.security.jwt.GetUserFromToken;
import com.example.demo.service.BookingRoomService;
import com.example.demo.service.CommentService;
import com.example.demo.service.NotificationService;
import com.example.demo.service.RoomService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    CommentService commentService;
    @Autowired
   NotificationService notificationService;

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
            List<BookingRoom> bookingRoomList = bookingRoomService.findBookingOfRoomInPeriodOfTime(idRoom, bookingRequest.getStart(), bookingRequest.getEnd()); // trả về list các booking thành công hoặc đang chờ
            if(bookingRoomList.isEmpty()){
                LocalDate from = bookingRequest.getStart();
                LocalDate to = bookingRequest.getEnd();
                Room room = roomService.findOne(idRoom);
                User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));

                Notification notyUser = new Notification();
                notyUser.setForUser(user.getId());
                notyUser.setTimeNotification(LocalDateTime.now());
                notyUser.setHotelName(roomService.getHotelByRoomId(idRoom).getHotel());
                notyUser.setHotelId(roomService.getHotelByRoomId(idRoom).getHotelId());
                notyUser.setStart(from.toString());
                notyUser.setEnd(to.toString());
                notyUser.setContent("Đơn đặt hàng đang chờ xác nhận");
                notyUser.setRoomName(roomService.getHotelByRoomId(idRoom).getRoom());
                notyUser.setRead(false);
                notificationService.save(notyUser);

                Notification notyDirector = new Notification();
                notyDirector.setForUser(roomService.getHotelDirectorId(idRoom));
                notyDirector.setContent("Có 1 đơn đặt phòng đang chờ xác nhận");
                notyDirector.setTimeNotification(LocalDateTime.now());
                notyDirector.setHotelId(roomService.getHotelByRoomId(idRoom).getHotelId());
                notyDirector.setHotelName(roomService.getHotelByRoomId(idRoom).getHotel());
                notyDirector.setRoomName(roomService.getHotelByRoomId(idRoom).getRoom());
                notyDirector.setRead(false);
                notificationService.save((notyDirector));

                bookingRoomService.bookRoom(from, to, idRoom, user); // luu vao bang booking room
                roomService.saveRoom(room);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");


                return ResponseEntity.ok(new MessageResponse("Waiting accepted"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Can't booking this room"));
            }

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    // =================================== CHỨC NĂNG COMMENT ================================

    //    API chức năng comment
    @PostMapping(value ="/comment/{idBooking}/post")
    public  ResponseEntity<?> writeComment(@RequestHeader("Authorization") String token, @RequestParam("commentRequest") String jsonComment, @PathVariable("idBooking") Long idBooking){
        try {
            BookingRoom bookingRoom = bookingRoomService.getOneBookingById(idBooking);
            bookingRoom.setComment(true);
            bookingRoomService.save(bookingRoom);

            String newToken = token.substring(7);
            User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
            Gson gson = new Gson();
            CommentRequest commentRequest = gson.fromJson(jsonComment, CommentRequest.class);
            Comment comment = new Comment();
            comment.setRoom(bookingRoom.getRoom());
            comment.setMessenger(commentRequest.getMessenger());
            comment.setUserName(user.getUserDetail().getNameUserDetail());
            comment.setUserId(user.getId());
            comment.setAvatar(user.getUserDetail().getAvatar());
            comment.setTimeComment(LocalDateTime.now());
            comment.setStar(commentRequest.start);
            commentService.saveComment(comment);
            return ResponseEntity.ok(new MessageResponse("comment successfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /*
    *
    * THỐNG KÊ USER
    * */

    @GetMapping(value = "/statistical/waitting")
    public ResponseEntity<?> waittingBooking ( @RequestHeader("Authorization") String token){
        try{
            String newToken = token.substring(7);
            User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
            return ResponseEntity.ok().body(bookingRoomService.getBookingWaittingUserId(user.getId()));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/statistical/unaccepted")
    public ResponseEntity<?> unacceptedBooking ( @RequestHeader("Authorization") String token){
        try{
            String newToken = token.substring(7);
            User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
            return ResponseEntity.ok().body(bookingRoomService.getBookingUnacceptedUserId(user.getId()));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/statistical/accepted")
    public ResponseEntity<?> acceptedBooking ( @RequestHeader("Authorization") String token){
        try{
            String newToken = token.substring(7);
            User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
            return ResponseEntity.ok().body(bookingRoomService.getBookingAcceptedUserId(user.getId()));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/statistical/complete")
    public ResponseEntity<?> completeBooking ( @RequestHeader("Authorization") String token){
        try{
            String newToken = token.substring(7);
            User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
            return ResponseEntity.ok().body(bookingRoomService.getBookingCompleteUserId(user.getId()));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /*
    * CHỨC NĂNG HUỶ ĐẶT PHÒNG
    * */
    @PutMapping(value = "/cancel/{idBooking}")
    public ResponseEntity<?> cancelBooking (@RequestHeader("Authorization") String token, @PathVariable("idBooking") Long idBooking){

       try {
           BookingRoom bookingRoom = bookingRoomService.getOneBookingById(idBooking);
           if (bookingRoom.getStatus().equalsIgnoreCase("waitting")){
               bookingRoom.setStatus("canceled");
               bookingRoomService.save(bookingRoom);
               return ResponseEntity.ok().body(new MessageResponse(" Cancel booking successfuly"));
           }
           else if( bookingRoom.getStatus().equalsIgnoreCase("accepted")){
               if(bookingRoom.getStart().compareTo(LocalDate.now()) > 3) {
                   bookingRoom.setStatus("canceled");
                   bookingRoomService.save(bookingRoom);
                   return ResponseEntity.ok().body(new MessageResponse(" Cancel booking successfuly"));
               }else {
                   return ResponseEntity.badRequest().body(new MessageResponse("Bạn chỉ có thể huỷ đặt phòng trước ngày nhận phòng tối đa 3 ngày"));
               }
           }else {
               return ResponseEntity.badRequest().body(new MessageResponse("Booking status is can't cancel "));
           }
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

}
