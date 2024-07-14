package com.bachtx.gateway.configurations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfiguration {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", (r) ->
                        r.path("/auth/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.stripPrefix(1))
                                .uri("lb://auth-service"))
                .route("gallery", (r) ->
                        r.path("/gallery/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.stripPrefix(1))
                                .uri("lb://gallery-service"))
                .route("manga", (r) ->
                        r.path("/manga/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.stripPrefix(1))
                                .uri("lb://manga-service"))
                .build();
    }
}
