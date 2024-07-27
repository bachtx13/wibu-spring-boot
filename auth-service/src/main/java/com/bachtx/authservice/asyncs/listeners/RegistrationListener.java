package com.bachtx.authservice.asyncs.listeners;

import com.bachtx.authservice.asyncs.events.OnRegistrationCompleteEvent;
import com.bachtx.authservice.dtos.templates.RegistrationVerifyTemplate;
import com.bachtx.authservice.messages.KafkaProducer;
import com.bachtx.wibucommon.dtos.messages.SendMailModel;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationListener {
    private final KafkaProducer kafkaProducer;

    @Async
    @EventListener
    public void handleRegistrationComplete(OnRegistrationCompleteEvent event) {
        kafkaProducer.sendMessage(
                "send-mail",
                SendMailModel.builder()
                        .to(event.getEmail())
                        .subject("Registration verify")
                        .templateName("mails/registration-verify")
                        .templateData(
                                RegistrationVerifyTemplate.builder()
                                        .url("http://localhost:8763/auth/registration-verify/" + event.getToken())
                                        .build()
                        )
                        .build());
    }
}
