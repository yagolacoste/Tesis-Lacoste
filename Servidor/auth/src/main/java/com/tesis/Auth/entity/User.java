package com.Tesis.auth.entity;





import com.Tesis.auth.payload.request.SignupRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="appuser",uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User implements Serializable{

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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "stored_document_id")
    private StoredDocument storedDocument;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "appuser_id", referencedColumnName = "id",updatable = false, insertable = false)})
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    public User() {
    }

    public User(SignupRequest signUpRequest) {
        this.password = signUpRequest.getPassword();
        this.firstName = signUpRequest.getFirstName();
        this.lastName = signUpRequest.getLastName();
        this.age = signUpRequest.getAge();
        this.phone = signUpRequest.getPhone();
        this.email = signUpRequest.getEmail();
        this.weight=signUpRequest.getWeight();
        this.height=signUpRequest.getHeight();
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


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public List<RefreshToken> getRefreshTokens() {
        return refreshTokens;
    }

    public void setRefreshTokens(List<RefreshToken> refreshTokens) {
        this.refreshTokens = refreshTokens;
    }

    public StoredDocument getStoredDocument() {
        return storedDocument;
    }

    public void setStoredDocument(StoredDocument storedDocument) {
        this.storedDocument = storedDocument;
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
