package com.admin.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="appuser")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "appuser",columnDefinition = "serial")
    private Long id;

    @Column(name= "username")
    private String username;

    @Column(name= "password")
    private String password;

    @Column(name= "id_identity_type")
    private String identityType;

    @Column(name= "identity")
    private String identity;

    @Column(name= "address")
    private String address;

    @Column(name= "first_name")
    private String firstName;

    @Column(name= "last_name")
    private String lastName;

    @Column(name= "age")
    private int age;

    @Column(name="birthday")
    private Date birthday;

    @Column(name= "phone")
    private String phone;

    @Column(name= "email")
    private String email;

    @Column(name= "creation_time")
    private Date creationTime;

    @Column(name= "update_time")
    private Date updateTime;

    @Column(name= "active")
    private boolean active;

    @Column(name= "deleted")
    private boolean deleted;



    public User() {
        creationTime=new Date();
        updateTime=new Date();
        active=true;
        deleted=false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
