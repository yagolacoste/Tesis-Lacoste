package com.Tesis.admin.Models;

import lombok.Getter;

@Getter
public enum FriendshipRequestStatus {

    PENDING(0, "PENDING"),
    ACCEPTED(1, "ACCEPTED"),
    REJECTED(2, "REJECTED");

    private Integer value;
    private String title;

    FriendshipRequestStatus(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
