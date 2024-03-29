package com.shop.ordersservice.controller;


import com.shop.ordersservice.dto.OrderDto;
import com.shop.ordersservice.dto.Purchase;
import com.shop.ordersservice.dto.PurchaseResponse;
import com.shop.ordersservice.entity.Order;
import com.shop.ordersservice.event.OnOrderPlacedEvent;
import com.shop.ordersservice.mapper.OrderMapper;
import com.shop.ordersservice.mapper.PurchaseMapper;
import com.shop.ordersservice.service.OrderService;
import com.shop.coreutils.utils.PageableUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/api")
public class OrderController {

    OrderService orderService;

    ApplicationEventMulticaster applicationEventMulticaster;

    @Autowired
    public OrderController(OrderService orderService,
                           ApplicationEventMulticaster applicationEventMulticaster){
        this.orderService = orderService;
        this.applicationEventMulticaster = applicationEventMulticaster;
    }

    @PostMapping("/purchase")
    public String checkout(@RequestBody Purchase purchase, HttpServletRequest request){

        applicationEventMulticaster.multicastEvent(new OnOrderPlacedEvent(this, purchase, request.getContextPath()));

        return "Order placed successfully. Please check your email for more details.";

//        Order order = orderService.saveOrder(PurchaseMapper.purchaseToOrder(purchase));
//
//        return PurchaseResponse.
//                builder().
//                orderTrackingNumber(order.getOrderTrackingNumber())
//                .build();// new PurchaseResponse(order.getOrderTrackingNumber());
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String,Object>> getOrderByUserId(@RequestParam(name = "userId") long userId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "orderId,asc") String[] sort) {

       // Long userId = userService.findUserIdByName(authentication.getName());
        Page<OrderDto> pageOrders = orderService.
                findByUserId(userId, PageableUtils.createPageable(page,size,sort)).
                map(OrderMapper::orderToOrderDto);
        return PageableUtils.preparePaginatedResponse("orders", pageOrders);
    }

//    @Value("${myValue.test}")
//    String myValue;
//    @GetMapping("/testRefresh")
//    public String refresh(){
//        return "myValue: " + myValue;
//    }


}
