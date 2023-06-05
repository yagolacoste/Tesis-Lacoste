package com.Tesis.admin.Models;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonRawValue;

import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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

    @Column(name = "distance")
    private float distance;

    @Column(name = "avgProm")
    private LocalTime avgProm;
    @OneToMany(mappedBy = "route")
    private List<Battle> battles;

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

    public List<Battle> getBattles() {
        return battles;
    }

    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }



    public float getDistance() {

        return distance;
    }



    public void setDistance(float distance) {

        this.distance = distance;
    }



    public LocalTime getAvgProm() {

        return avgProm;
    }



    public void setAvgProm(LocalTime avgProm) {

        this.avgProm = avgProm;
    }
}
