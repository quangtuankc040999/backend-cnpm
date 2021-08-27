package com.example.demo.repository;

import com.example.demo.entity.Hotel;
import com.example.demo.payload.reponse.ThongKeDoanhThuDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository  extends JpaRepository<Hotel,Long > {
    Hotel findById (long id);

    @Query(value = "SELECT * FROM hotel where is_active = 0 and is_delete = 0;", nativeQuery = true)
    public List<Hotel> getHotelIsActiveFalse();

    @Query(value = "SELECT * FROM hotel where h_owner_id = ? and is_delete = 0", nativeQuery = true)
    List<Hotel> getAllHotelByDirectorId (Long idDirector);

    @Query(value="SELECT * FROM hotel  join localization on hotel.id = localization.hotel_id where localization.province like  %? and is_delete = 0 ", nativeQuery=true)
    List<Hotel> findAllHotelByProvice (String province);

    @Query(value = "SELECT id as hotelId, name as hotelName, 0 as totalInMonth  FROM hotel where h_owner_id = ? and is_delete = 0", nativeQuery = true)
    List<ThongKeDoanhThuDirector> getAllHotelVeBieuDo (Long directorId);




}