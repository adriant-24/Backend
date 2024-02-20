package com.shop.ordersservice.event;

import com.shop.ordersservice.dto.Purchase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnOrderPlacedEvent extends ApplicationEvent {

    Purchase purchase;
    String appUrl;
    public OnOrderPlacedEvent(Object source, Purchase purchase, String appUrl) {
        super(source);
        this.purchase = purchase;
        this.appUrl = appUrl;
    }
}
