package com.Tesis.bicycle.Model.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.util.StringUtil;

import com.Tesis.bicycle.Constants;

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

@Entity(tableName = "route")
public class Route {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name="id")
    private String id;

    @ColumnInfo(name= "name")
    private String name;

    @ColumnInfo(name= "description")
    private String description;

    @ColumnInfo(name= "coordinates")
    private String coordinates;

    public Route() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
