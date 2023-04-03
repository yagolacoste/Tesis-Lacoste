package com.Tesis.admin.Dto.Route;

import org.json.JSONObject;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RouteNewRequestDto implements Serializable {
    @NotBlank(message = "id is required")
    private String id;

    private String description;

    private String  name;

    @NotBlank(message = "Coordinates is required")
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
