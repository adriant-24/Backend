package com.shop.onlineshop.controller;


import com.shop.onlineshop.dto.Purchase;
import com.shop.onlineshop.dto.PurchaseResponse;
import com.shop.onlineshop.entity.Order;
import com.shop.onlineshop.mapper.PurchaseMapper;
import com.shop.onlineshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CheckoutController {

    OrderService orderService;

    @Autowired
    public CheckoutController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public PurchaseResponse checkout(@RequestBody Purchase purchase){

        Order order = orderService.saveOrder(PurchaseMapper.purchaseToOrder(purchase));

        return PurchaseResponse.
                builder().
                orderTrackingNumber(order.getOrderTrackingNumber())
                .build();// new PurchaseResponse(order.getOrderTrackingNumber());
    }


}
