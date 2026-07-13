package com.panonit.rereview.exceptions;

public class RestaurantNotFoundException extends BaseException {

    public RestaurantNotFoundException() {
        super();
    }

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Throwable cause) {
        super(cause);
    }

    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
