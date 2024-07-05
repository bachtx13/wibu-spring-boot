package com.bachtx.galleryservice.configurations;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {
    @Value("${cloudinary-config.api-key}")
    private String apiKey;
    @Value("${cloudinary-config.api-secret}")
    private String apiSecret;
    @Value("${cloudinary-config.cloud-name}")
    private String cloudName;

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary();
        cloudinary.config.apiKey = apiKey;
        cloudinary.config.apiSecret = apiSecret;
        cloudinary.config.cloudName = cloudName;
        return cloudinary;
    }
}
