package com.Tesis.admin.Models;


public enum FriendshipRequestStatus {
  PENDING("PENDING"),
  ACCEPTED("ACCEPTED"),
  REJECTED("REJECTED");

  private String value;



  FriendshipRequestStatus(String rejected) {

  }



  public String getValue() {

    return value;
  }



  public void setValue(String value) {

    this.value = value;
  }
}
