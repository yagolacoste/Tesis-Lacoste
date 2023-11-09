package com.Tesis.bicycle.Converters;

import android.location.Location;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ListTypeConverter {
    @TypeConverter
    public static List<Location> fromString(String value) {
        return new Gson().fromJson(value, new TypeToken<List<Location>>() {}.getType());
    }

    @TypeConverter
    public static String toString(List<Location> list) {
        List<String>locations=new ArrayList<>();
        for (int i=0;i< list.size()-1;i++) {
            Location loc=list.get(i);
           locations.add("POINT("+loc.getLongitude()+" "+loc.getLatitude()+")"+","+"acc: "+loc.getAccuracy()+" veloc: "+loc.getSpeed()+"dis"+loc.distanceTo(list.get(i+1)));
        }
        Location loc=list.get(list.size()-1);
        locations.add("POINT("+loc.getLongitude()+" "+loc.getLatitude()+")"+","+"acc: "+loc.getAccuracy()+" veloc: "+loc.getSpeed());

        return String.join("\n", locations);
    }
}