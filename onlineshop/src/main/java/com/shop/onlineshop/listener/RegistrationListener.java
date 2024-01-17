package com.shop.onlineshop.listener;

import com.shop.onlineshop.dto.EmailDetails;
import com.shop.onlineshop.entity.User;
import com.shop.onlineshop.entity.VerificationToken;
import com.shop.onlineshop.event.OnRegistrationCompleteEvent;
import com.shop.onlineshop.service.EmailService;
import com.shop.onlineshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    Logger logger = LoggerFactory.getLogger(RegistrationListener.class);
    EmailService emailService;

    UserService userService;

    @Autowired
    public RegistrationListener(EmailService emailService, UserService userService){
        this.emailService = emailService;
        this.userService = userService;
    }
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {

        this.confirmRegistration(event);

    }

    public void confirmRegistration(OnRegistrationCompleteEvent event){
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = userService.createToken(token, user);

        String body = "Please click bellow link to activate your account: \n";
        String confirmationUrl = event.getAppUrl() + "/register/registrationConfirmation?token=" + token;
        EmailDetails emailDetails = EmailDetails.builder()
                .recepient(event.getUser().getUsername())
                .body(body + "http://localhost:8080" + confirmationUrl)
                .subject("OnlineShop Confirm Registration")
                .build();

        String response = emailService.sendEmail(emailDetails);
        logger.info(response);

    }
}
