package org.bachtx.wibuspringboot.configurations;

import com.cloudinary.Cloudinary;
import org.bachtx.wibuspringboot.configurations.properties.CloudinaryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {
    private final CloudinaryProperties cloudinaryProperties;

    public CloudinaryConfiguration(CloudinaryProperties cloudinaryProperties) {
        this.cloudinaryProperties = cloudinaryProperties;
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary();
        cloudinary.config.apiKey = cloudinaryProperties.getApiKey();
        cloudinary.config.apiSecret = cloudinaryProperties.getApiSecret();
        cloudinary.config.cloudName = cloudinaryProperties.getCloudName();
        return cloudinary;
    }
}
