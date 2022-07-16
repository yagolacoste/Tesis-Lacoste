package com.admin.Models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="route")
public class Route implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_route")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "weather")
    private String weather;

    @Column(name = "coordinates")
    private String coordinates;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
