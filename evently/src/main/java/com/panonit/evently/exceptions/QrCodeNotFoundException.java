package com.panonit.evently.exceptions;

public class QrCodeNotFoundException extends BaseException {

    public QrCodeNotFoundException() {
        super();
    }

    public QrCodeNotFoundException(String message) {
        super(message);
    }

    public QrCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QrCodeNotFoundException(Throwable cause) {
        super(cause);
    }
}
