package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findByEmail(String email);

    @Query(value = "SELECT * FROM user u join user_roles ur on u.id = ur.user_id where u.is_active = 0 and ur.role_id = 2", nativeQuery = true)
    public List<User> findDirectorActiveFalse ();





}
