package com.Tesis.bicycle.Converters;



import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Type;
import java.util.List;

public class ListOfGeoPointDeserializer implements JsonDeserializer<List<GeoPoint>> {


    @Override
    public List<GeoPoint> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<GeoPoint>>(){}.getType();
        List<GeoPoint> arrayDeJson = gson.fromJson(json, listType);
        return arrayDeJson;
    }
}
