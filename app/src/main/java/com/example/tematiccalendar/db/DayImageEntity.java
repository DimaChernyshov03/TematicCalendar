package com.example.tematiccalendar.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class DayImageEntity {
    @PrimaryKey
    public LocalDate date;

    @ColumnInfo
    public Long resourceId;

    @ColumnInfo
    public String text;
}
