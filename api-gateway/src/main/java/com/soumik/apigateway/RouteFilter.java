package com.soumik.apigateway;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteFilter {

    public final List<String> authAPIs = List.of(
            // Auth Routes
            "/api/auth/register",
            "/api/auth/login",
            // Eureka
            "/eurekawebui",
            "/eureka",
            // Swagger Related
            "api-docs",
            "/webjars/swagger-ui",
            "/swagger-ui.html",
            "swagger-config"
    );

    public boolean isSecured(ServerHttpRequest serverHttpRequest) {
        return authAPIs
                .stream()
                .noneMatch(
                        uri -> serverHttpRequest.getURI().getPath().contains(uri));
    }

}
