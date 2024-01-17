package com.shop.onlineshop.mapper;

import com.shop.onlineshop.dto.Purchase;
import com.shop.onlineshop.entity.Order;

public class PurchaseMapper {

    public static Order purchaseToOrder(Purchase purchase){

        //Order order = Order.builder()
        Order order = OrderMapper.orderDtoToOrder(purchase.getOrder());
        OrderMapper.orderItemsDtoToOrderItems(purchase.getOrderItems()).forEach(order::addOrderItem);
        order.setBillingContactInfo(ContactInfoMapper.contactInfoDtoToContactInfo(purchase.getBillingContactInfo()));
        order.setShippingContactInfo(ContactInfoMapper.contactInfoDtoToContactInfo(purchase.getShippingContactInfo()));
        return order;
    }
}
