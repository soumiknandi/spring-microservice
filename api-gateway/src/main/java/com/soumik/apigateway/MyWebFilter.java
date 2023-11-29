package com.soumik.apigateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class MyWebFilter implements WebFilter {

    Logger logger = LoggerFactory.getLogger(MyWebFilter.class);

    final
    JWTUtil jwtUtil;

    final
    RouteFilter routeFilter;


    public MyWebFilter(JWTUtil jwtUtil, RouteFilter routeFilter) {
        this.jwtUtil = jwtUtil;
        this.routeFilter = routeFilter;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        logger.info("In filter");

        if (routeFilter.isSecured(request)) {
            // Authenticated Route
            String tokenFromHeader = jwtUtil.getTokenFromHeader(request);

            if (tokenFromHeader != null) {
                if (!jwtUtil.isTokenExpired(tokenFromHeader)) {
                    // if valid token
                    return chain.filter(exchange);
                } else {
                    // Invalid Token
                    JWTError error = JWTError.builder()
                            .error(jwtUtil.getError())
                            .build();

                    try {
                        String err = new ObjectMapper().writeValueAsString(error);

                        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
                        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
                        response.setStatusCode(HttpStatus.FORBIDDEN);

                        return response.writeWith(Flux.just(dataBuffer));

                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            // Public route
            return chain.filter(exchange);
        }

        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();    }
}
