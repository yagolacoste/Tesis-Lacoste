package com.Tesis.bicycle.Model;



public enum FriendshipRequestStatus {

    PENDING(0, "PENDING"),
    ACCEPTED(1, "ACCEPTED"),
    REJECTED(2, "REJECTED"),
    NOTFRIENDS(3, "NOT FRIENDS");

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



    public Integer getValue() {

        return value;
    }



    public String getTitle() {

        return title;
    }
}

