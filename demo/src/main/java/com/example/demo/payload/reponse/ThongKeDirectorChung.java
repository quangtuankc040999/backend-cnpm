package com.example.demo.payload.reponse;

public class ThongKeDirectorChung {
   private Long hotelId;
    private Long directorId;
    private Long bookingInDay;
   private Long totalBookingInMonth;
    private Long totalSalesInMonth;
    private  Long bookingYesterday;
    private Long totalBookingLastMonth;
    private Long totalSalesLastMonth;

    public Long getBookingYesterday() {
        return bookingYesterday;
    }

    public void setBookingYesterday(Long bookingYesterday) {
        this.bookingYesterday = bookingYesterday;
    }

    public Long getTotalBookingLastMonth() {
        return totalBookingLastMonth;
    }

    public void setTotalBookingLastMonth(Long totalBookingLastMonth) {
        this.totalBookingLastMonth = totalBookingLastMonth;
    }

    public Long getTotalSalesLastMonth() {
        return totalSalesLastMonth;
    }

    public void setTotalSalesLastMonth(Long totalSalesLastMonth) {
        this.totalSalesLastMonth = totalSalesLastMonth;
    }

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
