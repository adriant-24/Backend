package com.shop.onlineshop.runner;

import com.shop.onlineshop.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {


    @Autowired
    UserServiceImpl userService;

    @Override
    public void run(String... args) throws Exception {

        //saveItem();
    }



}