package com.example.tematiccalendar.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {DayImageEntity.class}, version = 1, exportSchema = false)
@TypeConverters(LocalDateConverters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DayImageDao dayImageDao();

    public static AppDatabase getInstance(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, "tematic-calendar-db")
                .allowMainThreadQueries()
                .build();
    }
}
