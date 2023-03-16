package com.Tesis.auth.advise;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String message, String code) {
        super(message, code);
    }
}
