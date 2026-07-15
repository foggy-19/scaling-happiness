package com.panonit.evently.exceptions;

public class TicketTypeNotFoundException extends BaseException {

    public TicketTypeNotFoundException() {
        super();
    }

    public TicketTypeNotFoundException(String message) {
        super(message);
    }

    public TicketTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketTypeNotFoundException(Throwable cause) {
        super(cause);
    }
}
