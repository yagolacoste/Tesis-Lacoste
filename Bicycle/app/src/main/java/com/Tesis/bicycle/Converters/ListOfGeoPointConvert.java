package com.Tesis.bicycle.Converters;

import android.location.Location;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class ListOfGeoPointConvert {

    @TypeConverter
    public static List<GeoPoint> fromString(String value) {
        return new Gson().fromJson(value, new TypeToken<List<GeoPoint>>() {}.getType());
    }

    @TypeConverter
    public static String toString(List<GeoPoint> list) {
        JsonArray jsonArray = new JsonArray();
        for (GeoPoint geoPoint : list) {
            JsonArray coordinates = new JsonArray();
            coordinates.add(new JsonPrimitive(geoPoint.getLatitude()));
            coordinates.add(new JsonPrimitive(geoPoint.getLongitude()));
            jsonArray.add(coordinates);
        }
        return new Gson().toJson(jsonArray);
    }
}
