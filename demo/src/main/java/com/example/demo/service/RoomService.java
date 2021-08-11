package com.example.demo.service;

import com.example.demo.entity.Room;
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
}
