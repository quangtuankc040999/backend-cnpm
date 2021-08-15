package com.example.demo.service;

import com.example.demo.entity.Notification;
import com.example.demo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    public void save(Notification notification){
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationOfUser(Long userId){
        return notificationRepository.getNotificationOfUser(userId);
    }

    public  Notification getToRead(Long bookingId){
        return  notificationRepository.getOneNotificationOfUserForRead(bookingId);
    }
}
