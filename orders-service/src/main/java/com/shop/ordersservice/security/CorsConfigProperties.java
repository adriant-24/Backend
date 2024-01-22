package com.shop.ordersservice.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "web.cors")
public class CorsConfigProperties {


        private String[] allowedOrigins;

        private String[] allowedMethods;

        private String[] allowedHeaders;

        private String[] exposedHeaders;

        private long maxAge;
}
