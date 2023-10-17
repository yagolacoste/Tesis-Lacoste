package com.Tesis.bicycle.Model.Room;

import android.location.Location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.Tesis.bicycle.Converters.ListTypeConverter;

import org.jetbrains.annotations.NotNull;
import org.osmdroid.util.GeoPoint;

import java.util.List;

@Entity(tableName="routes")
public class Routes {
    @PrimaryKey
    @NotNull
    @ColumnInfo(name="id")
    private String id;
    @ColumnInfo(name = "unfiltered_points")
    @TypeConverters(ListTypeConverter.class)
    private List<Location> unfilteredPoints;
    @ColumnInfo(name = "filtered_points")
    @TypeConverters(ListTypeConverter.class)
    private List<Location> filteredPoints;

    public Routes() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Location> getUnfilteredPoints() {
        return unfilteredPoints;
    }

    public void setUnfilteredPoints(List<Location> unfilteredPoints) {
        this.unfilteredPoints = unfilteredPoints;
    }

    public List<Location> getFilteredPoints() {
        return filteredPoints;
    }

    public void setFilteredPoints(List<Location> filteredPoints) {
        this.filteredPoints = filteredPoints;
    }
}
