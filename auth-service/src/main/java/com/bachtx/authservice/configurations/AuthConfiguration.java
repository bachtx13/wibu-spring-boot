package com.bachtx.authservice.configurations;

import com.bachtx.wibucommon.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfiguration {
    @Value("${security-config.token.hash-secret}")
    private String hashSecret;

    @Value("${security-config.token.validity}")
    private Long validity;

    @Value("${security-config.password-encoder.strength}")
    private Integer passwordEncoder;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(hashSecret, validity);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(passwordEncoder);
    }
}
