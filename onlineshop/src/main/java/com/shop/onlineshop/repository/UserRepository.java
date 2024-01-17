package com.shop.onlineshop.repository;

import com.shop.onlineshop.entity.User;
import com.shop.onlineshop.entity.UserInfo;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query(nativeQuery = true, value = "Select * from Address a, User u where a.user_id = u.user_id and u.user_id = :userId")
//    List<Address> findAllAddressesByUserId(@Param("userId")int userId);

    @Query("Select u from User u JOIN FETCH u.addresses where u.id = :userId")
    User findByIdJoinFetchAddress(@Param("userId") Long userId);


    @Query("Select u from User u where u.userId = :userId")
    User findByIdUserOnly(@Param("userId") Long userId);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    @Query("Select u.userId from User u where u.username = :userName")
    Long findUserIdByName(@Param("userName") String userName);


    User findByUsername(String username);

    @Query(nativeQuery = true, value ="Select ui.* from User u, User_Info ui where u.user_id = ui.user_id and u.username = :userName")
    UserInfo findUserInfoByUserName(@Param("userName") String userName);

}
