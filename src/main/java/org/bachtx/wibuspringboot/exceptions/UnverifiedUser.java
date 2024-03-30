package org.bachtx.wibuspringboot.exceptions;

import java.io.Serial;

public class UnverifiedUser extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4845326325578406576L;

    public UnverifiedUser() {
    }

    public UnverifiedUser(String message) {
        super(message);
    }

    public UnverifiedUser(String message, Throwable cause) {
        super(message, cause);
    }

    public UnverifiedUser(Throwable cause) {
        super(cause);
    }

    public UnverifiedUser(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
