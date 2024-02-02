package com.shop.onlineshop.controller;

//import com.shop.coreutils.api.client.APIClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.coreutils.api.client.APIClient;
import com.shop.onlineshop.dto.AddressDto;
import com.shop.onlineshop.dto.WebUser;
import com.shop.onlineshop.mapper.AddressMapper;
import com.shop.onlineshop.mapper.UserMapper;
import com.shop.onlineshop.service.OrderService;
import com.shop.onlineshop.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;
    OrderService orderService;

    APIClient apiClient;
    ObjectMapper objectMapper;
    @Autowired
    public UserController(UserService userService, OrderService orderService, APIClient apiClient){
        this.userService = userService;
        this.orderService = orderService;
        this.apiClient = apiClient;
    }

    @RequestMapping("/login")
    public WebUser login(Authentication authentication){
        String userName = authentication.getName();

        WebUser webUser = UserMapper.userToWebUser(userService.findByUserName(userName));
        return webUser;
    }
    @RequestMapping("/addresses")
    public List<AddressDto> getUserAddresses(@RequestParam("userName") String userName){

        return (userService.findAllAddressesByUserName(userName)).
                stream().map(AddressMapper::addressToAddressDto).toList();
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

        String ordersServiceUrl = apiClient.getServiceURL("ORDERS-SERVICE");
        return apiClient.callAPI(ordersServiceUrl + "/api/orders", HttpMethod.GET,
                queryParams, null, httpHeaders, null,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                new ParameterizedTypeReference<Map<String,Object>>(){}, true);
//        Page<OrderDto> pageOrders = orderService.
//                findByUserId(userId, PageableUtils.createPageable(page,size,sort)).
//                map(OrderMapper::orderToOrderDto);
//        return PageableUtils.preparePaginatedResponse("orders", pageOrders);
    }

    //@CircuitBreaker
   //  @Retry(name="${spring.application.name}", fallbackMethod = "getDefaultTestOrders")
    @GetMapping("/testOrders")
    public ResponseEntity<String> testOrders(){
        String ordersServiceUrl = apiClient.getServiceURL("ORDERS-SERVICE");
        return apiClient.callAPIWithRetry(ordersServiceUrl + "/api/testRefresh", HttpMethod.GET,
                null, null, null, null,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                new ParameterizedTypeReference<String>(){});
    }

    ResponseEntity<String> getDefaultTestOrders(Exception exception){
        return new ResponseEntity<>("zzzzzzzzzzz", HttpStatus.OK);
    }
}
