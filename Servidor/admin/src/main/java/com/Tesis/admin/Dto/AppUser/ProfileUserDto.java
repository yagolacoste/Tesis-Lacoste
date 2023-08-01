package com.Tesis.admin.Dto.AppUser;

import com.Tesis.admin.Models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ProfileUserDto implements Serializable {

    @JsonProperty("nameComplete")
    private String nameComplete;

    @JsonProperty("photo")
    private String photo;

    public ProfileUserDto(UserAppDto u) {
        this.nameComplete=u.getFirstName()+" "+u.getLastName();
        this.photo=u.getFileName();
    }

    public String getNameComplete() {
        return nameComplete;
    }

    public void setNameComplete(String nameComplete) {
        this.nameComplete = nameComplete;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
