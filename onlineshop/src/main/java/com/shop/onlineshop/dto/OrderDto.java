package com.shop.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class OrderDto {

    Long orderId;

    private String orderTrackingNumber;

    private int totalQuantity;

    private BigDecimal totalPrice;

    @NotBlank
    private String status;

    String payType;
    @JsonProperty("isPaid")
    boolean isPaid;

    private Date dateCreated;

    private Date lastUpdated;

    @Builder.Default
    Set<OrderItemDto> orderItems = new HashSet<>();

    ContactInfoDto shippingContactInfo;

    ContactInfoDto billingContactInfo;

    Long userId;
}
