package com.shop.onlineshop.repository;

import com.shop.onlineshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query(value="Select o from Order o JOIN FETCH o.items where o.orderId = :orderId")
//    Order findAllByOrderIdJoinFetchItem(@Param(value="orderId") Long orderId);
    Page<Order> findByUserId(Long userId, Pageable pageable);
}
