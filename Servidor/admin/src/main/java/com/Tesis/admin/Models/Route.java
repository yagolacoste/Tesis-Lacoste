package com.Tesis.admin.Models;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonRawValue;

import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="route")
public class Route implements Serializable {

    @Id
    @Column(name= "id_route")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    @Column(name = "coordinates")
    @JsonRawValue
    private String coordinates;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
