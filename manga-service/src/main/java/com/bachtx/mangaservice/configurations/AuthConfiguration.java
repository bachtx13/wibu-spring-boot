package com.bachtx.mangaservice.configurations;

import com.bachtx.wibucommon.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {

    @Value("${security-config.token.hash-secret}")
    private String hashSecret;

    @Value("${security-config.token.validity}")
    private Long validity;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(hashSecret, validity);
    }
}
