package com.tesis.Auth.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tesis.Auth.models.Role;
import com.tesis.Auth.models.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserTokenDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;


    public UserTokenDto() {
    }

    public UserTokenDto(UserDto userDto) {
        this.id = userDto.getId();
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
