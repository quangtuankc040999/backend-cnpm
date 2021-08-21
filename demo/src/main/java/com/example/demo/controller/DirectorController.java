package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.payload.reponse.*;
import com.example.demo.payload.request.HotelRequest;
import com.example.demo.payload.request.RoomRequest;
import com.example.demo.security.jwt.GetUserFromToken;
import com.example.demo.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/director")
public class DirectorController {

    @Autowired
    private GetUserFromToken getUserFromToken;
    @Autowired
    private ImageService imageService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private LocalizationService localizationService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BookingRoomService bookingRoomService;
    @Autowired
    private  NotificationService notificationService;
        /*
     *  API FOR HOTEL
     * */
    // API thêm khách sạn
    @PostMapping(value = "/hotel/new-hotel", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addHotell(@RequestParam("hotelRequest") String jsonHotel, @RequestParam(name = "images") List<String> images, @RequestHeader("Authorization") String token){
        try{
            String newToken = token.substring(7);
            User hOwner = getUserFromToken.getUserByUserNameFromJwt(newToken);

            if(hOwner.isActive()){
                Gson gson = new Gson();
                HotelRequest hotelRequest = gson.fromJson(jsonHotel, HotelRequest.class) ;
                Hotel hotel = new Hotel();
                for(String image : images){
                    imageService.save(new Image(image, hotel)); // ảnh khách sạn
                }
                hotel.setActive(false);// được active hay chưa
                hotel.sethOwner(hOwner); // chủ ks
                hotel.setName(hotelRequest.getName()); // tên khách sạn
                hotel.setStandard(hotelRequest.getStandard()); // ks bao nhiêu sao
                Localization localization = new Localization();
                localization.setProvince(hotelRequest.localization.getProvince());// tỉnh thành phố
                localization.setDistricts(hotelRequest.localization.getDistricts()); // thành phố/ quận huyện
                localization.setWard(hotelRequest.localization.getWard()); // phường xã
                localization.setStreet(hotelRequest.localization.getStreet()); // địa chỉ cụ thể của khách sạn
                hotel.setTypeOfHotel(hotelRequest.typeOfHotel);
                hotel.setAdded(LocalDateTime.now());
                localization.setHotel(hotel);
                hotel.setLocalization(localization);

                localizationService.saveLoacation(localization);
                hotelService.saveHotel(hotel);
            }
            else{
                return  ResponseEntity.ok(new MessageResponse("your account is not active"));
            }
            return  ResponseEntity.ok(new MessageResponse("add hotel successfully"));
            }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API update hotel

    @GetMapping(value = "/hotel/{hotelId}/update")
    public ResponseEntity<?> updateHotel(@PathVariable("hotelId") Long hotelId) {
        try{
            Hotel hotel = hotelService.findHotelById(hotelId);
            if(hotel.isActive()){
                return ResponseEntity.ok().body(hotelService.findHotelById(hotelId));
            }
            else {
                return ResponseEntity.badRequest().body(new MessageResponse("Hotel not active"));
            }
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(new MessageResponse("Not found hotel"));
        }
    }

    @Transactional
    @PostMapping(value = "/hotel/{hotelId}/update")
    public ResponseEntity<?> SaveUpdateHotel(@RequestParam("hotelRequest") String jsonHotel, @PathVariable("hotelId") Long hotelId,@RequestParam(name = "images") List<String> images ) {
        try {
            Gson gson = new Gson();
            HotelRequest hotelRequest = gson.fromJson(jsonHotel, HotelRequest.class) ;

            Hotel hotel = hotelService.findHotelById(hotelId);
            if(hotel.isActive()){
                hotel.setStandard(hotelRequest.getStandard());
                hotel.setName(hotelRequest.getName());
                Localization localization = localizationService.getLocationById(hotel.getLocalization().getId());
                localization.setProvince(hotelRequest.localization.getProvince());// tỉnh thành phố
                localization.setDistricts(hotelRequest.localization.getDistricts()); // thành phố/ quận huyện
                localization.setWard(hotelRequest.localization.getWard()); // phường xã
                localization.setStreet(hotelRequest.localization.getStreet()); // địa chỉ cụ thể của khách sạn
                localization.setStreet(hotelRequest.getLocalization().getStreet());
                hotel.setLocalization(localization);
                hotel.setUpdated(LocalDateTime.now());
                localizationService.saveLoacation(localization);
                hotel.setLocalization(localization);
                imageService.deleteImgHotel(hotelId);
                for(String image : images){
                    imageService.save(new Image(image, hotel)); // ảnh khách sạn
                }
                hotelService.saveHotel(hotel);
                return ResponseEntity.ok().body(new MessageResponse("Save changes"));

            }else {
                return ResponseEntity.badRequest().body(new MessageResponse("Hotel not active"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Update hotel fail"));
        }
    }
    // ================= delete hotel =======================
    @PostMapping("/hotel/delete/{hotelId}")
    public ResponseEntity<?> deleteHotel( @RequestHeader("Authorization") String token, @PathVariable("hotelId")Long hotelId){
        try {
            Hotel hotel = hotelService.findHotelById(hotelId);
            hotel.setDelete(true);
            hotelService.saveHotel(hotel);
            return ResponseEntity.ok().body(new MessageResponse("Delete hotel successfully "));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API FOR ROOM
    // API thêm phòng
    @PostMapping("/hotel/{hotelId}/new-room")
    public ResponseEntity<?> addRoom(@PathVariable("hotelId") Long hotelId,@RequestParam(name = "images") List<String> images, @RequestParam("roomRequest") String jsonRoom) {
        try {
            Hotel hotel = hotelService.findHotelById(hotelId);
            if(hotel.isActive()){
                Gson gson = new Gson();
                RoomRequest roomRequest = gson.fromJson(jsonRoom, RoomRequest.class);
                Room room = new Room();

                for(String image : images){
                    imageService.save(new Image(image, room)); // ảnh phòng
                }
                room.setHotel(hotel);
                room.setType(roomRequest.getType());
                room.setArea(roomRequest.getArea());
                room.setCapacity(roomRequest.getCapacity());
                room.setDescription(roomRequest.getDescription());
                room.setName(roomRequest.getName());
                room.setPrice(roomRequest.getPrice());
                room.setAdded(LocalDateTime.now());
                room.setUtilities(roomRequest.getUtilities());
                roomService.saveRoom(room);
                return ResponseEntity.ok().body(new MessageResponse("Add room successfully"));

            }else {
                return ResponseEntity.badRequest().body(new MessageResponse("Hotel not active"));
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Add room false"));
        }
    }

    // API update room
    @GetMapping(value = "/hotel/{hotelId}/{roomId}/update")
    public ResponseEntity<?> updateRoom(@PathVariable("roomId") Long roomId, @PathVariable("hotelId") Long hotelId) {
        return ResponseEntity.ok().body(roomService.getRoomById(roomId, hotelId));
    }
    @Transactional
    @PostMapping(value = "/hotel//update")
    public ResponseEntity<?> SaveUpdateRoom(@RequestParam("roomRequest") String jsonRoom, @PathVariable("hotelId") Long hotelId,@PathVariable("roomId") Long roomId, @RequestParam(required = false, name = "images") List<String> images ) {
        try {
            Gson gson = new Gson();
            RoomRequest roomRequest = gson.fromJson(jsonRoom, RoomRequest.class) ;

            Room room = roomService.getRoomById(roomId, hotelId);
            room.setName(roomRequest.getName());
            room.setType(roomRequest.getType());
            room.setPrice(roomRequest.getPrice());
            room.setDescription(roomRequest.getDescription());
            room.setCapacity(roomRequest.getCapacity());
            room.setArea(roomRequest.getArea());
            imageService.deleteImgRoom(roomId);
            room.setUtilities(roomRequest.getUtilities());
            for(String image : images){
                imageService.save(new Image(image, room)); // ảnh phòng
            }
            room.setUppdate(LocalDateTime.now());
            roomService.saveRoom(room);

            return ResponseEntity.ok().body(new MessageResponse("Save changes"));
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/hotel/delete/{hotelId}/{roomId}")
    public ResponseEntity<?> deleteRoom( @RequestHeader("Authorization") String token, @PathVariable("hotelId")Long hotelId, @PathVariable("roomId") Long roomId){
        try {
            Room room = roomService.getRoomById(roomId, hotelId);
            room.setDelete(true);
            roomService.saveRoom(room);
            return ResponseEntity.ok().body(new MessageResponse("Delete room successfully "));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ====================================================================================================================

    // API get all hotel of director
    @GetMapping("/all-hotel")
    public ResponseEntity<?> getAllHotel(@RequestHeader("Authorization") String token){
        Long idDirector = getUserFromToken.getUserByUserNameFromJwt(token.substring(7)).getId();
        List<Hotel> hotels = hotelService.findAllHotelByDirectorId(idDirector);
        return  ResponseEntity.ok().body(hotels);
    }

    @GetMapping("/{hotelId}/all-room")
    public  ResponseEntity<?> getAllRoom(@PathVariable("hotelId") Long hotelId){
        List<Room> rooms = roomService.getAllRoomByHotelId(hotelId);
        for(Room room : rooms){
            if(commentService.getRateRoom(room.getId()) != null){
                room.setRate(commentService.getRateRoom(room.getId()).getRate());
                room.setNumberReview((commentService.getRateRoom(room.getId()).getTotal()));
                roomService.saveRoom(room);
            }
            else {
                room.setRate(0.0);
                room.setNumberReview(0);
                roomService.saveRoom(room);
            }
        }
        return  ResponseEntity.ok().body(rooms);
    }

    // ==================ACCEPT BOOKING REQUEST ===================================
    @GetMapping("/get-booking")
    public  ResponseEntity<?> getAllBookingWaitting(@RequestHeader("Authorization") String token){
        List<BookingResponse> bookingWaitting = bookingRoomService.getAllBookingWaitting(getUserFromToken.getUserByUserNameFromJwt(token.substring(7)).getId());
       return  ResponseEntity.ok().body(bookingWaitting);
    }
    @GetMapping("/get-booking/{hotelId}")
    public  ResponseEntity<?> getAllBookingWaitting(@RequestHeader("Authorization") String token,@PathVariable("hotelId") long hotelId){
        List<BookingResponse> bookingWaitting = bookingRoomService.getAllBookingWaittingOfHotel(hotelId);
        return  ResponseEntity.ok().body(bookingWaitting);
    }

    @PutMapping("/get-booking/accept/{bookingId}")
    public ResponseEntity<?> accpetBooking(@PathVariable("bookingId") long bookingId){
        try {
            bookingRoomService.accepetedBooking(bookingId);
            InfoNotifyResponse infoNotifyResponse = bookingRoomService.getInfoBooking(bookingId);
            Notification notification = new Notification();
            notification.setTimeNotification(LocalDateTime.now());
            notification.setForUser(infoNotifyResponse.getForUser());
            notification.setContent("Đơn đặt phòng đã được xác nhận. Chi tiết");
            notification.setRoomName(infoNotifyResponse.getRoom());
            notification.setHotelName(infoNotifyResponse.getHotel());
            notification.setStart(infoNotifyResponse.getStart().toString());
            notification.setEnd(infoNotifyResponse.getEnd().toString());
            notification.setRead(false);
            notificationService.save(notification);
            return ResponseEntity.ok().body(new MessageResponse("Done accept"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
    @PutMapping("/get-booking/unaccept/{bookingId}")
    public ResponseEntity<?> unaccpetBooking(@PathVariable("bookingId") long bookingId){
        try {
            bookingRoomService.unaccepetedBooking(bookingId);
            InfoNotifyResponse infoNotifyResponse = bookingRoomService.getInfoBooking(bookingId);
            Notification notification = new Notification();
            notification.setTimeNotification(LocalDateTime.now());
            notification.setForUser(infoNotifyResponse.getForUser());
            notification.setContent("Đơn đặt phòng bị từ chối. Chi tiết");
            notification.setRoomName(infoNotifyResponse.getRoom());
            notification.setHotelName(infoNotifyResponse.getHotel());
            notification.setStart(infoNotifyResponse.getStart().toString());
            notification.setEnd(infoNotifyResponse.getEnd().toString());
            notification.setRead(false);
            notificationService.save(notification);
            return ResponseEntity.ok().body(new MessageResponse("Done unaccept"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // ============================ Chức năng nhận phòng ============================================

    // api lấy toàn bộ phòng để checkin
    @GetMapping(value = "/checkin/{hotelId}")
    public  ResponseEntity<?> getAllRoomToCheckIn (@RequestHeader("Authorization") String token, @PathVariable("hotelId") Long hotelId){
        String newToken = token.substring(7);
        User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
        try {
            List<BookingResponse> allBookingCanCheckin = bookingRoomService.getAllBookingAcceptedStartNow(hotelId);
            return ResponseEntity.ok(allBookingCanCheckin);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/checkin")
    public  ResponseEntity<?> getAllRoomToCheckInAllHotel (@RequestHeader("Authorization") String token){
        String newToken = token.substring(7);
        User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
        try {
            List<BookingResponse> allBookingCanCheckin = bookingRoomService.getAllBookingAcceptedStartNowAllHotel(user.getId());
            return ResponseEntity.ok(allBookingCanCheckin);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/checkin/{bookingId}")
    public ResponseEntity<?> checkIn (@PathVariable("bookingId") long bookingId){
        try {
            bookingRoomService.checkinBooking(bookingId);
            return  ResponseEntity.ok().body(new MessageResponse("Check in sussesfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ===================================== Chức năng trả phòng ========================================

    @GetMapping(value = "/checkout")
    public  ResponseEntity<?> getAllRoomToCheckOutAll (@RequestHeader("Authorization") String token){
        String newToken = token.substring(7);
        User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
        try {
            List<BookingResponse> allBookingCanCheckOut = bookingRoomService.getAllRoomCheckOutAllHotel(user.getId());
            return ResponseEntity.ok(allBookingCanCheckOut);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(value = "/checkout/{hotelId}")
    public  ResponseEntity<?> getAllRoomToCheckOut (@RequestHeader("Authorization") String token, @PathVariable("hotelId") Long hotelId){
        String newToken = token.substring(7);
        User user = getUserFromToken.getUserByUserNameFromJwt(newToken);
        try {
            List<BookingResponse> allBookingCanCheckOut = bookingRoomService.getAllRoomCheckOut(hotelId);
            return ResponseEntity.ok(allBookingCanCheckOut);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value = "/checkout/{bookingId}")
    public ResponseEntity<?> checkout (@PathVariable("bookingId") long bookingId){
        try {
            bookingRoomService.checkoutBooking(bookingId);
            return  ResponseEntity.ok().body(new MessageResponse("Check out sussesfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /*
    *
    * THỐNG KÊ CỦA DIRECTOR
    *
    * */
    @GetMapping(value = "/statistical/all")
    public ResponseEntity<?> thongKeChung (@RequestHeader("Authorization") String token){
        try {
            String newToken = token.substring(7);
            User director = getUserFromToken.getUserByUserNameFromJwt(newToken);
            Long directorId = director.getId();

            ThongKeDirectorChung thongKeDirectorChung = new ThongKeDirectorChung();
            thongKeDirectorChung.setBookingInDay(bookingRoomService.soDonDatPhongTrongNgay(directorId));
            thongKeDirectorChung.setTotalBookingInMonth(bookingRoomService.soDonDatPhongTrongThang(directorId));
            thongKeDirectorChung.setTotalSalesInMonth(bookingRoomService.tongDoanhThuTrongThang(directorId));
            thongKeDirectorChung.setBookingYesterday(bookingRoomService.soDonDatPhongTrongNgayHomQua(directorId));
            thongKeDirectorChung.setTotalBookingLastMonth(bookingRoomService.soDonDatPhongTrongThangTruoc(directorId));
            thongKeDirectorChung.setTotalSalesLastMonth(bookingRoomService.tongDoanhThuTrongThangTruoc(directorId));
            thongKeDirectorChung.setDirectorId(directorId);
            return  ResponseEntity.ok().body(thongKeDirectorChung);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping(value = "/statistical/{hotelId}")
    public ResponseEntity<?> thongKeChung (@RequestHeader("Authorization") String token, @PathVariable("hotelId") Long hotelId){
        try {
            String newToken = token.substring(7);
            User director = getUserFromToken.getUserByUserNameFromJwt(newToken);
            Long directorId = director.getId();

            ThongKeDirectorChung thongKeDirectorChung = new ThongKeDirectorChung();
            thongKeDirectorChung.setBookingInDay(bookingRoomService.soDonDatPhongTrongNgayKS(directorId,hotelId));
            thongKeDirectorChung.setTotalBookingInMonth(bookingRoomService.soDonDatPhongTrongThangKS(directorId, hotelId));
            thongKeDirectorChung.setTotalSalesInMonth(bookingRoomService.tongDoanhThuTrongThangKS(directorId,hotelId));
            thongKeDirectorChung.setDirectorId(directorId);
            thongKeDirectorChung.setHotelId(hotelId);
            return  ResponseEntity.ok().body(thongKeDirectorChung);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/statistical/chart/{year}")
    public ResponseEntity<?> thongKeDoanhThuVeBieuDo (@RequestHeader("Authorization") String token, @PathVariable("year") int year){
        try {
            String newToken = token.substring(7);
            User director = getUserFromToken.getUserByUserNameFromJwt(newToken);
            Long directorId = director.getId();
            List<Hotel> hotels = hotelService.findAllHotelByDirectorId(directorId);
            List<Long> hotelIds = new ArrayList<>();
            for(Hotel hotel : hotels){
                hotelIds.add(hotel.getId());
            }
            List<List<ThongKeDoanhThuDirector>> doanhThu = new ArrayList<>();
            for(Long hotelId : hotelIds) {
                 doanhThu.add(bookingRoomService.thongKeDoanhThuDeVeBieuDo(hotelId, directorId, year));
            }

            return  ResponseEntity.ok().body(doanhThu);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
