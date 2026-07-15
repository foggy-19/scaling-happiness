package com.panonit.evently.exceptions;

public class TicketSoldOutException extends BaseException {

    public TicketSoldOutException() {
        super();
    }

    public TicketSoldOutException(String message) {
        super(message);
    }

    public TicketSoldOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketSoldOutException(Throwable cause) {
        super(cause);
    }
}
