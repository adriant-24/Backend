package com.shop.apigateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;
import static org.springframework.core.io.buffer.DataBufferUtils.join;

//@Component
//public class LoggingFilter {}implements GlobalFilter {
//    Log log = LogFactory.getLog(getClass());
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
//        String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
//        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
//        URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
//        log.info("Incoming request " + originalUri + " is routed to id: " + route.getId()
//                + ", uri:" + routeUri);
//
////        ServerHttpRequestDecorator requestMutated = new ServerHttpRequestDecorator(exchange.getRequest()) {
////            @Override
////            public Flux<DataBuffer> getBody() {
////                StringBuilder body = new StringBuilder();
////               // Logger requestLogger = new Logger(getDelegate());
////                if(String.valueOf(getHeaders().getContentType()).toLowerCase().equals(MediaType.APPLICATION_JSON_VALUE)) {
////                    return super.getBody().map(ds -> {
////                        body.append(ds.asByteBuffer());
////                        log.debug(body);
////                        return ds;
////                    }).doFinally(s -> log.debug(body));
////                } else {
////                   // requestLogger.log();
////                    return super.getBody();
////                }
////            }
////        };
//
//        ServerHttpResponseDecorator responseMutated = new ServerHttpResponseDecorator(exchange.getResponse()) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                StringBuilder responseBody = new StringBuilder();
//               // Logger responseLogger = new Logger(getDelegate());
//                if(String.valueOf(getHeaders().getContentType()).toLowerCase().equals(MediaType.APPLICATION_JSON_VALUE)) {
//                    return join(body).flatMap(db -> {
//                        responseBody.append(db.asByteBuffer());
//                        log.debug(responseBody);
//                        return getDelegate().writeWith(Mono.just(db));
//                    });
//                } else {
//                   // responseLogger.log();
//                    return getDelegate().writeWith(body);
//                }
//            }
//        };
//        return chain.filter(exchange.mutate().response(responseMutated).build());
//    }
//}