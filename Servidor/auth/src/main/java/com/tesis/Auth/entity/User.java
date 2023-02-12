package com.Tesis.auth.entity;


import com.Tesis.auth.payload.request.SignupRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="appuser",uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id",columnDefinition = "serial")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(SignupRequest signUpRequest) {
        this.username = signUpRequest.getUsername();
        this.password = signUpRequest.getPassword();
        this.identityType = signUpRequest.getIdentityType();
        this.identity = signUpRequest.getIdentity();
        this.address = signUpRequest.getAddress();
        this.firstName = signUpRequest.getFirstName();
        this.lastName = signUpRequest.getLastName();
        this.age = signUpRequest.getAge();
        this.birthday = signUpRequest.getBirthday();
        this.phone = signUpRequest.getPhone();
        this.email = signUpRequest.getEmail();
        this.creationTime = new Date();
        this.updateTime =  new Date();
        this.active = true;
        this.deleted = false;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
