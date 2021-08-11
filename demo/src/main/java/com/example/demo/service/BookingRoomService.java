package com.example.demo.service;

import com.example.demo.entity.BookingRoom;
import com.example.demo.entity.User;
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
        bookingRoomRepository.save(bookingRoom);
    }


    public List<BookingRoom> getAllRoomByDateBooking(LocalDate start, LocalDate end) {
        return bookingRoomRepository.findRoomByDateBooking(start, end, start, end);
    }

    public List<BookingRoom> findBookingOfRoomInPeriodOfTime(Long room_id, LocalDate start, LocalDate end) {
        return bookingRoomRepository.findBookingOfRoomInPeriodOfTime(room_id,start, end, start, end);
    }

}
