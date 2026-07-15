package com.panonit.evently.exceptions;

public class EventUpdateException extends BaseException {

    public EventUpdateException() {
        super();
    }

    public EventUpdateException(String message) {
        super(message);
    }

    public EventUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventUpdateException(Throwable cause) {
        super(cause);
    }
}
