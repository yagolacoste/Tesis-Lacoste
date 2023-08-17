package com.Tesis.admin.Models;

import javax.persistence.*;

@Entity
@Table(name="FriendshipRequest")
public class FriendshipRequest {
    @EmbeddedId
    private FriendshipRequestId id;

    @ManyToOne
    @MapsId("userOriginId")
    @JoinColumn(name = "user_origin")
    private User userOrigin;

    @ManyToOne
    @MapsId("userDestId")
    @JoinColumn(name = "user_dest")
    private User userDest;


    @Column(name = "status")
    private Integer status;


    public FriendshipRequest() {
    }

    public FriendshipRequestId getId() {
        return id;
    }

    public void setId(FriendshipRequestId id) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
