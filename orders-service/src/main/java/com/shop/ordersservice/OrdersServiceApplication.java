package com.shop.ordersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan
@EnableConfigurationProperties
@EnableWebSecurity(debug = true)
public class OrdersServiceApplication {



	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrdersServiceApplication.class, args);
	}


}
