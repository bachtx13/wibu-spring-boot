package com.bachtx.gateway.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration implements WebFluxConfigurer {
    public static final String[] PERMITTED_URLS = new String[]{
            "/token/get",
            "/auth/**",
            "/actuator/**"
    };
    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerSecurityContextRepository securityContextRepository;
    private final ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;
    private final ServerAccessDeniedHandler serverAccessDeniedHandler;
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity serverHttpSecurity) {

        return serverHttpSecurity
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(serverAuthenticationEntryPoint)
                        .accessDeniedHandler(serverAccessDeniedHandler)
                )
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(PERMITTED_URLS)
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                )
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true).maxAge(3600);
    }
}
