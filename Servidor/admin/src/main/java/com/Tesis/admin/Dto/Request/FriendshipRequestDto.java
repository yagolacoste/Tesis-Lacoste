package com.Tesis.admin.Dto.Request;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class FriendshipRequestDto implements Serializable {

  @JsonProperty("userOrigin")
  private Long userOrigin;

  @JsonProperty("userDest")
  private Long userDest;



  public FriendshipRequestDto() {

  }



  public Long getUserOrigin() {

    return userOrigin;
  }



  public void setUserOrigin(Long userOrigin) {

    this.userOrigin = userOrigin;
  }



  public Long getUserDest() {

    return userDest;
  }



  public void setUserDest(Long userDest) {

    this.userDest = userDest;
  }
}
