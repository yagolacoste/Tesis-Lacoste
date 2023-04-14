package com.Tesis.bicycle.Dto.ApiRest.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TokenRefreshRequest implements Serializable {

    @JsonProperty("refreshToken")
    private String refreshToken;

    public TokenRefreshRequest() {
    }

    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}