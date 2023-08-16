package com.Tesis.bicycle.Dto.ApiRest.FriendshipRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FriendshipRequestDto implements Serializable {

    @JsonProperty("nameComplete")
    private String nameComplete;

    @JsonProperty("fileName")
    private String fileName;

    public String getNameComplete() {
        return nameComplete;
    }

    public void setNameComplete(String nameComplete) {
        this.nameComplete = nameComplete;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
