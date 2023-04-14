package com.Tesis.admin.Exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message, String code) {
        super(message, code);
    }
}
