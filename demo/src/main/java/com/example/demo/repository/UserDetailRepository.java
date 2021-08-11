package com.example.demo.repository;

import com.example.demo.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    @Query(value = "SELECT * FROM user_detail where user_id = ?1" , nativeQuery = true)
    UserDetail findOne(Long userId);
    @Query(value = "SELECT * FROM user_detail where user_id = ?;", nativeQuery = true)
    UserDetail getAvatar(Long userId);
}
