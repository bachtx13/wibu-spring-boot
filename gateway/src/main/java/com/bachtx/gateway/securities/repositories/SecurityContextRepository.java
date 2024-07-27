package com.bachtx.gateway.securities.repositories;

import jakarta.ws.rs.core.HttpHeaders;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final ReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        return Mono.just(serverWebExchange.getRequest())
                .mapNotNull(serverHttpRequest -> serverHttpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authenticationHeader -> authenticationHeader != null && authenticationHeader.startsWith(TOKEN_PREFIX))
                .switchIfEmpty(Mono.empty())
                .map(authHeader -> authHeader.replace(TOKEN_PREFIX, "".trim()))
                .flatMap(authToken -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authToken, authToken)))
                .map(SecurityContextImpl::new);
    }
}