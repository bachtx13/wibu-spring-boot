package org.bachtx.wibuspringboot.core.listeners;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.core.events.OnRegistrationCompleteEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationListener {
    private final JavaMailSender javaMailSender;

    @EventListener
    public void handleRegistrationComplete(OnRegistrationCompleteEvent event) {
        
    }
}
