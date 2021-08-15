package com.example.demo.repository;

import com.example.demo.entity.BookingRoom;
import com.example.demo.payload.reponse.BookingResponse;
import com.example.demo.payload.reponse.InfoNotifyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRoomRepository  extends JpaRepository<BookingRoom, Long> {

    //===================================================================for seach room============================================
    @Query(value="select * from booking_room where \n" +
            "            (end BETWEEN ? AND ?) or \n" +
            "            (start between  ? AND ? ) and (status = \"using\" or status = \"accepted\" or status = \"waitting\")", nativeQuery=true)
    List<BookingRoom> findRoomByDateBooking(LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);

    // ==========================================for booking room=======================================================
    @Query(value = "select * from booking_room where \n" +
            "             room_id = ? and\n" +
            "            (end BETWEEN ? AND ? ) or \n" +
            "            (start between  ? AND ? ) and (status = \"using\" or status = \"accepted\" or status = \"waitting\")" , nativeQuery = true)
    List<BookingRoom> findBookingOfRoomInPeriodOfTime(Long room_id, LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);


    // Get all booking waitting
    @Query(value = "select booking_room.id as idBooking,status, user_detail.name_user_detail as host, room.name as roomName , hotel.name as hotelName, start, end, time_book as timeBook, (datediff(end,start) +1 )*room.price as total\n" +
            "from booking_room \n" +
            "join room on room.id = booking_room.room_id\n" +
            "join hotel on hotel.id = room.hotel_id\n" +
            "join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "where booking_room.status = \"waitting\" and hotel.h_owner_id = ? order by time_book desc", nativeQuery = true)
    List<BookingResponse> getAllBookingWaitting(Long directorId);

    @Query(value = "SELECT * FROM booking_room where id =? ", nativeQuery = true)
    BookingRoom getOneById(Long bookingId);


    // ======================= for notification =======================
    @Query(value = "select end, start, host_id as forUser, booking_room.id as idBooking, hotel.name as hotel, room.name as room  from booking_room join room on booking_room.room_id = room.id join hotel on room.hotel_id = hotel.id where booking_room.id = ?", nativeQuery = true)
    InfoNotifyResponse getInforBookingByBoookingId (Long bookingId);

    // ==================== cancel booking ==============================


    // ======================= nhận phòng ==================================
    // lấy ra tất cả booking  đã được xác nhận và nhận vào ngày hôm nay của khách sạn
    @Query(value = "select booking_room.id as idBooking, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status  from booking_room \n" +
            "join room on booking_room.room_id = room.id\n" +
            "join hotel on hotel.id = room.hotel_id\n" +
            "join user_detail on booking_room.host_id = user_detail.user_id\n" +
            " where status = \"accepted\" and start =  CURDATE()  and hotel_id = ?  ", nativeQuery = true)
    List<BookingResponse> getRoomStartNowAndAccepted (Long hotelId);

    // ========================= trả phòng ==================================
    @Query(value = "select booking_room.id as idBooking, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status  from booking_room \n" +
            "join room on booking_room.room_id = room.id\n" +
            "join hotel on hotel.id = room.hotel_id\n" +
            "join user_detail on booking_room.host_id = user_detail.user_id\n" +
            " where status = \"using\" and end =  CURDATE()  and hotel_id = ?  ", nativeQuery = true)
    List<BookingResponse> getRoomForCheckOut (Long hotelId);


    /*
    *
    * CANCEL BOOKING
    * */
    @Query(value = "select * from booking_room where id = ?", nativeQuery = true)
    BookingRoom getBookingRoomById(Long bookingId);

    /*
    *
    * THỐNG KÊ CỦA USER
    *
    * */

    // ===============================  WAITTING =======================
    @Query(value = " select booking_room.id as idBooking,hotel.name as hotelName, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"waitting\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingWaittingByUserId(Long userId);

    // ================================= UNACCEPTED ===========================
    @Query(value = " select booking_room.id as idBooking,hotel.name as hotelName, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"unaccepted\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingUnacceptedByUserId(Long userId);

    // =================================== ACCEPTED ===============================
    @Query(value = " select booking_room.id as idBooking,hotel.name as hotelName, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"accepted\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingAcceptedByUserId(Long userId);

    // ================================== COMPLETE ====================================
    @Query(value = " select is_comment as isComment, booking_room.id as idBooking,hotel.name as hotelName, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total  \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"complete\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingCompleteByUserId(Long userId);



}

