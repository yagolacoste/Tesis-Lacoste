package com.Tesis.admin.Models;

import com.Tesis.admin.Dto.AppUser.UserAppDto;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="appuser")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id",columnDefinition = "serial")
    private Long id;

    @Column(name= "password")
    private String password;


    @Column(name= "first_name")
    private String firstName;

    @Column(name= "last_name")
    private String lastName;

    @Column(name= "age")
    private int age;

    @Column(name= "phone")
    private String phone;

    @Column(name= "email")
    private String email;

    @Column(name= "weight")
    private Integer weight;

    @Column(name= "height")
    private Integer height;


    @OneToOne(cascade = CascadeType.ALL, targetEntity = StoredDocument.class)
    @JoinColumn(name = "stored_document_id")
    private StoredDocument storedDocument;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "friends",joinColumns = @JoinColumn(name = "appuser_id"),
    inverseJoinColumns = @JoinColumn(name = "appuser_id_friends"))
    private Set<User> friends;


    public User() {

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

    public StoredDocument getStoredDocument() {
        return storedDocument;
    }

    public void setStoredDocument(StoredDocument storedDocument) {
        this.storedDocument = storedDocument;
    }


    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
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
