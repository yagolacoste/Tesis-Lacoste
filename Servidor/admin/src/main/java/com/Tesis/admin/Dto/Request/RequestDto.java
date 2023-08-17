package com.Tesis.admin.Dto.Request;

import com.Tesis.admin.Models.FriendshipRequest;
import com.Tesis.admin.Models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class RequestDto implements Serializable {

    @JsonProperty("nameComplete")
    private String nameComplete;

    @JsonProperty("userDest")
    private Long userDest;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("fileName")
    private String fileName;

    public RequestDto() {
    }

    public RequestDto(User user,Integer status) {
        this.nameComplete = user.getFirstName()+" "+user.getLastName();
        this.userDest = user.getId();
        this.status = status;
        this.fileName = user.getStoredDocument().getCompleteFileName();
    }

    public String getNameComplete() {
        return nameComplete;
    }

    public void setNameComplete(String nameComplete) {
        this.nameComplete = nameComplete;
    }

    public Long getUserDest() {
        return userDest;
    }

    public void setUserDest(Long userDest) {
        this.userDest = userDest;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
