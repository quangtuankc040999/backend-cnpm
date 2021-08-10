package com.example.demo.security.jwt;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserFromToken {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    public User getUser(String token) {
        Long id = jwtUtils.getUserIdFromJWT(token);
        return  userService.getUserById(id);
    }
    public User getUserByUserNameFromJwt(String token) {
        String userName = jwtUtils.getUserNameFromJwtToken(token);
        return  userService.findByUserName(userName);
    }

}
