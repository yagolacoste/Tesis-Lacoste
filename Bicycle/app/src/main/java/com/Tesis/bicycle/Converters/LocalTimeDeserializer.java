package com.Tesis.bicycle.Converters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("HH:mm:ss").withLocale(Locale.ENGLISH));
    }
}
