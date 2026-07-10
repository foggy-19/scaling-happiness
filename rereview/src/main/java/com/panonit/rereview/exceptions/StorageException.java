package com.panonit.rereview.exceptions;

public class StorageException extends BaseException {

    public StorageException() {
        super();
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }


    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
