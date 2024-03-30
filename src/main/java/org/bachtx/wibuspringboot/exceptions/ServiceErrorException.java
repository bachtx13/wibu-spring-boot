package org.bachtx.wibuspringboot.exceptions;

import java.io.Serial;

public class ServiceErrorException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2121535502369090705L;

    public ServiceErrorException() {
    }

    public ServiceErrorException(String message) {
        super(message);
    }

    public ServiceErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceErrorException(Throwable cause) {
        super(cause);
    }

    public ServiceErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
