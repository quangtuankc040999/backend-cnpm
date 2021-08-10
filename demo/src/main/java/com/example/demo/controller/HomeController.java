package com.example.demo.controller;

import com.example.demo.entity.ConfirmationToken;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.payload.reponse.MessageResponse;
import com.example.demo.payload.request.PasswordRequest;
import com.example.demo.payload.request.UpdateInformationRequest;
import com.example.demo.repository.ConfirmationTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.GetUserFromToken;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

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

    // API change password
    @PostMapping(value = "/update-information/save-password")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String token, @RequestBody PasswordRequest passwordRequest) {
        User user = getUserFromToken.getUserByUserNameFromJwt(token.substring(7));
        if(encoder.matches(passwordRequest.getOldPassword(), user.getPassword() )) {
            user.setPassword(encoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().body(new MessageResponse("change password successfully"));
        } else {
            return ResponseEntity.ok().body(new MessageResponse("current password incorrect"));
        }
    }


    // forget password

    @PostMapping(value = "/forgot-password/{email}")
    public ResponseEntity<?> forgotUserPassword(@PathVariable("email") String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            // Create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
            // Save it
            confirmationTokenRepository.save(confirmationToken);
            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setText("Code: "+confirmationToken.getConfirmationToken());

            // Send the email
            emailSenderService.sendEmail(mailMessage);
            return ResponseEntity.ok().body(confirmationToken.getConfirmationToken());
        } else {
            return ResponseEntity.ok().body(new MessageResponse("Email does not exist"));
        }
    }

    @PostMapping(value = "/forgot-password/reset-password/{token}")
    public ResponseEntity<?> confirmPassword(@PathVariable("token") String token, @RequestParam("password") String passwordRequest) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        User user = confirmationToken.getUser();
        user.setPassword(encoder.encode(passwordRequest));

        userRepository.save(user);

        return ResponseEntity.ok().body(new MessageResponse("change password successfully"));
    }

}
