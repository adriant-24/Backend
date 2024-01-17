package com.shop.onlineshop.api.client;


import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class APIClient {

    public static final Logger log = LoggerFactory.getLogger(APIClient.class);

    @Value("${com.onlineshop.maxTotalConnections:20}")
    int maxTotalConnections;

    @Value("${com.onlineshop.maxTotalConnectionsPerRoute:5}")
    int maxTotalConnectionsPerRoute;
    RestTemplate restTemplate;

    PoolingHttpClientConnectionManager customizedPoolingHttpClientConnectionManager;

    @Bean
    public PoolingHttpClientConnectionManager customizedPoolingHttpClientConnectionManager() {

        PoolingHttpClientConnectionManager poolingManager = new PoolingHttpClientConnectionManager();
        poolingManager.setMaxTotal(maxTotalConnections);
        poolingManager.setDefaultMaxPerRoute(maxTotalConnectionsPerRoute);
        return poolingManager;
    }
    @Bean
    public RestTemplate restTemplate (){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(4,TimeUnit.SECONDS)
                .setConnectTimeout(4,TimeUnit.SECONDS)
                .setResponseTimeout(4,TimeUnit.SECONDS)
                .build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(customizedPoolingHttpClientConnectionManager)
                .setConnectionManagerShared(true)
                .setDefaultRequestConfig(requestConfig);

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build()));
        //RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        this.restTemplate = restTemplate;
        return restTemplate;
    }

    @Value("${com.online.shop.apiNumberOfRetries:3}")
    int numberOfRetries;

    @Value("${com.online.shop.waitTimeBetweenRetriesSec:10}")
    int waitTime;

    @Value("${com.online.shop.statusesToRetry:}")
    String statusesToRetry;

    public <T> ResponseEntity<T> callAPI(String url,
                                         HttpMethod method,
                                         MultiValueMap<String, String> queryParams,
                                         Map<String,String> pathVariables,
                                         HttpHeaders headers,
                                         Object body,
                                         MediaType accept,
                                         MediaType contentType,
                                         ParameterizedTypeReference<T> returnType,
                                         boolean shouldRetry) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);

        if(pathVariables != null){
            uriComponentsBuilder = UriComponentsBuilder.fromUri(uriComponentsBuilder.buildAndExpand(pathVariables).toUri());
        }
        if (queryParams != null)
            uriComponentsBuilder.queryParams(queryParams);
        String uri = uriComponentsBuilder.build().toUri().toString();
        RequestEntity.BodyBuilder requestBodyBuilder = RequestEntity.method(method, uriComponentsBuilder.build().toUri());

        if (accept != null)
            requestBodyBuilder.accept(accept);

        if (contentType != null)
            requestBodyBuilder.contentType(contentType);

        if (headers != null)
            addCustomHttpHeaders(headers, requestBodyBuilder);

        RequestEntity<Object> requestEntity = requestBodyBuilder.body(body);
        ResponseEntity<T> responseEntity;

        int tryNumber = 1;
        while (tryNumber <= numberOfRetries) {
            try {
                responseEntity = restTemplate.exchange(requestEntity, returnType);
                if(!isRetryingResponseStatus(responseEntity.getStatusCode()))
                    return responseEntity;
                else
                    try {
                        log.warn("Response status is in Retry Responses List:" + responseEntity.getStatusCode() + ". Retrying...)");
                        Thread.sleep(waitTime * 1000 * (long) Math.pow(2, tryNumber - 1));
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                        e.printStackTrace();
                    }

            } catch (RestClientException re) {
                log.warn("Failed to send message to:" + url + ". Retrying...(error: " + re.getMessage() + ")");

                try {
                    Thread.sleep(waitTime * 1000 * (long) Math.pow(2, tryNumber - 1));
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
            }
            tryNumber++;
        }


        return restTemplate.exchange(requestEntity, returnType);
    }

    public boolean isRetryingResponseStatus(HttpStatusCode statusCode){
        if(statusesToRetry.isEmpty())
            return false;
        return Arrays.stream(statusesToRetry.split(",")).map(Integer::parseInt).anyMatch(v-> v == statusCode.value());
    }

    public void addDefaultHttpHeaders(HttpEntity<?> httpEntity) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpEntity.getHeaders().addAll(httpHeaders);
    }

    public void addCustomHttpHeaders(HttpHeaders customHeaders, RequestEntity.BodyBuilder bodyBuilder) {
        customHeaders
                .forEach((key, value) ->
                        bodyBuilder
                                .header(key, value.toArray(new String[value.size()]))
                );
    }

    public void addParameters(UriComponentsBuilder uriComponentsBuilder, Map<String, String> queryParameters) {
        queryParameters.forEach((key, value) -> uriComponentsBuilder.queryParam(key, value));
    }
}
