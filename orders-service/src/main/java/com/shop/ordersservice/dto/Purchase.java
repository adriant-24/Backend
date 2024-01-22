package com.shop.ordersservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private ContactInfoDto shippingContactInfo;
    private ContactInfoDto billingContactInfo;
    private OrderDto order;
    private Set<OrderItemDto> orderItems;
}
