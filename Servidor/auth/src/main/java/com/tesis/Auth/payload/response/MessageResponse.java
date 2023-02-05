package com.tesis.Auth.payload.response;

import java.io.Serializable;

public class MessageResponse implements Serializable {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
