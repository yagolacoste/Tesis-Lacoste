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
    public JsonElement serialize(List<GeoPoint> src, Type typeOfSrc, JsonSerializationContext context) {
        JSONArray aux=new JSONArray();
        if(!src.isEmpty()){
            for(GeoPoint p:src){
//                Gson gson=new Gson();
//                String json= gson.toJson(p);
                aux.put(p);
            }
        }
        return new JsonPrimitive(aux.toString());
    }
}
