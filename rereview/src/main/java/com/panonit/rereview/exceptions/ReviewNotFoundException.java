package com.panonit.rereview.exceptions;

public class ReviewNotFoundException extends BaseException {

    public ReviewNotFoundException() {
        super();
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
