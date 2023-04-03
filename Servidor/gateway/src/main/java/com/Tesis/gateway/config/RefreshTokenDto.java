package com.Tesis.gateway.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RefreshTokenDto implements Serializable {

    @JsonProperty("token")
    private String token;

    public RefreshTokenDto() {
    }

    public RefreshTokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}