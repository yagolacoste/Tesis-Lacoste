package com.Tesis.bicycle.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "route")
public class Route {

    @PrimaryKey(autoGenerate = true)
     int id;

    @ColumnInfo(name= "coordinates")
     String coordinates;


    public Route(String coordinates) {
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
