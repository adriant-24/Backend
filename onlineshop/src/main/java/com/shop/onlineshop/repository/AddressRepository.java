package com.shop.onlineshop.repository;

import com.shop.onlineshop.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(nativeQuery = true, value = "Select * from Address a where a.user_id = :userId")
    List<Address> findAllByUserId(@Param("userId")Long userId);
}
