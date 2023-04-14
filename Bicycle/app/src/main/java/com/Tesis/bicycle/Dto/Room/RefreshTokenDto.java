package com.Tesis.bicycle.Dto.Room;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;

public class RefreshTokenDto {

    private Long id;

    private String refreshToken;

    private String accessToken;

    private String tokenType;

    public RefreshTokenDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
