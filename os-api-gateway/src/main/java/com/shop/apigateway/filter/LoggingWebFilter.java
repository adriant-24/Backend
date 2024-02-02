package com.shop.apigateway.filter;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * Project: gateway-service-filter-properties.<br/>
 * Des: <br/>
 * User: HieuTT<br/>
 * Date: 04/08/2022<br/>
 */
@Component

public class LoggingWebFilter implements WebFilter {

    private final Logger log = LoggerFactory.getLogger(LoggingWebFilter.class);

    /**
     * Process the Web request and (optionally) delegate to the next
     * {@code WebFilter} through the given {@link WebFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("XXXXXXXXXXXXXXs347823489349");
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        DataBufferFactory dataBufferFactory = response.bufferFactory();

//        Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
//        String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
//        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
//        URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
//        log.info("Incoming request " + originalUri + " is routed to id: " + route.getId()
//                + ", uri:" + routeUri);

        // log the request body
        ServerHttpRequest decoratedRequest = getDecoratedRequest(request);
        // log the response body
        ServerHttpResponseDecorator decoratedResponse = getDecoratedResponse(response, request, dataBufferFactory);
        return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build());
    }

    private ServerHttpResponseDecorator getDecoratedResponse(ServerHttpResponse response, ServerHttpRequest request,
                                                             DataBufferFactory dataBufferFactory) {
        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux) {

                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                        DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
                        byte[] content = new byte[joinedBuffers.readableByteCount()];
                        joinedBuffers.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);// MODIFY RESPONSE and Return
                        log.info("Response Body: {}", responseBody);
                        // the Modified response
//                        JSONObject jsonObject = new JSONObject(responseBody);
//                        System.out.println("final value red from : " + jsonObject.getString("message"));
//                        jsonObject.put("message", "");
                        return dataBufferFactory.wrap(responseBody.getBytes());
                    }).switchIfEmpty(Flux.defer(() -> {

                        log.info("Write to database here");
                        return Flux.just();
                    }))).onErrorResume(err -> {
                        log.error("error while decorating Response: {}", err.getMessage());
                        return Mono.empty();
                    });

                } else {
                    log.info("Body not an instance of Flux");
                }
                return super.writeWith(body);
            }
        };
    }

    private ServerHttpRequest getDecoratedRequest(ServerHttpRequest request) {

        return new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {

                log.info("requestId: {}, method: {} , url: {}", request.getId(), request.getMethod(),
                        request.getURI());
                return super.getBody().publishOn(Schedulers.boundedElastic()).doOnNext(dataBuffer -> {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                        String body = baos.toString(StandardCharsets.UTF_8);
                        log.info("for requestId: {}, request body :{}", request.getId(), body);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });
            }
        };
    }
}
