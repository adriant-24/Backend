package com.shop.onlineshop.repository;

import com.shop.onlineshop.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    @Query(value ="Select ui from UserInfo ui inner join ui.user u where u.username = :userName")
    UserInfo findUserInfoByUserName(@Param("userName") String userName);
}
