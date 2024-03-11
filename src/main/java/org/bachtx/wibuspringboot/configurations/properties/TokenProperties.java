package org.bachtx.wibuspringboot.configurations.properties;

import lombok.Getter;
import lombok.Setter;
import org.bachtx.wibuspringboot.core.YamlPropertiesFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "security-config.token")
@PropertySource(value = "classpath:config-properties.yml", factory = YamlPropertiesFactory.class)
@Getter
@Setter
public class TokenProperties {
    private String hashSecret;
    private Long validity;
}
