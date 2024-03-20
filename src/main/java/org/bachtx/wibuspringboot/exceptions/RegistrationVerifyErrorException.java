package org.bachtx.wibuspringboot.exceptions;

import java.io.Serial;

public class RegistrationVerifyErrorException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6828739865507226240L;

    public RegistrationVerifyErrorException() {
    }

    public RegistrationVerifyErrorException(String message) {
        super(message);
    }

    public RegistrationVerifyErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationVerifyErrorException(Throwable cause) {
        super(cause);
    }

    public RegistrationVerifyErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
