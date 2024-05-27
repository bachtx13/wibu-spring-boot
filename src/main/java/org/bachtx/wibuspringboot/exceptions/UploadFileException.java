package org.bachtx.wibuspringboot.exceptions;

import java.io.Serial;

public class UploadFileException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 92302322343038431L;

    public UploadFileException() {
    }

    public UploadFileException(String message) {
        super(message);
    }

    public UploadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadFileException(Throwable cause) {
        super(cause);
    }

    public UploadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
