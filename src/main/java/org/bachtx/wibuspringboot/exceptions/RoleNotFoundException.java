package org.bachtx.wibuspringboot.exceptions;

import java.io.Serial;

public class RoleNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4952076459766457060L;

    public RoleNotFoundException() {
    }

    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotFoundException(Throwable cause) {
        super(cause);
    }

    public RoleNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
