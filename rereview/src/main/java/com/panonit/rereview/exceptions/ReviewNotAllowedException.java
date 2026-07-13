package com.panonit.rereview.exceptions;

public class ReviewNotAllowedException extends BaseException {

    public ReviewNotAllowedException() {
        super();
    }

    public ReviewNotAllowedException(String message) {
        super(message);
    }

    public ReviewNotAllowedException(Throwable cause) {
        super(cause);
    }

    public ReviewNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
