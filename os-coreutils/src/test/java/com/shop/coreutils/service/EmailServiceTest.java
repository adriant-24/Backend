package com.shop.coreutils.service;

import com.shop.coreutils.model.EmailDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    void sendEmailTest(){
        EmailDetails emailDetails = EmailDetails.builder()
                .recepient("adriant_24@yahoo.com")
                .subject("Test Mail OnlineShop Subject")
                .body("Test Mail OnlineShop body")
                .build();

        String result = emailService.sendEmail(emailDetails);
        assertEquals(result,"Email sent successfully.");
    }
}
