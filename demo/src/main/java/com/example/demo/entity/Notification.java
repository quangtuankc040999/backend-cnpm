package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private LocalDateTime timeNotification;
    private long forUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeNotification() {
        return timeNotification;
    }

    public void setTimeNotification(LocalDateTime timeNotification) {
        this.timeNotification = timeNotification;
    }

    public long getForUser() {
        return forUser;
    }

    public void setForUser(long forUser) {
        this.forUser = forUser;
    }
}
