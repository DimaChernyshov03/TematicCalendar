package com.example.tematiccalendar.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface DayImageDao {
    @Query("SELECT * FROM DayImageEntity")
    List<DayImageEntity> getAll();

    @Query("SELECT * FROM DayImageEntity WHERE date >= :date1 AND date <= :date2")
    List<DayImageEntity> findByDateRange(LocalDate date1, LocalDate date2);

    @Query("SELECT * FROM DayImageEntity WHERE date = :date")
    DayImageEntity findByDate(LocalDate date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DayImageEntity dayImageEntity);

}
