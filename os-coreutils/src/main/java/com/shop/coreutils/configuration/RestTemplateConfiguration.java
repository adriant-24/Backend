package com.shop.coreutils.configuration;

import com.shop.coreutils.api.client.APIClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
@RefreshScope
public class RestTemplateConfiguration {

    public static final Logger log = LoggerFactory.getLogger(APIClient.class);

    @Value("${com.onlineshop.maxTotalConnections:20}")
    int maxTotalConnections;

    @Value("${com.onlineshop.maxTotalConnectionsPerRoute:5}")
    int maxTotalConnectionsPerRoute;

    PoolingHttpClientConnectionManager customizedPoolingHttpClientConnectionManager;

    @Bean
    public PoolingHttpClientConnectionManager customizedPoolingHttpClientConnectionManager() {

        PoolingHttpClientConnectionManager poolingManager = new PoolingHttpClientConnectionManager();
        poolingManager.setMaxTotal(maxTotalConnections);
        poolingManager.setDefaultMaxPerRoute(maxTotalConnectionsPerRoute);
        return poolingManager;
    }
    @Bean
   // @LoadBalanced
    public RestTemplate restTemplate (RestTemplateBuilder restTemplateBuilder){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(4, TimeUnit.SECONDS)
                .setConnectTimeout(4,TimeUnit.SECONDS)
                .setResponseTimeout(4,TimeUnit.SECONDS)
                .build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(customizedPoolingHttpClientConnectionManager)
                .setConnectionManagerShared(true)
                .setDefaultRequestConfig(requestConfig);
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
        return  restTemplateBuilder.requestFactory(()->requestFactory).build();
     //   RestTemplate restTemplate = new RestTemplate();
        //RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    //    this.restTemplate = restTemplate;
     //   return restTemplate;
    }
}
