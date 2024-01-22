package com.shop.ordersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PurchaseResponse {

    String orderTrackingNumber;

}
