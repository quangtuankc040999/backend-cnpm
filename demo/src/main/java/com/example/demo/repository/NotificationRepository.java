package com.example.demo.repository;

import com.example.demo.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "SELECT * FROM notification where for_user = ? order by time_notification desc", nativeQuery = true)
    List<Notification> getNotificationOfUser(Long userId);

    @Query(value = "SELECT * FROM notification where id = ? ", nativeQuery = true)
    Notification getOneNotificationOfUserForRead(Long bookingId);

}
