package com.example.demo.repository;

import com.example.demo.entity.BookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRoomRepository  extends JpaRepository<BookingRoom, Long> {

    @Query(value="select * from booking_room where \n" +
            "            ((DATE(end) BETWEEN ? AND ?) or \n" +
            "            (DATE(start) between  ? AND ? )) and (status = \"waitting\" or status = \"accepted\")", nativeQuery=true)
    List<BookingRoom> findRoomByDateBooking(LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);


    @Query(value = "select * from booking_room where \n" +
            "             room_id = ? and\n" +
            "            ((DATE(end) BETWEEN ? AND ? ) or \n" +
            "            (DATE(start) between  ? AND ? )) and (status = \"waitting\" or status = \"accepted\")" , nativeQuery = true)
    List<BookingRoom> findBookingOfRoomInPeriodOfTime(Long room_id, LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);
}
