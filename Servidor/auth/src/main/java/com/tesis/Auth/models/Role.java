package com.tesis.Auth.models;

import com.tesis.Auth.Dto.RoleDto;
import com.tesis.Auth.config.ERole;

import javax.persistence.*;


@Entity
@Table(name= "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Role() {
    }


    public Role(String roleDto) {
        this.name=roleDto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
