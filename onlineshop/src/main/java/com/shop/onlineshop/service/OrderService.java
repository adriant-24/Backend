package com.shop.onlineshop.service;

import com.shop.onlineshop.entity.Order;
import com.shop.onlineshop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Page<Order> findAll(Pageable pageable){
        return orderRepository.findAll(pageable);
    }

    public Order findById(Long orderId){
        return orderRepository.findById(orderId).orElse(null);
    }

    @Transactional
    public Order saveOrder(Order order){
        order.setOrderTrackingNumber(generateOrderTrackingNumber());
        return  orderRepository.save(order);
    }

    String generateOrderTrackingNumber(){
        return UUID.randomUUID().toString();
    }

    public Page<Order> findByUserId(Long userId, Pageable pageable){
        return orderRepository.findByUserId(userId,pageable);
    }
}
