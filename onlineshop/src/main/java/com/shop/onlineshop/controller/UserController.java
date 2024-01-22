package com.shop.onlineshop.controller;

//import com.shop.coreutils.api.client.APIClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.coreutils.api.client.APIClient;
import com.shop.onlineshop.dto.OrderDto;
import com.shop.onlineshop.dto.WebUser;
import com.shop.onlineshop.mapper.OrderMapper;
import com.shop.onlineshop.mapper.UserMapper;
import com.shop.onlineshop.service.OrderService;
import com.shop.onlineshop.service.UserService;
import com.shop.onlineshop.utils.PageableUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    APIClient apiClient;
    ObjectMapper objectMapper;
    @Autowired
    public UserController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
  //      this.apiClient = apiClient;
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
    public ResponseEntity<Map<String,Object>> getOrderByUserId(HttpServletRequest request,
                                                               @RequestParam(name="userName") String userName,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "orderId,asc"
                                                                       ) String[] sort) {


        Long userId = userService.findUserIdByName(userName);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("userId",String.valueOf(userId));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", request.getHeader("Authorization"));

        return apiClient.callAPI("http://localhost/8083/api/orders", HttpMethod.GET,
                queryParams, null, httpHeaders, null,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                new ParameterizedTypeReference<Map<String,Object>>(){}, true);
//        Page<OrderDto> pageOrders = orderService.
//                findByUserId(userId, PageableUtils.createPageable(page,size,sort)).
//                map(OrderMapper::orderToOrderDto);
//        return PageableUtils.preparePaginatedResponse("orders", pageOrders);
    }
}
