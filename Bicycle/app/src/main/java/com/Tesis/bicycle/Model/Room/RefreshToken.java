package com.Tesis.bicycle.Model.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;

@Entity(tableName="refresh_token")
public class RefreshToken {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    private String id;

    @ColumnInfo(name="refreshToken")
    private String refreshToken;

    @ColumnInfo(name="accessToken")
    private String accessToken;

    @ColumnInfo(name="tokenType")
    private String tokenType;

    public RefreshToken() {
    }

    public RefreshToken(JwtResponse jwtResponse) {
        this.id = String.valueOf(jwtResponse.getId());
        this.refreshToken = jwtResponse.getRefreshToken();
        this.accessToken = jwtResponse.getAccessToken();
        this.tokenType = jwtResponse.getTokenType();
    }

    public RefreshToken(RefreshTokenDto refreshToken) {
        this.id = refreshToken.getId();
        this.refreshToken = refreshToken.getRefreshToken();
        this.accessToken = refreshToken.getAccessToken();
        this.tokenType = refreshToken.getTokenType();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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
