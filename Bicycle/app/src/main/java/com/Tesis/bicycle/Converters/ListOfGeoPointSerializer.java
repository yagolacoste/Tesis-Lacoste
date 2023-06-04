package com.Tesis.bicycle.Converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListOfGeoPointSerializer implements JsonSerializer<List<GeoPoint>> {

    @Override
    public JsonElement serialize(List<GeoPoint> geoPoints, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        for (GeoPoint geoPoint : geoPoints) {
            JsonArray coordinates = new JsonArray();
            coordinates.add(new JsonPrimitive(geoPoint.getLatitude()));
            coordinates.add(new JsonPrimitive(geoPoint.getLongitude()));
            jsonArray.add(coordinates);
        }
        return jsonArray;
    }
}
