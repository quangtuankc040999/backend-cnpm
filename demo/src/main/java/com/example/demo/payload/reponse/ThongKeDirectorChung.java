package com.example.demo.payload.reponse;

public class ThongKeDirectorChung {
   private Long hotelId;
    private Long directorId;
    private Long bookingInDay;
   private Long totalBookingInMonth;
    private Long totalSalesInMonth;

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    public Long getBookingInDay() {
        return bookingInDay;
    }

    public void setBookingInDay(Long bookingInDay) {
        this.bookingInDay = bookingInDay;
    }

    public Long getTotalBookingInMonth() {
        return totalBookingInMonth;
    }

    public void setTotalBookingInMonth(Long totalBookingInMonth) {
        this.totalBookingInMonth = totalBookingInMonth;
    }

    public Long getTotalSalesInMonth() {
        return totalSalesInMonth;
    }

    public void setTotalSalesInMonth(Long totalSalesInMonth) {
        this.totalSalesInMonth = totalSalesInMonth;
    }
}
