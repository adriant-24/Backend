package com.shop.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private Long orderItemId;

    private String imageUrl;

    private String itemName;

    private BigDecimal unitPrice;

    private int quantity;

    private Long productId;

    private OrderDto order;
}
