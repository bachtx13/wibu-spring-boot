package com.bachtx.messagesystemserver.configurations;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaAdminConfiguration {
    @Value("${spring.kafka.bootstrap-servers: localhost:9092}")
    private String bootstrapServers;
    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers
        ));
    }

    @Bean
    public NewTopic sendMailTopic() {
        return new NewTopic("send-mail", 2, (short) 1);
    }
}
