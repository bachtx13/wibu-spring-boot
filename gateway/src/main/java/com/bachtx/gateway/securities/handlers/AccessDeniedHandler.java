package com.bachtx.gateway.securities.handlers;

import com.bachtx.wibucommon.dtos.response.ErrorResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class AccessDeniedHandler implements ServerAccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException accessDeniedException) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ResponseTemplate<?> errorResponse = ResponseTemplate.builder()
                .message("Forbidden Access")
                .errors(List.of(
                        ErrorResponse.builder()
                                .detail(accessDeniedException.getMessage())
                                .build()
                ))
                .status(EResponseStatus.FAIL)
                .build();

        return Mono.fromRunnable(() -> {
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
                exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))).subscribe();
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        });
    }
}
