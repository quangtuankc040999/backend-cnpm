package com.example.demo.repository;

import com.example.demo.entity.BookingRoom;
import com.example.demo.payload.reponse.BookingResponse;
import com.example.demo.payload.reponse.InfoNotifyResponse;
import com.example.demo.payload.reponse.ThongKeDoanhThuDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRoomRepository  extends JpaRepository<BookingRoom, Long> {

    //===================================================================for seach room============================================
    @Query(value="select * from booking_room where \n" +
            "            ((end BETWEEN ? AND ?) or \n" +
            "            (start between  ? AND ? )) and (status = \"using\" or status = \"accepted\" or status = \"waitting\")", nativeQuery=true)
    List<BookingRoom> findRoomByDateBooking(LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);

    // ==========================================for booking room=======================================================
    @Query(value = "select * from booking_room where \n" +
            "             room_id = ? and\n" +
            "            ((end BETWEEN ? AND ? ) or \n" +
            "            (start between  ? AND ? )) and (status = \"using\" or status = \"accepted\" or status = \"waitting\")" , nativeQuery = true)
    List<BookingRoom> findBookingOfRoomInPeriodOfTime(Long room_id, LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);


    // Get all booking waitting
    @Query(value = "select is_comment as isComment, booking_room.id as idBooking,hotel.id as hotelId, status, user_detail.name_user_detail as host, room.name as roomName , hotel.name as hotelName,room.id as roomId, start, end, time_book as timeBook, (datediff(end,start) +1 )*room.price as total\n" +
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
    @Query(value = "select is_comment as isComment, booking_room.id as idBooking,hotel.id as hotelId, user_detail.name_user_detail as host, room.name as roomName ,room.id as roomId, start, end, time_book as timeBook , status  from booking_room \n" +
            "join room on booking_room.room_id = room.id\n" +
            "join hotel on hotel.id = room.hotel_id\n" +
            "join user_detail on booking_room.host_id = user_detail.user_id\n" +
            " where status = \"accepted\" and start =  CURDATE()  and hotel_id = ?  ", nativeQuery = true)
    List<BookingResponse> getRoomStartNowAndAccepted (Long hotelId);

    // ========================= trả phòng ==================================
    @Query(value = "select is_comment as isComment, booking_room.id as idBooking,hotel.id as hotelId, user_detail.name_user_detail as host, room.name as roomName ,room.id as roomId, start, end, time_book as timeBook , status  from booking_room \n" +
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
    @Query(value = " select is_comment as isComment, booking_room.id as idBooking,hotel.name as hotelName,room.id as roomId, user_detail.name_user_detail as host,hotel.id as hotelId, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"waitting\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingWaittingByUserId(Long userId);

    // ================================= UNACCEPTED ===========================
    @Query(value = " select is_comment as isComment, booking_room.id as idBooking,hotel.name as hotelName,hotel.id as hotelId,room.id as roomId, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"unaccepted\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingUnacceptedByUserId(Long userId);

    // =================================== ACCEPTED ===============================
    @Query(value = " select is_comment as isComment, booking_room.id as idBooking,hotel.name as hotelName,hotel.id as hotelId,room.id as roomId, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"accepted\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingAcceptedByUserId(Long userId);

    // ================================== COMPLETE ====================================
    @Query(value = " select is_comment as isComment, booking_room.id as idBooking,hotel.name as hotelName,hotel.id as hotelId,room.id as roomId, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total  \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"complete\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingCompleteByUserId(Long userId);

    // ===================================== CANCEL ============================
    @Query(value = " select is_comment as isComment, booking_room.id as idBooking,hotel.name as hotelName,hotel.id as hotelId, room.id as roomId, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total  \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where status = \"canceled\" and host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingCancelByUserId(Long userId);
    // ========================== ALL ==============================
    @Query(value = " select is_comment as isComment, booking_room.id as idBooking,hotel.name as hotelName, hotel.id as hotelId, room.id as roomId, user_detail.name_user_detail as host, room.name as roomName , start, end, time_book as timeBook , status, (datediff(end,start) +1 )*room.price as total  \n" +
            "            from booking_room \n" +
            "            join room on booking_room.room_id = room.id\n" +
            "            join hotel on hotel.id = room.hotel_id\n" +
            "            join user_detail on booking_room.host_id = user_detail.user_id\n" +
            "            where  host_id = ? ", nativeQuery = true)
    List<BookingResponse> getBookingByUserId(Long userId);


    /*
    * THỐNG KÊ DIRECTORS
    *
    * */

    // ==============================Thống kê chung============================
    //=================================Thống kê tất cả số đơn đặt hàng mới trong ngày của director ========================
    @Query(value = "SELECT  count(booking_room.id) as BookingInDay FROM booking_room \n" +
            "join room on room.id = booking_room.room_id \n" +
            "join hotel on room.hotel_id = hotel.id\n" +
            "where (date(time_book) = date(curdate()) and month(time_book) = month(curdate()) and year(time_book) = year(curdate())) and hotel.h_owner_id = ?\n" +
            "group by hotel.h_owner_id;", nativeQuery = true)
    Long soDonDatPhongMoiTrongNgay(Long directorId);

    // ====================== Thống kê số lượng tất cả  đơn đặt hàng trong tháng =====================================
    @Query(value = "SELECT  count(booking_room.id) as totalBookingInMonth FROM booking_room \n" +
            "join room on room.id = booking_room.room_id \n" +
            "join hotel on room.hotel_id = hotel.id\n" +
            "where (month(start) = month(now()) and year(start) = year(now())) and hotel.h_owner_id = ?\n " +
            "group by hotel.h_owner_id;",nativeQuery = true)
    Long soDonDatPhongTrongThang (Long  directorId);

    // ==================== Thong ke tong doanh thu trong tháng ========================================
    @Query(value = "SELECT  sum((datediff(end,start)+1) * price ) as totalSalesInMonth FROM booking_room \n" +
            "join room on room.id = booking_room.room_id \n" +
            "join hotel on room.hotel_id = hotel.id\n" +
            "where (month(start) = month(now()) and year(start) = year(now()))  and (status = \"using\" or status = \"accepted\" or status = \"complete\") and hotel.h_owner_id = ?\n" +
            "group by hotel.h_owner_id", nativeQuery = true)
    Long tongDoanhThuTrongThang (Long directorId);

    // ==============================Thống kê từng khách sạn ============================
    //=================================Thống kê tất cả số đơn đặt hàng mới trong ngày của khách sạn ========================
    @Query(value = "SELECT  count(booking_room.id) as BookingInDay FROM booking_room \n" +
            "join room on room.id = booking_room.room_id \n" +
            "join hotel on room.hotel_id = hotel.id\n" +
            "where (date(time_book) = date(curdate()) and month(time_book) = month(curdate()) and year(time_book) = year(curdate())) and hotel.h_owner_id = ? and hotel.id = ? \n" +
            "group by hotel.id;", nativeQuery = true)
    Long soDonDatPhongMoiTrongNgayKS(Long directorId, Long hotelId);

    // ====================== Thống kê số lượng tất cả  đơn đặt hàng trong tháng của khách sạn =====================================
    @Query(value = "SELECT  count(booking_room.id) as totalBookingInMonth FROM booking_room \n" +
            "join room on room.id = booking_room.room_id \n" +
            "join hotel on room.hotel_id = hotel.id\n" +
            "where (month(start) = month(now()) and year(start) = year(now())) and hotel.h_owner_id = ? and hotel.id = ? \n " +
            "group by hotel.id;",nativeQuery = true)
    Long soDonDatPhongTrongThangKS (Long  directorId, Long hotelId);

    // ==================== Thong ke tong doanh thu trong tháng của khách sạn ========================================
    @Query(value = "SELECT  sum((datediff(end,start)+1) * price ) as totalSalesInMonth FROM booking_room \n" +
            "join room on room.id = booking_room.room_id \n" +
            "join hotel on room.hotel_id = hotel.id\n" +
            "where (month(start) = month(now()) and year(start) = year(now()))  and (status = \"using\" or status = \"accepted\" or status = \"complete\") and hotel.h_owner_id = ? and hotel.id = ? \n" +
            "group by hotel.id", nativeQuery = true)
    Long tongDoanhThuTrongThangKS (Long directorId, Long hotelId);

    // ======================== THỐNG KÊ DOANH THU ĐỂ VEX BIỂU ĐỒ ===========================================
    @Query(value = "select month(start) as month, hotel.name as hotelName, province, sum( (datediff(end,start)+1)*room.price) as totalInMonth, count(booking_room.id) as numberBookingInMonth\n" +
            "from booking_room \n" +
            "join room on booking_room.room_id = room.id\n" +
            "join hotel on room.hotel_id = hotel.id\n" +
            "join localization on hotel.id = localization.hotel_id\n" +
            "where hotel.id = ? and hotel.h_owner_id = ? and year(start) = ? and (status = \"using\" or status = \"accepted\" or status = \"complete\")\n" +
            "group by month(start)\n" +
            "ORDER BY month DESC;", nativeQuery = true)
    List<ThongKeDoanhThuDirector> thongKeDoanhThuDeVeBieuDo(Long hotelId, Long directorId, int year);
}

