package com.Tesis.auth.advise;

import java.util.Date;

public class ErrorMessage {
    private int statusCode;
    private String message;
    private String description;

    public ErrorMessage(int statusCode, String message, String description) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }


    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
