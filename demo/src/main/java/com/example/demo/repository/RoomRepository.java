package com.example.demo.repository;

import com.example.demo.entity.Room;
import com.example.demo.payload.reponse.InfoNotifyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value="select * from room where id= ? and hotel_id = ?", nativeQuery=true)
    Room findOneById(Long roomid, Long hotelid);

    @Query(value="select * from room where hotel_id= ?", nativeQuery=true)
    List<Room> findAllRoomByHotelId(Long id);


    @Query(value = "SELECT * FROM room where hotel_id = ?1 and capacity = ?2", nativeQuery = true)
    List<Room> searchRoomByCapacity (Long hotelId, int capacity);

    @Query(value = "SELECT room_id FROM booking_room\n" +
            "where host_id = ? and  status = \"accepted\"" +
            "group by room_Id ", nativeQuery = true)
    List<Long> getAllRoomBookedByUser(Long userId);

    //=================================================================
    @Query (value ="select h_owner_id \n" +
            "from hotel join room on hotel.id = room.hotel_id\n" +
            "where room.id = ?", nativeQuery = true)
    Long getHotelDirectorId(Long roomId);
    
    // ================================== notify ============
    @Query(value = "select hotel.name as hotel, room.name as room  from room join hotel on hotel.id = room.hotel_id\n" +
            "where room.id = ?", nativeQuery = true)
    InfoNotifyResponse getHotelByRoomId(Long roomId);
}

