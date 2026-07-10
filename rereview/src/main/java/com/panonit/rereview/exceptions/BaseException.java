package com.panonit.rereview.exceptions;

/**
 * RuntimeException and its subclasses are unchecked exceptions.
 * Unchecked exceptions do not need to be declared in a method or constructor's throws clause
 * if they can be thrown by the execution of the method or constructor and propagate outside the method or constructor boundary.
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
