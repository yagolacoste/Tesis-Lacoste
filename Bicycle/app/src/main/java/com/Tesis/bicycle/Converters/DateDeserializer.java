package com.Tesis.bicycle.Converters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateDeserializer implements JsonDeserializer<Date> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
//            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
//            Date date=new Date( new SimpleDateFormat("yyyy-MM-dd").parse(json.getAsString()).getDate());
//            return date;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String date=json.toString();
            Date date1=dateFormat.parse(date);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
