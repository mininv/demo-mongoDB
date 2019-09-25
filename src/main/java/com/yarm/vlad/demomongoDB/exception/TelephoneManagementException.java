package com.yarm.vlad.demomongoDB.exception;

public class TelephoneManagementException extends RuntimeException {
    private static final long serialVersionUID = 4528174638173808924L;

    public TelephoneManagementException() {
    }

    public TelephoneManagementException(final String message) {
        super(message);
    }

    public TelephoneManagementException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
