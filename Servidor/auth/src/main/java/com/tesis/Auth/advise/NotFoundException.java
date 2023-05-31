package com.tesis.Auth.advise;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, String code) {
        super(message, code);
    }
}
