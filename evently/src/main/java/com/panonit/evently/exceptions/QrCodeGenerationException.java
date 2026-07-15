package com.panonit.evently.exceptions;

public class QrCodeGenerationException extends BaseException {

    public QrCodeGenerationException() {
        super();
    }

    public QrCodeGenerationException(String message) {
        super(message);
    }

    public QrCodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public QrCodeGenerationException(Throwable cause) {
        super(cause);
    }
}
