package com.Tesis.bicycle.Dto.ApiRest.Request;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class RequestNotifications implements Serializable {

    @JsonProperty("mySent")
    private List<RequestDto> mySent;

    @JsonProperty("myReceived")
    private List<RequestDto> myReceived;

    public RequestNotifications() {
    }

    public List<RequestDto> getMySent() {
        return mySent;
    }

    public void setMySent(List<RequestDto> mySent) {
        this.mySent = mySent;
    }

    public List<RequestDto> getMyReceived() {
        return myReceived;
    }

    public void setMyReceived(List<RequestDto> myReceived) {
        this.myReceived = myReceived;
    }


}
