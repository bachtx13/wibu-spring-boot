package org.bachtx.wibuspringboot.core.listeners;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.core.events.OnRegistrationCompleteEvent;
import org.bachtx.wibuspringboot.dtos.commons.RegistrationVerifyTemplate;
import org.bachtx.wibuspringboot.services.MailSenderService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationListener {
    private final MailSenderService mailSenderService;

    @Async
    @EventListener
    public void handleRegistrationComplete(OnRegistrationCompleteEvent event) {
        mailSenderService.sendMessageWithFreemarkerTemplate(
                event.getEmail(),
                "Registration verify",
                "mails/registration-verify",
                RegistrationVerifyTemplate.builder()
                        .url("http://localhost:8080/api/v1/auth/registration-verify/" + event.getToken())
                        .build()
        );
    }
}
