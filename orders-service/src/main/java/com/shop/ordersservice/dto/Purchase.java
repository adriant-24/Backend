package com.shop.ordersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Purchase {
    private ContactInfoDto shippingContactInfo;
    private ContactInfoDto billingContactInfo;
    private OrderDto order;
    private Set<OrderItemDto> orderItems;
}
