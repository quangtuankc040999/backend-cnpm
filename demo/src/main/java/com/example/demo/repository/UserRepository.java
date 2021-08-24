package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.payload.reponse.ThongKeAdminTaiKhoan;
import com.example.demo.payload.reponse.ThongKeAdminTaiKhoan2;
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


    @Query(value = "SELECT count(user.id) as number, role.name  as roleName FROM user \n" +
            "join user_detail on user.id = user_detail.user_id \n" +
            "join user_roles on user.id = user_roles.user_id \n" +
            "join role on role.id = user_roles.role_id \n" +
            "where month(time_sign_up) = ?\n" +
            "group by role_id;", nativeQuery = true)
    public  List<ThongKeAdminTaiKhoan2> thongKeTaiKhoan(int month);



}
