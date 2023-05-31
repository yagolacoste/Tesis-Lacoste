package com.Tesis.bicycle.Dto.ApiRest.auth.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Set;

public class SignupRequest implements Serializable {



    @JsonProperty("password")
    private String password;


    @JsonProperty("firstName")
    private String firstName;


    @JsonProperty("lastName")
    private String lastName;


    @JsonProperty("age")
    private int age;


    @JsonProperty("phone")
    private String phone;


    @JsonProperty("email")
    private String email;


    @JsonProperty("weigth")
    private Integer weight;


    @JsonProperty("heigth")
    private Integer height;

    @JsonProperty("role")
    private Set<String> role;

    @JsonProperty("photo")
    private Long photo;

    public SignupRequest() {
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public Long getPhoto() {
        return photo;
    }

    public void setPhoto(Long photo) {
        this.photo = photo;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
