package com.Tesis.bicycle.Converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.util.Date;

public class ConvertersDate {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
            return value == null ? null :LocalDate.ofEpochDay(value);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }
}
