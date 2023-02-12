package com.Tesis.bicycle.Converters;

import android.os.Build;
import android.text.format.DateFormat;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateDeserializer implements JsonDeserializer<Date> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return new Date( new SimpleDateFormat("dd-MM-yyy").parse(json.getAsString()).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
