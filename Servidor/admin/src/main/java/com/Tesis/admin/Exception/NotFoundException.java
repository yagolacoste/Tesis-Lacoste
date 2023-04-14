package com.Tesis.admin.Exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, String code) {
        super(message, code);
    }
}
