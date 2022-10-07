package com.Tesis.bicycle.Converters;

import androidx.room.TypeConverter;

import java.util.Date;

public class ConvertersDate {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
