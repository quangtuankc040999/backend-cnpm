package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.payload.reponse.InfoNotifyResponse;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public void saveRoom(Room room){roomRepository.save(room);}
    public Room getRoomById(Long roomid, Long hotelid) {
        return roomRepository.findOneById(roomid, hotelid);
    }

    public List<Room> getAllRoomByHotelId(Long id) {
        return roomRepository.findAllRoomByHotelId(id);
    }
    public Room findOne(Long idRoom){
        return roomRepository.getOne(idRoom);
    }

    public List<Room> searchRoomByCapacity(Long hotelId, int capacity){
        return  roomRepository.searchRoomByCapacity(hotelId,capacity);
    }
    public List<Long> getAllRoomBookedByUser(Long userId){
        return  roomRepository.getAllRoomBookedByUser(userId);
    }

    public Long getHotelDirectorId(Long roomId){
        return roomRepository.getHotelDirectorId(roomId);
    }

    public InfoNotifyResponse getHotelByRoomId (Long roomId){
        return  roomRepository.getHotelByRoomId(roomId);
    }


}
