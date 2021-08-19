package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.payload.reponse.RateResponse;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    // get rate room
    public RateResponse getRateRoom(Long roomId){
        return  commentRepository.getRateRoom(roomId);
    }
}
