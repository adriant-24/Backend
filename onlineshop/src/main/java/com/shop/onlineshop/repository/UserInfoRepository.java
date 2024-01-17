package com.shop.onlineshop.repository;

import com.shop.onlineshop.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    @Query(nativeQuery = true, value ="Select ui.* from User u, User_Info ui where u.user_id = ui.user_id and u.username = :userName")
    UserInfo findUserInfoByUserName(@Param("userName") String userName);
}
