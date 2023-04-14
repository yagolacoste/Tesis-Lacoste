package com.Tesis.auth.advise;

public enum ErrorCodes {

    EMAIL_NOT_PROVIDER("email-not-provided"),
    NOT_FOUND("Not Found"),
    ACCESS_DENIED("Access_denied"),
    TOKEN_EXPIRATION("token expirate");

    private String code;

    public String getCode() {

        return code;
    }

    private ErrorCodes(String code) {

        this.code = code;
    }
}
