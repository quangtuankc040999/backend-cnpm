package com.example.demo.service;

import com.example.demo.entity.BookingRoom;
import com.example.demo.entity.User;
import com.example.demo.payload.reponse.BookingResponse;
import com.example.demo.payload.reponse.InfoNotifyResponse;
import com.example.demo.payload.reponse.MessageResponse;
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

    public void save(BookingRoom bookingRoom){
        bookingRoomRepository.save(bookingRoom);
    }


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

    public  BookingRoom getOneBookingById(Long idBooking){
        return bookingRoomRepository.getOneById(idBooking);
    }



    // =============================== nhận phòng =========================
    public  List<BookingResponse> getAllBookingAcceptedStartNow (Long hotelId){
        return bookingRoomRepository.getRoomStartNowAndAccepted(hotelId);
    }
    public  void checkinBooking(Long bookingId){
        BookingRoom bookingRoom = bookingRoomRepository.getOneById(bookingId);
        bookingRoom.setStatus("using");
        bookingRoomRepository.save(bookingRoom);

    }

    // ========================== trả phòng =======================================
    public  List<BookingResponse> getAllRoomCheckOut (Long hotelId){
        return bookingRoomRepository.getRoomForCheckOut(hotelId);
    }
    public  void checkoutBooking(Long bookingId){
        BookingRoom bookingRoom = bookingRoomRepository.getOneById(bookingId);
        bookingRoom.setStatus("complete");
        bookingRoomRepository.save(bookingRoom);
    }


    /*
    *
    * THỐNG KÊ USER
    *
    * */

    public List<BookingResponse> getBookingWaittingUserId(Long userId){
        return bookingRoomRepository.getBookingWaittingByUserId(userId);
    }
    public List<BookingResponse> getBookingUnacceptedUserId(Long userId){
        return bookingRoomRepository.getBookingUnacceptedByUserId(userId);
    }

    public List<BookingResponse> getBookingAcceptedUserId(Long userId){
        return bookingRoomRepository.getBookingAcceptedByUserId(userId);
    }

    public List<BookingResponse> getBookingCompleteUserId(Long userId){
        return bookingRoomRepository.getBookingCompleteByUserId(userId);
    }

    public List<BookingResponse> getBookingCancelUserId(Long userId){
        return bookingRoomRepository.getBookingCancelByUserId(userId);
    }
    public List<BookingResponse> getBookingUserId(Long userId){
        return bookingRoomRepository.getBookingByUserId(userId);
    }


    // ======================== Comment ===============================
}
