package com.Tesis.auth.advise;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message, String code) {
        super(message, code);
    }
}
