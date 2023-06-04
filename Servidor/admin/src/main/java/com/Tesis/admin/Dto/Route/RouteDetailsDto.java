package com.Tesis.admin.Dto.Route;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class RouteDetailsDto implements Serializable {

    private String id;

    private String name;

    private String description;

    private List<GeoPoint> coordinates;

    public RouteDetailsDto(String id, String description, String name, String coordinates) {
        this.id = id;
        this.description = description;
        this.name = name;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<GeoPoint>>() {}.getType();
        this.coordinates = gson.fromJson(coordinates, listType);

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

    public List<GeoPoint> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<GeoPoint> coordinates) {
        this.coordinates = coordinates;
    }
}
