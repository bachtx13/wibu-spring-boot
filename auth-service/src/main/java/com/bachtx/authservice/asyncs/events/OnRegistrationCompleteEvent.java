package com.bachtx.authservice.asyncs.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final UUID token;
    private final String email;

    public OnRegistrationCompleteEvent(UUID token, String email) {
        super(token);
        this.token = token;
        this.email = email;
    }
}
