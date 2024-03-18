package org.bachtx.wibuspringboot.core.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bachtx.wibuspringboot.entities.User;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.util.Locale;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -5296723820651839538L;
    private final Locale locale;
    private final String appUrl;
    private final User user;

    public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
