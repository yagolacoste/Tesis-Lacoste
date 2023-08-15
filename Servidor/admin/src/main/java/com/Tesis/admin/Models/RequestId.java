package com.Tesis.admin.Models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class RequestId implements Serializable {

    @Column(name="user_origin")
    private Long userOriginId;

    @Column(name="user_dest")
    private Long userDestId;

    public RequestId() {
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
