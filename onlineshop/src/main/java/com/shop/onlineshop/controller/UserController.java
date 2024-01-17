package com.shop.onlineshop.controller;

import com.shop.onlineshop.dto.OrderDto;
import com.shop.onlineshop.dto.WebUser;
import com.shop.onlineshop.mapper.OrderMapper;
import com.shop.onlineshop.mapper.UserMapper;
import com.shop.onlineshop.service.OrderService;
import com.shop.onlineshop.service.UserService;
import com.shop.onlineshop.utils.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;
    OrderService orderService;
    @Autowired
    public UserController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
    }

    @RequestMapping("/login")
    public WebUser login(Authentication authentication){
        String userName = authentication.getName();

        WebUser webUser = UserMapper.userToWebUser(userService.findByUserName(userName));
        return webUser;
    }

    @GetMapping("")
    public WebUser getUserByName(@RequestParam("userName") String userName){

        WebUser webUser = UserMapper.userToWebUser(userService.findByUserName(userName));

        return webUser;

    }
    @GetMapping("/orders")
    public ResponseEntity<Map<String,Object>> getOrderByUserId(Authentication authentication,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "orderId,asc") String[] sort) {

        Long userId = userService.findUserIdByName(authentication.getName());
        Page<OrderDto> pageOrders = orderService.
                findByUserId(userId, PageableUtils.createPageable(page,size,sort)).
                map(OrderMapper::orderToOrderDto);
        return PageableUtils.preparePaginatedResponse("orders", pageOrders);
    }
}
