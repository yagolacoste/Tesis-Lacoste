package com.Tesis.bicycle.Converters;



import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListOfGeoPointDeserializer implements JsonDeserializer<List<GeoPoint>> {


//    @Override
//    public List<GeoPoint> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

//        Gson gson = new Gson();
//        Type listType = new TypeToken<List<GeoPoint>>(){}.getType();
//        List<GeoPoint> arrayDeJson = gson.fromJson(json, listType);
//        return arrayDeJson;


//        try {
//            JSONArray coordinates=new JSONArray(resultCoordinates);
//            for(int i=0;i<coordinates.length();i++){
//                String aux=coordinates.getString(i);
//                String[] split=aux.split(",");
//                result.add(new GeoPoint(Double.valueOf(split[0]),Double.valueOf(split[1])));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
    @Override
    public List<GeoPoint> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        List<GeoPoint> geoPoints = new ArrayList<>();
        JsonArray jsonArray = json.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject coordinates = element.getAsJsonObject();
            double latitude = coordinates.get("latitude").getAsDouble();
            double longitude = coordinates.get("longitude").getAsDouble();

            // Crear un objeto GeoPoint y agregarlo a la lista
            GeoPoint geoPoint = new GeoPoint(latitude, longitude);
            geoPoints.add(geoPoint);
        }
        return geoPoints;
    }

}
