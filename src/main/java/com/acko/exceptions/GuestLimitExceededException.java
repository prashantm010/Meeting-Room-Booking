package com.acko.exceptions;

public class GuestLimitExceededException extends RuntimeException {
    public GuestLimitExceededException(String message) {
        super(message);
    }
}
