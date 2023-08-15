package com.Tesis.admin.Dto.Request;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class FriendshipRequestDetailsDto implements Serializable {

  @JsonProperty("nameComplete")
  private String nameComplete;

  @JsonProperty("userDest")
  private Long userDest;

  @JsonProperty("status")
  private String status;


  public FriendshipRequestDetailsDto() {

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



  public String getStatus() {

    return status;
  }



  public void setStatus(String status) {

    this.status = status;
  }
}
