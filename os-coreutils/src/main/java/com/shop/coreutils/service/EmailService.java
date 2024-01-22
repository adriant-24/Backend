package com.shop.coreutils.service;

import com.shop.coreutils.model.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String sender;
    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public String sendEmail(EmailDetails emailDetails){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailDetails.getRecepient());
            simpleMailMessage.setText(emailDetails.getBody());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            javaMailSender.send(simpleMailMessage);

            return "Email sent successfully.";
        }catch (Exception e)
        {
            return "Error while trying to send email...";
        }
    }
}
