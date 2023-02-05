package com.tesis.Auth.payload.response;

import com.tesis.Auth.Dto.UserTokenDto;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private UserTokenDto userDto;
    private List<String>roles;

    public JwtResponse(String token,String refreshToken, UserTokenDto userDto, List<String>roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userDto = userDto;
        this.roles=roles;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserTokenDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserTokenDto userDto) {
        this.userDto = userDto;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
