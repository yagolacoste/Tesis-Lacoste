package com.Tesis.bicycle.Model.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "route")
public class Route {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    long id;

    @ColumnInfo(name= "coordinates")
     String coordinates;


    public Route(String coordinates) {
        this.coordinates = coordinates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
