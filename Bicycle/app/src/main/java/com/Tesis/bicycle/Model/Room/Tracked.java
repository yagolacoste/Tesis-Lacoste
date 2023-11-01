package com.Tesis.bicycle.Model.Room;

import android.location.Location;

import androidx.room.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.osmdroid.util.GeoPoint;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName="tracked")
public class Tracked {

    private String id;
    private String title="";
    private String description="";
    private float distance=0;
    private float avgSpeed=0;
    @JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE,pattern = "H:mm:ss")
    private LocalTime time;

    @JsonProperty("timeCreated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    private Date timeCreated;
    private transient List<Location> points=new ArrayList<>();//si no se repite
    private boolean repeat=false;//para ver si se repite
    private Long battle;//si se hizo una batalla
    private boolean update;//si esta subido

}
