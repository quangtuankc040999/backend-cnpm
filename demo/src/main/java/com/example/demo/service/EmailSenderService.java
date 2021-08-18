package com.example.demo.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {

    private JavaMailSender javaMailSender;
    private  static final  String YOUR_DOMAIN_NAME = "sandbox15e74a32d85a4483836913af79848b42.mailgun.org";
    private static final String API_KEY = "b3c9aa5ec9065f6ad284bbd0a5a60a91-9776af14-77286c47";

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }


    @Async
    public void sendSimpleMessage(String email, String token) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
			    .basicAuth("api", API_KEY)
                .field("from", "Excited User <bookinghotelcnpm2021@gmail.com>")
                .field("to", "Excited User <"+email+">")
                .field("subject", "Token reset password")
                .field("text", "CODE: "+token+"")
                .asJson();
    }

}