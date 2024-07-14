package com.bachtx.gateway.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    public static final String[] PERMITTED_URL = new String[]{
            "/token/get",
            "/auth/**",
            "/actuator/**"
    };
    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerSecurityContextRepository securityContextRepository;

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity serverHttpSecurity) {

        return serverHttpSecurity
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                                swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                        )).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() ->
                                swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                        )))
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(PERMITTED_URL)
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                )
                .build();
    }
}
