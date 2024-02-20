package com.shop.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.coreutils.api.client.APIClient;
import com.shop.onlineshop.controller.UserController;
import com.shop.onlineshop.dto.OrderDto;
import com.shop.onlineshop.service.ProductService;
import com.shop.onlineshop.service.UserService;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.junit.Before;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc()
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@WebMvcTest(controllers = UserController.class)
//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@MockBean(RestTemplate.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

//    @Autowired
//    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

//    @Autowired
//    APIClient apiClient;

//    @Autowired
//    PoolingHttpClientConnectionManager customizedPoolingHttpClientConnectionManager;

//    @Autowired
//    UserService userService;


//    @Autowired
//    com.shop.onlineshop.controller.UserController userController;

    static List<OrderDto> orderDtoList;

    @BeforeAll
    static public void setInitialObjects(){
        orderDtoList = new ArrayList<>();
        orderDtoList.add(OrderDto.builder()
                .orderId(100L)
                .orderTrackingNumber("AAA-BBB-01")
                .isPaid(true)
                .userId(1L)
                .payType("Credit Card")
                .status("In Delivery")
                .totalPrice(BigDecimal.valueOf(235.99))
                .build());
        orderDtoList.add(OrderDto.builder()
                .orderId(101L)
                .orderTrackingNumber("AAA-BBB-02")
                .isPaid(true)
                .userId(1L)
                .payType("Credit Card")
                .status("Delivered")
                .totalPrice(BigDecimal.valueOf(100.99))
                .build());

    }

    public void mockRestTemplate(){
        ResponseEntity<Map<String, Object>> ordersResponse =  prepareMockResponse("orders", orderDtoList, 1);
        Mockito.when(
                        restTemplate.exchange(any(RequestEntity.class),
                                ArgumentMatchers.<ParameterizedTypeReference<Map<String, Object>>>any()))
                .thenReturn(ordersResponse);
    }
    @Test
    @Order(1)
    public void givenMockingIsDoneByMockito_whenGetOrdersIsCalled_shouldReturnMockedObject() throws Exception {

        mockRestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userName", "john.tuker");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/orders").params(params))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("totalItems").value(1))
                .andExpect(jsonPath("orders", hasSize(2)))
                .andExpect(jsonPath("orders[0].orderTrackingNumber").value("AAA-BBB-01"));

    }

    ResponseEntity<Map<String, Object>> prepareMockResponse(String entityName, Object entity, int itemsNumber){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(entityName, entity);
        responseMap.put("currentPage", 1);
        responseMap.put("totalItems", itemsNumber);
        responseMap.put("totalPages", 1);
        responseMap.put("size", 1);
        responseMap.put("numberOfElements",itemsNumber);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }



}
