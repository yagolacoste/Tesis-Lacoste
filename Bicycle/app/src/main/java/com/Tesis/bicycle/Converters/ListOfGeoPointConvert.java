package com.Tesis.bicycle.Converters;

import android.location.Location;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListOfGeoPointConvert {

    @TypeConverter
    public static List<GeoPoint> fromString(String value) {
        Type listType = new TypeToken<List<List<Double>>>() {}.getType();
        List<List<Double>> coordinatesList = new Gson().fromJson(value, listType);

        // Convertir la lista de listas de coordenadas a GeoPoint
        List<GeoPoint> geoPoints = new ArrayList<>();
        for (List<Double> coordinates : coordinatesList) {
            double latitude = coordinates.get(0);
            double longitude = coordinates.get(1);
            geoPoints.add(new GeoPoint(latitude, longitude));
        }

        return geoPoints;

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
