package com.Tesis.bicycle.Converters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateSerializer implements JsonSerializer<Date> {
    private static final DateFormat formatter = new SimpleDateFormat("dd-MM-yyy");
    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(src));
    }
}
