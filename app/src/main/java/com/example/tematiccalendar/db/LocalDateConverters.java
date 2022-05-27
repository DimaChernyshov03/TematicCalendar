package com.example.tematiccalendar.db;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateConverters {
    @TypeConverter
    public LocalDate fromTimestamp(Long value) {
        if (value == null) {
            return null;
        }
        return LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public Long dateToTimestamp(LocalDate date){
        return date.toEpochDay();
    }
}
