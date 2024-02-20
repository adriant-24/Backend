package com.shop.ordersservice.listener;

import com.shop.coreutils.model.EmailDetails;
import com.shop.coreutils.service.EmailService;
import com.shop.ordersservice.dto.ContactInfoDto;
import com.shop.ordersservice.dto.Purchase;
import com.shop.ordersservice.entity.ContactInfo;
import com.shop.ordersservice.entity.Order;
import com.shop.ordersservice.event.OnOrderPlacedEvent;
import com.shop.ordersservice.mapper.OrderMapper;
import com.shop.ordersservice.mapper.PurchaseMapper;
import com.shop.ordersservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedListener {

    Logger logger = LoggerFactory.getLogger(OrderPlacedListener.class);
    OrderService orderService;
    EmailService emailService;

    String appUrl;
    @Autowired
    public OrderPlacedListener(OrderService orderService,
                               EmailService emailService
    ){
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @EventListener(OnOrderPlacedEvent.class)
    public void onApplicationEvent(OnOrderPlacedEvent event) {
        this.appUrl = event.getAppUrl();
        placeOrder(event.getPurchase());
    }

    void placeOrder(Purchase purchase){
        Order order = orderService.saveOrder(PurchaseMapper.purchaseToOrder(purchase));
        logger.info("Order Saved!");
        sendEmailForPlacedOrder(order);
    }

    void sendEmailForPlacedOrder(Order order){
        ContactInfo contactInfo = order.getShippingContactInfo();
        String urlOrders = "http://localhost:4200" + appUrl + "/user/orders";
        String body = String.format("""
                        Hello %s.\s
                        Thank you for your order placed on OnlineShop.\s
                        Your order has the number %s and is currently in waiting until your payment is confirmed. \s
                        For more information about your order you can check the orders in your account: %s""",
                contactInfo.getFirstName(),
                order.getOrderTrackingNumber(),
                urlOrders);
        logger.info("Email body: " + body);
        EmailDetails emailDetails = EmailDetails.builder()
                .recepient(contactInfo.getEmail())
                .subject("OnlineShop order placed confirmation")
                .body(body)
                .build();

        String response = emailService.sendEmail(emailDetails);
        logger.info(response);
    }
}