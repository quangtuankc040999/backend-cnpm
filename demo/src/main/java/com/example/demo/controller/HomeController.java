package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.payload.reponse.MessageResponse;
import com.example.demo.payload.request.UpdateInformationRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.GetUserFromToken;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HomeController {

    @Autowired
    GetUserFromToken getUserFromToken;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserService userService;

    // API update information

    @GetMapping(value = "/update-information")
    public ResponseEntity<?> updateInformation(@RequestHeader("Authorization") String token) {
        User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/update-information/save")
    public ResponseEntity<?> updateInformation(@RequestHeader("Authorization") String token, @RequestBody UpdateInformationRequest userRequest) {
        User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));
        UserDetail userDetail = user.getUserDetail();
        userDetail.setPhoneNumber(userRequest.getPhoneNumber());
        userDetail.setBirth(userRequest.getBirth());
        userDetail.setNameUserDetail(userRequest.getNameUserDetail());
        user.setUserDetail(userDetail);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("Update successfully"));
    }
}
