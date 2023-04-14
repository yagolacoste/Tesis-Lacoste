package com.Tesis.bicycle.Dto.ApiRest.auth.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private String email;

    private String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
