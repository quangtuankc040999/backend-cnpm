package com.example.demo.repository;

import com.example.demo.entity.Comment;
import com.example.demo.payload.reponse.RateResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT room_id as room_id, avg(star) as rate, count(id) as total FROM comment where room_id = ? group by room_id;", nativeQuery = true)
    RateResponse getRateRoom( Long roomId);
}
