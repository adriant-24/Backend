package com.shop.ordersservice.mapper;

import com.shop.ordersservice.dto.OrderDto;
import com.shop.ordersservice.dto.OrderItemDto;
import com.shop.ordersservice.entity.Order;
import com.shop.ordersservice.entity.OrderItem;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order orderDtoToOrder(OrderDto orderDto){
        return Order.builder()
                .orderId(orderDto.getOrderId())
                .totalPrice(orderDto.getTotalPrice())
                .totalQuantity(orderDto.getTotalQuantity())
                .status(orderDto.getStatus())
                .orderTrackingNumber(orderDto.getOrderTrackingNumber())
                .payType(orderDto.getPayType())
                .isPaid(orderDto.isPaid())
               // .dateCreated(orderDto.getDateCreated())
               // .lastUpdated(orderDto.getLastUpdated())
              //  .orderItems(orderItemsDtoToOrderItems(orderDto.getOrderItems()))
              //  .billingContactInfo(ContactInfoMapper.contactInfoDtoToContactInfo(orderDto.getBillingContactInfo()))
           //     .shippingContactInfo(ContactInfoMapper.contactInfoDtoToContactInfo(orderDto.getShippingContactInfo()))
                .userId(orderDto.getUserId())
                .build();
    }

    public static OrderDto orderToOrderDto(Order order){
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .totalPrice(order.getTotalPrice())
                .totalQuantity(order.getTotalQuantity())
                .status(order.getStatus())
                .orderTrackingNumber(order.getOrderTrackingNumber())
                .payType(order.getPayType())
                .isPaid(order.isPaid())
                .dateCreated(order.getDateCreated())
                .lastUpdated(order.getLastUpdated())
                .orderItems(orderItemsToOrderItemsDto(order.getOrderItems()))
                .billingContactInfo(ContactInfoMapper.contactInfoToContactInfoDto(order.getBillingContactInfo()))
                .shippingContactInfo(ContactInfoMapper.contactInfoToContactInfoDto(order.getShippingContactInfo()))
                .userId(order.getUserId())
                .build();
    }

    public static Set<OrderItem> orderItemsDtoToOrderItems(Set<OrderItemDto> orderItems){
       return orderItems != null ?
                orderItems.stream().map(OrderMapper::orderItemDtoToOrderItem).collect(Collectors.toSet()) :
                null;
    }

    public static Set<OrderItemDto> orderItemsToOrderItemsDto(Set<OrderItem> orderItems){
        return orderItems != null ?
                orderItems.stream().map(OrderMapper::orderItemToOrderItemDto).collect(Collectors.toSet()) :
                null;
    }

    public static OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto){
        return OrderItem.builder()
                .orderItemId(orderItemDto.getOrderItemId())
                .itemName(orderItemDto.getItemName())
                .imageUrl(orderItemDto.getImageUrl())
                .unitPrice(orderItemDto.getUnitPrice())
                .quantity(orderItemDto.getQuantity())
                .productId(orderItemDto.getProductId())
                .build();
    }

    public static OrderItemDto orderItemToOrderItemDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .itemName(orderItem.getItemName())
                .imageUrl(orderItem.getImageUrl())
                .unitPrice(orderItem.getUnitPrice())
                .quantity(orderItem.getQuantity())
                .productId(orderItem.getProductId())
                .build();
    }


}
