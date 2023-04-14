package com.Tesis.bicycle.Dto.ApiRest.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    @JsonProperty("token")
    private String token;

    public TokenResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
