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
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("8"));
        configuration.setAllowedMethods(asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(
                asList("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin",
                        "Access-Control-Expose-Headers", "Access-Control-Allow-Headers"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}