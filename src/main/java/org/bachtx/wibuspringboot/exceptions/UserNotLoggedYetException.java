package org.bachtx.wibuspringboot.exceptions;

import java.io.Serial;

public class UserNotLoggedYetException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6378223320887788565L;

    public UserNotLoggedYetException() {
    }

    public UserNotLoggedYetException(String message) {
        super(message);
    }

    public UserNotLoggedYetException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotLoggedYetException(Throwable cause) {
        super(cause);
    }

    public UserNotLoggedYetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
