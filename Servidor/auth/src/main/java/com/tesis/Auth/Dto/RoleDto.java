package com.tesis.Auth.Dto;

import com.tesis.Auth.config.ERole;
import com.tesis.Auth.models.Role;

import java.io.Serializable;

public class RoleDto implements Serializable {

    private Integer id;

    private String name;

    public RoleDto() {
    }




    public RoleDto(RoleDto appRole) {
        this.id = appRole.getId();
        this.name = appRole.getName();
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
