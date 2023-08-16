package com.Tesis.admin.Models;

import com.Tesis.admin.Dto.Request.FriendshipRequestDto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class FriendshipRequestId implements Serializable {

    @Column(name="user_origin")
    private Long userOriginId;

    @Column(name="user_dest")
    private Long userDestId;

    public FriendshipRequestId(FriendshipRequestDto friendshipRequestDto) {
        this.userOriginId= friendshipRequestDto.getUserOrigin();
        this.userDestId= friendshipRequestDto.getUserDest();
    }

    public FriendshipRequestId(Long userOriginId, Long userDestId) {
        this.userOriginId = userOriginId;
        this.userDestId = userDestId;
    }

    public Long getUserOriginId() {
        return userOriginId;
    }

    public void setUserOriginId(Long userOriginId) {
        this.userOriginId = userOriginId;
    }

    public Long getUserDestId() {
        return userDestId;
    }

    public void setUserDestId(Long userDestId) {
        this.userDestId = userDestId;
    }
}
