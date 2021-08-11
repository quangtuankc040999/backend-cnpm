package com.example.demo.repository;

import com.example.demo.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository  extends JpaRepository<Hotel,Long > {
    Hotel findById (long id);

    @Query(value = "SELECT * FROM hotel where is_active = 0;", nativeQuery = true)
    public List<Hotel> getHotelIsActiveFalse();

    @Query(value = "SELECT * FROM hotel where h_owner_id = ?", nativeQuery = true)
    List<Hotel> getAllHotelByDirectorId (Long idDirector);

}