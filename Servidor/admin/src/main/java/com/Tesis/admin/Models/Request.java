package com.Tesis.admin.Models;

import javax.persistence.*;

@Entity
@Table(name="Request")
public class Request {
    @EmbeddedId
    private RequestId id;

    @ManyToOne
    @MapsId("userOriginId")
    @JoinColumn(name = "user_origin")
    private User userOrigin;

    @ManyToOne
    @MapsId("userDestId")
    @JoinColumn(name = "user_dest")
    private User userDest;

    private Integer state;

    public Request() {
    }

    public RequestId getId() {
        return id;
    }

    public void setId(RequestId id) {
        this.id = id;
    }

    public User getUserOrigin() {
        return userOrigin;
    }

    public void setUserOrigin(User userOrigin) {
        this.userOrigin = userOrigin;
    }

    public User getUserDest() {
        return userDest;
    }

    public void setUserDest(User userDest) {
        this.userDest = userDest;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
