package com.admin.Dto.Route;

import com.fasterxml.jackson.annotation.JsonRawValue;
import org.json.JSONObject;

import javax.persistence.Column;
import java.io.Serializable;

public class RouteDetailsDto implements Serializable {

    private String id;

    private String name;

    private String description;

    private String coordinates;

    public RouteDetailsDto(String id, String description, String name, String coordinates) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.coordinates = coordinates;
    }

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
