package com.example.demo.service;

import com.example.demo.entity.BookingRoom;
import com.example.demo.entity.User;
import com.example.demo.payload.reponse.BookingResponse;
import com.example.demo.payload.reponse.InfoNotifyResponse;
import com.example.demo.repository.BookingRoomRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingRoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    BookingRoomRepository bookingRoomRepository;


    public void bookRoom(LocalDate from, LocalDate to, long id, User user) {
        BookingRoom bookingRoom = new BookingRoom();
        bookingRoom.setStart(from);
        bookingRoom.setEnd(to);
        bookingRoom.setRoom(roomRepository.getOne(id));
        bookingRoom.setHost(user);
        bookingRoom.setTimeBook(LocalDateTime.now());
        bookingRoom.setStatus(new String("waitting"));
        bookingRoom.setComment(false);
        bookingRoomRepository.save(bookingRoom);
    }


    public List<BookingRoom> getAllRoomByDateBooking(LocalDate start, LocalDate end) {
        return bookingRoomRepository.findRoomByDateBooking(start, end, start, end);
    }

    public List<BookingRoom> findBookingOfRoomInPeriodOfTime(Long room_id, LocalDate start, LocalDate end) {
        return bookingRoomRepository.findBookingOfRoomInPeriodOfTime(room_id,start, end, start, end);
    }

    public List<BookingResponse> getAllBookingWaitting(Long directorId){
        return bookingRoomRepository.getAllBookingWaitting(directorId);
    }

    public  void accepetedBooking(Long bookingId){
        BookingRoom bookingRoom = bookingRoomRepository.getOneById(bookingId);
        bookingRoom.setStatus("accepted");
        bookingRoomRepository.save(bookingRoom);
    }

    public  void unaccepetedBooking(Long bookingId){
        BookingRoom bookingRoom = bookingRoomRepository.getOneById(bookingId);
        bookingRoom.setStatus("unaccepted");
        bookingRoomRepository.save(bookingRoom);
    }

    // ======================= noti ======================
    public InfoNotifyResponse getInfoBooking (Long bookingId){
        return  bookingRoomRepository.getInforBookingByBoookingId(bookingId);
    }

    // ====================== cancel booking =============================


}
