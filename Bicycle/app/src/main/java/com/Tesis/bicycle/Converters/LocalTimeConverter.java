package com.Tesis.bicycle.Converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter {

    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter

    public static LocalTime toLocalTime(String timeString) {
        return LocalTime.parse(timeString, timeFormat);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static String fromLocalTime(LocalTime localTime) {
        return localTime.format(timeFormat);
    }
}

