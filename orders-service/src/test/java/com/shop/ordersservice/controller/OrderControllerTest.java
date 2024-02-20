package com.shop.ordersservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.coreutils.service.APIService;
import com.shop.coreutils.service.EmailService;
import com.shop.ordersservice.dto.ContactInfoDto;
import com.shop.ordersservice.dto.OrderDto;
import com.shop.ordersservice.dto.OrderItemDto;
import com.shop.ordersservice.dto.Purchase;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    static Purchase purchase;
    @Autowired
    ObjectMapper objectMapper;



    @Test
    @Order(1)
    void whenOrdersRequestIsCalled_thenOrderIsPlaced() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/purchase")
                        .content(objectMapper.writeValueAsString(purchase))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Order placed successfully. Please check your email for more details."));

    }

    @BeforeAll
    static void initTest(){
        Set<OrderItemDto> orderItemDtos = new HashSet<>();
        orderItemDtos.add(OrderItemDto.builder()
                .itemName("Item 1")
                .unitPrice(BigDecimal.valueOf(135.99))
                .imageUrl("/img1")
                .quantity(1)
              //  .orderItemId(1L)
                .build());
        orderItemDtos.add(OrderItemDto.builder()
                .itemName("Item 2")
                .unitPrice(BigDecimal.valueOf(50))
                .imageUrl("/img2")
                .quantity(2)
             //   .orderItemId(2L)
                .build());

        ContactInfoDto shippingInfo = ContactInfoDto.builder()
                .infoType("Shipping")
                .email("adriant_24@yahoo.com")
                .city("Rome")
                .country("Italy")
                .streetAndNumber("St. Ranoa, nr. 33")
                .county("Rome")
                .additionalAddress("Bl. 12A, ap. 5")
                .zipCode("200222")
                .phoneNumber("+24765111222")
                .firstName("Adrian")
                .lastName("Constantin")
                .build();
        ContactInfoDto billingInfo = ContactInfoDto.builder()
                .infoType("Billing")
                .email("adriant_24@yahoo.com")
                .city("Rome")
                .country("Italy")
                .streetAndNumber("St. Ranoa, nr. 33")
                .county("Rome")
                .additionalAddress("Bl. 12A, ap. 5")
                .zipCode("200222")
                .phoneNumber("+24765111222")
                .firstName("Adrian")
                .lastName("Constantin")
                .build();
        OrderDto order = OrderDto.builder()
                // .orderId(100L)
                // .orderTrackingNumber("AAA-BBB-01")
                .isPaid(true)
                .totalQuantity(3)
                .userId(1L)
                .payType("Credit Card")
                .status("Placed")
                .totalPrice(BigDecimal.valueOf(235.99))
                .build();

        purchase = Purchase.builder()
                .orderItems(orderItemDtos)
                .order(order)
                .billingContactInfo(billingInfo)
                .shippingContactInfo(shippingInfo)
                .build();
    }
}
