package com.Tesis.auth.dto;

import java.io.Serializable;

public class TokenDto implements Serializable {

    private String token;

    public TokenDto() {
    }

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
