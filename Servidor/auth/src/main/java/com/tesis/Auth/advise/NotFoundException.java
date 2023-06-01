package com.Tesis.auth.advise;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, String code) {
        super(message, code);
    }
}
