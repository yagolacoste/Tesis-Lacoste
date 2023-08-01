package com.Tesis.bicycle.Dto.ApiRest.Statistics;


import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ProfileUserDto implements Serializable {

    @JsonProperty("nameComplete")
    private String nameComplete;

    @JsonProperty("photo")
    private String photo;

    public ProfileUserDto() {
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
