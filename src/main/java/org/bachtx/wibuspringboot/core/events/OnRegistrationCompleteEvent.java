package org.bachtx.wibuspringboot.core.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -5296723820651839538L;
    private final UUID token;
    private final String email;

    public OnRegistrationCompleteEvent(UUID token, String email) {
        super(token);
        this.token = token;
        this.email = email;
    }
}
