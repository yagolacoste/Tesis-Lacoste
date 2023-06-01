package com.Tesis.auth.advise;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message, String code) {
        super(message, code);
    }


}
