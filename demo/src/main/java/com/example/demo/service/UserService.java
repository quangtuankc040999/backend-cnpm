package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findByUserName(String username){
        return  userRepository.findByUsername(username);
    }

    public User getUserById(Long id){
        return userRepository.getOne(id);
    }
}
